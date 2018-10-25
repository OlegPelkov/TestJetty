
import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import test.AccountServer;
import test.messages.OperationResponse;

import java.io.BufferedReader;
import java.io.InputStreamReader;

//@Ignore
public class TestScenario {

    private static final String url = "http://127.0.0.1:8081/test?";
    private CloseableHttpClient client;
    private Gson gson;

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
        HttpGet request = new HttpGet(url+"command=create&value=1000");
        HttpResponse response = client.execute(request);
        BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        StringBuffer sb = new StringBuffer();
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        OperationResponse result = gson.fromJson(sb.toString(), OperationResponse.class);
        System.out.println(result);
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