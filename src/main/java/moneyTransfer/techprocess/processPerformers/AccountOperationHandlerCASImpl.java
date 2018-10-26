package moneyTransfer.techprocess.processPerformers;

import moneyTransfer.account.Account;
import moneyTransfer.account.AccountCASImpl;
import moneyTransfer.data.AccountDataHolderImpl;
import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.IdCounter;

import java.math.BigDecimal;

import static moneyTransfer.messages.Messages.*;

public class AccountOperationHandlerCASImpl implements AccountOperationHandler {
    /**
     * Use fo accounts witch implements CAS algorithm for for introduction and withdrawal
     **/
    private AccountDataHolderImpl accountDataHolder = AccountDataHolderImpl.getInstance();

    @Override
    public OperationResponse transfer(long sourceId, long destinationId, BigDecimal value) {
        Account srcAccount = accountDataHolder.get(sourceId);
        Account destAccount = accountDataHolder.get(destinationId);
        if (srcAccount == null) {
            return new OperationResponse(OperationStatus.ERROR, "FOR ID : " + sourceId + ACCOUNT_NOT_FOUND);
        }
        if (destAccount == null) {
            return new OperationResponse(OperationStatus.ERROR, "FOR ID : " + destinationId + ACCOUNT_NOT_FOUND);
        }
        return innerTransfer(srcAccount, destAccount, value);
    }

    @Override
    public OperationResponse createNewAccount(BigDecimal initialValue) {
        try {
            long nextId = IdCounter.getInstance().getNextId();
            if (accountDataHolder.get(nextId) == null) {
                accountDataHolder.put(nextId, new AccountCASImpl(nextId, initialValue));
                return new OperationResponse(OperationStatus.SUCCESS, String.valueOf(nextId));
            }
            throw new Exception("Can not create account with id: " + nextId);
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
            } throw new Exception("Can not remove account with id: " + id);
        } catch (Exception e) {
            return new OperationResponse(OperationStatus.ERROR,ERROR_DELETE_ACCOUNT + " WITH ID : " +id + ". " + e.getMessage());
        }
    }

    private OperationResponse innerTransfer(Account srcAccount, Account destAccount, BigDecimal value) {
        //try withdrawal
        int withdrawalResult = srcAccount.withdrawal(value);
        if (withdrawalResult < 0) {
            return new OperationResponse(OperationStatus.ERROR, "FOR ID : " + srcAccount.getId() + ACCOUNT_NOT_FOUND);
        }
        if (withdrawalResult == 0) {
            return new OperationResponse(OperationStatus.ERROR, "ON ACCOUNT : " + srcAccount + NOT_ENOUGH_MONEY + " CURRENT VALUE : " + srcAccount.getCurrentValue());
        }
        //if withdrawal success, try introduction
        int introductionResult = destAccount.introduction(value);
        //if introduction account was deleted success, try refund money to srcAccount
        if (introductionResult < 0) {
            int refundResult = srcAccount.introduction(value);
            if (refundResult < 0) {
                return new OperationResponse(OperationStatus.ERROR, "FOR ID : " + srcAccount.getId() + " AND FOR ID :" + destAccount.getId() + ACCOUNT_NOT_FOUND);
            } else {
                //refund money success
                return new OperationResponse(OperationStatus.ERROR, "FOR ID : " + destAccount.getId() + ACCOUNT_NOT_FOUND);
            }
        } else {
            //transfer money success
            return new OperationResponse(OperationStatus.SUCCESS, SUCCESS_TRANSFER_MONEY + " from : " + srcAccount.getId() + " to : " + destAccount.getId() + " value : " + value);
        }
    }
}
