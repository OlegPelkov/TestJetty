import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.commands.Command;
import moneyTransfer.techprocess.commands.CommandCreate;
import moneyTransfer.techprocess.commands.CommandDelete;
import org.junit.Assert;
import org.junit.Test;


public class TestDeleteAccount extends TestScenario {

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
    }
}
