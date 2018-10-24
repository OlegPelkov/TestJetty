package techprocess;

import account.Account;
import account.AccountImpl;
import data.AccountDataHolderImpl;

import java.math.BigDecimal;

import static messages.Messages.*;

public class AccountOperationHandlerImpl implements AccountOperationHandler {

    AccountDataHolderImpl accountDataHolder = AccountDataHolderImpl.getInstance();

    @Override
    public String transfer(long sourceId, long destinationId, BigDecimal value) {
        Account srcAccount = accountDataHolder.get(sourceId);
        Account destAccount = accountDataHolder.get(destinationId);
        if(srcAccount==null){
            return sourceId + ACCOUNT_NOT_FOUND;
        }
        if(destAccount==null){
            return destinationId + ACCOUNT_NOT_FOUND;
        }
        srcAccount.lock();
        try {
            if(srcAccount.isDeleted()){
                return sourceId + ACCOUNT_NOT_FOUND;
            }
            if (!(srcAccount.getCurrentValue().compareTo(value) < 0)) {
                destAccount.lock();
                try {
                    if(destAccount.isDeleted()){
                        return destinationId + ACCOUNT_NOT_FOUND;
                    }
                    srcAccount.widthrawal(value);
                    destAccount.introduction(value);
                } finally {
                    destAccount.unlock();
                }

            } else {
                return sourceId + NOT_ENOUGH_MONEY + " CURRENT VALUE : " + srcAccount.getCurrentValue();
            }
        } finally {
            srcAccount.unlock();
        }
        return SUCCESS_TRANSFER_MONEY +" from : " + sourceId + " to : " + destinationId + " value : " + value;
    }

    @Override
    public String createNewAccount(BigDecimal initialValue) {
        try {
            long nextId = IdCounter.getInstance().getNextId();
            if (accountDataHolder.get(nextId) == null) {
                accountDataHolder.put(nextId, new AccountImpl(nextId, initialValue));
                return String.valueOf(nextId);
            } throw new Exception("Can not create account with id: " + nextId);
        } catch (Exception e) {
            return ERROR_CREATE_ACCOUNT + " " + e.getMessage();
        }
    }

    @Override
    public String deleteAccount(long id) {
        try {
            Account account;
            if ((account = accountDataHolder.get(id)) != null) {
                account.lock();
                try {
                    accountDataHolder.remove(id, account);
                    account.setDeleted(true);
                    return String.valueOf(id);
                } finally {
                    account.unlock();
                }
            } throw new Exception("Can not remove account with id: " + id);
        } catch (Exception e) {
            return ERROR_DELETE_ACCOUNT + " " + e.getMessage();
        }
    }
}
