import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.commands.Command;
import moneyTransfer.techprocess.commands.CommandCreate;
import moneyTransfer.techprocess.commands.CommandTransfer;
import org.junit.Assert;
import org.junit.Test;


public class TestTransferMoney extends TestScenario {

    @Test
    public void testSteps() throws Exception {
        //when
        String initValue = "1000";
        String transferValue = "400";

        // create src account
        OperationResponse createResultSrcАccount = executeCommandQuery(Command.COMMAND + "=" + CommandCreate.NAME + "&" + Command.VALUE +"="+ initValue);
        Assert.assertEquals(OperationStatus.SUCCESS, createResultSrcАccount.getOperationStatus());

        // create dest account
        OperationResponse createResultDestАccount = executeCommandQuery(Command.COMMAND + "=" + CommandCreate.NAME + "&" + Command.VALUE +"="+ initValue);
        Assert.assertEquals(OperationStatus.SUCCESS, createResultDestАccount.getOperationStatus());

        //then

        // transfer attempt 1
        OperationResponse trancferResult1 = executeCommandQuery(Command.COMMAND + "=" + CommandTransfer.NAME + "&" +
                Command.SRC_ID + "=" + createResultSrcАccount.getMsg() + "&" + Command.DEST_ID + "=" +  createResultDestАccount.getMsg() + "&" +
                Command.VALUE + "=" +transferValue);
        Assert.assertEquals(OperationStatus.SUCCESS, trancferResult1.getOperationStatus());
        Assert.assertTrue(trancferResult1.getMsg().contains(" from : " + createResultSrcАccount.getMsg() + " to : " + createResultDestАccount.getMsg()
                + " value : " + transferValue));

        // transfer attempt 2
        OperationResponse trancferResult2 = executeCommandQuery(Command.COMMAND + "=" + CommandTransfer.NAME + "&" +
                Command.SRC_ID + "=" + createResultSrcАccount.getMsg() + "&" + Command.DEST_ID + "=" +  createResultDestАccount.getMsg() + "&" +
                Command.VALUE + "=" +transferValue);
        Assert.assertEquals(OperationStatus.SUCCESS, trancferResult2.getOperationStatus());
        Assert.assertTrue(trancferResult2.getMsg().contains(" from : " + createResultSrcАccount.getMsg() + " to : " + createResultDestАccount.getMsg()
                + " value : " + transferValue));

        // transfer attempt 3 - will be error
        OperationResponse trancferResult3 = executeCommandQuery(Command.COMMAND + "=" + CommandTransfer.NAME + "&" +
                Command.SRC_ID + "=" + createResultSrcАccount.getMsg() + "&" + Command.DEST_ID + "=" +  createResultDestАccount.getMsg() + "&" +
                Command.VALUE + "=" +transferValue);
        Assert.assertEquals(OperationStatus.ERROR, trancferResult3.getOperationStatus());
        Assert.assertTrue(trancferResult3.getMsg().contains(createResultSrcАccount.getMsg() + " Not enough money. CURRENT VALUE : " + "200"));
    }
}
