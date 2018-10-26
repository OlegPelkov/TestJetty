package moneyTransfer.techprocess.processPerformers;

import moneyTransfer.account.Account;
import moneyTransfer.account.AccountStandardImpl;
import moneyTransfer.account.AccountVIPImpl;
import moneyTransfer.data.AccountDataHolderImpl;
import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.IdCounter;

import java.math.BigDecimal;

import static moneyTransfer.messages.Messages.*;

public class AccountOperationHandlerStandardImpl implements AccountOperationHandler {

    private AccountDataHolderImpl accountDataHolder = AccountDataHolderImpl.getInstance();

    @Override
    public OperationResponse transfer(long sourceId, long destinationId, BigDecimal value) {

        Account srcAccount = accountDataHolder.get(sourceId);
        Account destAccount = accountDataHolder.get(destinationId);

        if (srcAccount == null) {
            return new OperationResponse(OperationStatus.ERROR,"FOR ID : " + sourceId + ACCOUNT_NOT_FOUND);
        }
        if (destAccount == null) {
            return new OperationResponse(OperationStatus.ERROR, "FOR ID : " + destinationId + ACCOUNT_NOT_FOUND);
        }

        // firstLockAccount secondLockAccount for resolve potential deadlock
        Account firstLockAccount;
        Account secondLockAccount;

        if (sourceId < destinationId) {
            firstLockAccount = srcAccount;
            secondLockAccount = destAccount;
        } else {
            firstLockAccount = destAccount;
            secondLockAccount = srcAccount;
        }

        firstLockAccount.lock();
        try {
            //check isDeleted() because another thread can delete this moneyTransfer.account, between our get() and lock()
            if (firstLockAccount.isDeleted()) {
                return new OperationResponse(OperationStatus.ERROR,"FOR ID : " + firstLockAccount.getId() + ACCOUNT_NOT_FOUND);
            }
            try {
                secondLockAccount.lock();
                if (destAccount.isDeleted()) {
                    return new OperationResponse(OperationStatus.ERROR, "FOR ID : " + destinationId + ACCOUNT_NOT_FOUND);
                }
                if (!(srcAccount.getCurrentValue().compareTo(value) < 0)) {
                    innerTransfer(srcAccount, destAccount, value);
                } else {
                    return new OperationResponse(OperationStatus.ERROR, "ON ACCOUNT : "+ sourceId + NOT_ENOUGH_MONEY + " CURRENT VALUE : " + srcAccount.getCurrentValue());
                }
            } finally {
                secondLockAccount.unlock();
            }
        } finally {
            firstLockAccount.unlock();
        }
        return new OperationResponse(OperationStatus.SUCCESS, SUCCESS_TRANSFER_MONEY + " from : " + sourceId + " to : " + destinationId + " value : " + value);
    }

    @Override
    public OperationResponse createNewAccount(BigDecimal initialValue, boolean vip) {
        try {
            long nextId = IdCounter.getInstance().getNextId();
            if (accountDataHolder.get(nextId) == null) {
                if(!vip) {
                    accountDataHolder.put(nextId, new AccountStandardImpl(nextId, initialValue));
                } else {
                    accountDataHolder.put(nextId, new AccountVIPImpl(nextId, initialValue));
                }
                return new OperationResponse(OperationStatus.SUCCESS, String.valueOf(nextId));
            } throw new Exception("Can not create moneyTransfer.account with id: " + nextId);
        } catch (Exception e) {
            return new OperationResponse(OperationStatus.ERROR, ERROR_CREATE_ACCOUNT + " " + e.getMessage());
        }
    }

    @Override
    public OperationResponse deleteAccount(long id) {
        try {
            Account account;
            if ((account = accountDataHolder.get(id)) != null) {
                account.lock();
                try {
                    accountDataHolder.remove(id, account);
                    account.setDeleted(true);
                    return new OperationResponse(OperationStatus.SUCCESS, String.valueOf(id));
                } finally {
                    account.unlock();
                }
            } throw new Exception("Can not remove moneyTransfer.account with id: " + id);
        } catch (Exception e) {
            return new OperationResponse(OperationStatus.ERROR,ERROR_DELETE_ACCOUNT + " WITH ID : " +id + ". " + e.getMessage());
        }
    }

    private void innerTransfer( Account srcAccount,  Account destAccount, BigDecimal value){
        srcAccount.withdrawal(value);
        destAccount.introduction(value);
    }
}
