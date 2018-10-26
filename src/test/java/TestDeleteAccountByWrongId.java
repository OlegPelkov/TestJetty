import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.commands.Command;
import moneyTransfer.techprocess.commands.CommandCreate;
import moneyTransfer.techprocess.commands.CommandDelete;
import org.junit.Assert;
import org.junit.Test;

import static moneyTransfer.messages.Messages.ERROR_DELETE_ACCOUNT;


public class TestDeleteAccountByWrongId extends TestScenario {

    @Test
    public void testSteps() throws Exception {
        //when
        String value = "1000";
        OperationResponse createResult = executeCommandQuery(Command.COMMAND + "=" + CommandCreate.NAME + "&" + Command.VALUE +"="+ value);
        Assert.assertEquals(OperationStatus.SUCCESS, createResult.getOperationStatus());

        //then
        OperationResponse deleteResult = executeCommandQuery(Command.COMMAND + "=" + CommandDelete.NAME + "&" + Command.ID +"="+ createResult.getMsg());
        Assert.assertEquals(OperationStatus.SUCCESS, deleteResult.getOperationStatus());
        Assert.assertEquals(deleteResult.getMsg(), createResult.getMsg());

        OperationResponse deleteResult1 = executeCommandQuery(Command.COMMAND + "=" + CommandDelete.NAME + "&" + Command.ID +"="+ createResult.getMsg());
        Assert.assertEquals(OperationStatus.ERROR, deleteResult1.getOperationStatus());
        Assert.assertTrue(deleteResult1.getMsg().contains(ERROR_DELETE_ACCOUNT + " WITH ID : " +createResult.getMsg()));
    }
}

