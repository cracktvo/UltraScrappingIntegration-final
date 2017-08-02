package jentities;

import modelo.entidades.Cliente;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import uploader.Uploader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by oscar on 05/10/2016.
 */
public class uploaderTest {

    public static void main(String args[]) throws IOException {
        Uploader uploader = new Uploader();
        uploader.insertarCliente(new Cliente("5539007928"));
    }
}
