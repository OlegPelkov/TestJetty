
import com.google.gson.Gson;
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
import java.io.InputStreamReader;

@Ignore
public class TestScenario {

    protected static final String url = "http://127.0.0.1:8081/moneyTransfer?";
    protected CloseableHttpClient client;
    protected Gson gson;

    @Before
    public void setup() {
        try {
            client =  HttpClients.createDefault();
            gson = new Gson();
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

}