package uploader;


import modelo.entidades.Cliente;
import org.apache.commons.httpclient.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.commons.httpclient.Credentials;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CookieStore;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by oscar on 05/10/2016.
 */
public class Uploader {

    public static void insertarCliente(Cliente cliente) throws IOException {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("admin","admin"));
        CloseableHttpClient httpClient =
                HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
            HttpPost post = new HttpPost("http://gemobile.jl.serv.net.mx/clientes");
        System.out.println(cliente.getJsonString());
        post.setEntity(cliente.getJsonString());
        HttpResponse response = httpClient.execute(post);
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
            System.out.println(line);
        }
    }
}
