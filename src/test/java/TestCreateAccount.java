import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.commands.Command;
import moneyTransfer.techprocess.commands.CommandCreate;
import org.junit.Assert;
import org.junit.Test;


public class TestCreateAccount extends TestScenario {
    @Test
    public void testSteps() throws Exception {
        String value = "1000";
        OperationResponse result = executeCommandQuery(Command.COMMAND + "=" + CommandCreate.NAME + "&" + Command.VALUE +"="+ value);
        Assert.assertEquals(OperationStatus.SUCCESS, result.getOperationStatus());
    }
}
