package uploader;


import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
/**
 * Created by oscar on 22/03/2017.
 */
public class Oc {
    private static String getConfig(String uri) throws IOException {
        String result = "";
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("admin","admin"));
        CloseableHttpClient httpClient =
                HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        HttpGet get = new HttpGet(uri);
        HttpResponse response = httpClient.execute(get);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            result+=line;
            System.out.println(line);
        }
        return result;
    }

    public static int getNumCotiCC(){
        try {
            return Integer.parseInt(getConfig("http://www.gemobile.com.mx/config/NUM_COTI_CC"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getSmartUser(){
        try {
            return getConfig("http://www.gemobile.com.mx/config/USER");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "18VIDI18091363235";
    }

    public static String getSmartPass(){
        try {
            return getConfig("http://www.gemobile.com.mx/config/PASS");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "573262631";
    }
}
