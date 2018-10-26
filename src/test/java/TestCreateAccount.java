import moneyTransfer.messages.OperationResponse;
import moneyTransfer.messages.OperationStatus;
import moneyTransfer.techprocess.commands.Command;
import moneyTransfer.techprocess.commands.CommandCreate;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class TestCreateAccount extends TestScenario {
    @Test
    public void testSteps() throws Exception {
        String value = "1000";
        HttpGet request = new HttpGet(url + Command.COMMAND + "=" + CommandCreate.NAME + "&" + Command.VALUE +"="+ value);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        StringBuffer sb = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        OperationResponse result = gson.fromJson(sb.toString(), OperationResponse.class);
        Assert.assertEquals(OperationStatus.SUCCESS, result.getOperationStatus());
        Assert.assertEquals("1", result.getMsg());
    }
}
