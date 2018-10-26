import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.commands.Command;
import moneyTransfer.techprocess.commands.CommandCreate;
import moneyTransfer.techprocess.commands.CommandTransfer;
import org.junit.Assert;
import org.junit.Test;

import static moneyTransfer.messages.Messages.INVALID_PARAMETER;


public class TestTransferMoneyIdenticalId extends TestScenario {

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

        // transfer attempt 1 from srcАccount to srcАccount
        OperationResponse trancferResult1 = executeCommandQuery(Command.COMMAND + "=" + CommandTransfer.NAME + "&" +
                Command.SRC_ID + "=" + createResultSrcАccount.getMsg() + "&" + Command.DEST_ID + "=" +  createResultSrcАccount.getMsg() + "&" +
                Command.VALUE + "=" +transferValue);
        Assert.assertEquals(OperationStatus.ERROR, trancferResult1.getOperationStatus());
        Assert.assertTrue(trancferResult1.getMsg().contains(INVALID_PARAMETER));
    }
}
