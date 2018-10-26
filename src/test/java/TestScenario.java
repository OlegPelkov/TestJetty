
import com.google.gson.Gson;
import moneyTransfer.techprocess.commands.Command;
import moneyTransfer.techprocess.commands.CommandCreate;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import moneyTransfer.AccountServer;
import moneyTransfer.messages.OperationResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Ignore
public class TestScenario {

    protected static final String url = "http://127.0.0.1:8081/moneyTransfer?";
    protected CloseableHttpClient client;
    protected Gson gson;

    protected boolean casMode = true;

    @Before
    public void setup() {
        try {
            client =  HttpClients.createDefault();
            gson = new Gson();
            AccountServer.getInstance().setCas(casMode);
            AccountServer.getInstance().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSteps() throws Exception {
    }

    @After
    public void clear() {
        try {
            AccountServer.getInstance().stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected OperationResponse executeCommandQuery(String commandQuery) throws IOException {
        HttpGet request = new HttpGet(url + commandQuery);
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        StringBuffer sb = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        return gson.fromJson(sb.toString(), OperationResponse.class);
    }

}