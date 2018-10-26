import moneyTransfer.AccountServlet;
import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.commands.Command;
import moneyTransfer.techprocess.commands.CommandCreate;
import moneyTransfer.techprocess.commands.CommandTransfer;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;


public class TestConcurrentTransferMoney extends TestScenario {

    private static final Logger LOG = LoggerFactory.getLogger(AccountServlet.class);

    private int poolSize = 200;
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(poolSize);

    @Test
    public void testSteps() throws Exception {

        String initValue = String.valueOf(poolSize*poolSize);
        String transferValue = String.valueOf(poolSize);

        // create src account
        OperationResponse createResultSrcАccount = executeCommandQuery(Command.COMMAND + "=" + CommandCreate.NAME + "&" + Command.VALUE +"="+ initValue);
        Assert.assertEquals(OperationStatus.SUCCESS, createResultSrcАccount.getOperationStatus());

        // create dest account
        OperationResponse createResultDestАccount = executeCommandQuery(Command.COMMAND + "=" + CommandCreate.NAME + "&" + Command.VALUE +"="+ initValue);
        Assert.assertEquals(OperationStatus.SUCCESS, createResultDestАccount.getOperationStatus());


        // Threads transfer money between two accounts. In the end money in accounts will be equally initValue.

        Runnable taskFromFirstToSecond = () -> {
            int repeatCount = 5;
            while (repeatCount > 0) {
                try {
                    executeCommandQuery(Command.COMMAND + "=" + CommandTransfer.NAME + "&" +
                            Command.SRC_ID + "=" + createResultSrcАccount.getMsg() + "&" + Command.DEST_ID + "=" + createResultDestАccount.getMsg() + "&" +
                            Command.VALUE + "=" + transferValue);
                    repeatCount--;
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }

        };

        Runnable taskFromSecondToFirst = () -> {
            int repeatCount = 5;
            while (repeatCount > 0) {
                try {
                    executeCommandQuery(Command.COMMAND + "=" + CommandTransfer.NAME + "&" +
                            Command.SRC_ID + "=" + createResultDestАccount.getMsg() + "&" + Command.DEST_ID + "=" + createResultSrcАccount.getMsg() + "&" +
                            Command.VALUE + "=" + transferValue);
                    repeatCount--;
                    Thread.sleep(100+repeatCount*10);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }

        };
        long startTime = System.currentTimeMillis();
        for(int i = 0; i<poolSize; i++) {
            executor.execute(new Thread(taskFromFirstToSecond));
            executor.execute(new Thread(taskFromSecondToFirst));
        }

        executor.shutdown();
        while (!executor.isTerminated()){
            Thread.sleep(200);
        }
        long endTime = System.currentTimeMillis();
        LOG.info("**");
        LOG.info("Performance value for cas = {} : {} ms",casMode, String.valueOf((endTime-startTime)));
        LOG.info("**");

        //then

        // transfer attempt
        // Money in accounts will be equally initValue
        OperationResponse trancferResult1 = executeCommandQuery(Command.COMMAND + "=" + CommandTransfer.NAME + "&" +
                Command.SRC_ID + "=" + createResultSrcАccount.getMsg() + "&" + Command.DEST_ID + "=" +  createResultDestАccount.getMsg() + "&" +
                Command.VALUE + "=" +initValue);
        Assert.assertEquals(OperationStatus.SUCCESS, trancferResult1.getOperationStatus());


        OperationResponse trancferResult2 = executeCommandQuery(Command.COMMAND + "=" + CommandTransfer.NAME + "&" +
                Command.SRC_ID + "=" + createResultDestАccount.getMsg()  + "&" + Command.DEST_ID + "=" +  createResultSrcАccount.getMsg() + "&" +
                Command.VALUE + "=" +initValue);
        Assert.assertEquals(OperationStatus.SUCCESS, trancferResult2.getOperationStatus());
    }
}
