/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_test_19037806;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
/**
/**
 *
 * @author Mitch
 */
public class StateTest {
    public static void main(String [] args) throws Exception {
        startGame();
    }

    private static void startGame() throws Exception {
        // Initialise game session
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
            .setScheme("http")
            .setHost("localhost:8080")
            .setPath("/ttt/istart")
            .build();
        HttpPost httpPost = new HttpPost(uri);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            // Send request for both format=txt and format=png
            testStateTxt(httpclient);
            testStatePng(httpclient);
        } finally {
            response.close();
        }
    }

    private static void testStatePng(CloseableHttpClient httpclient) throws URISyntaxException, IOException {
        URI uri3 = new URIBuilder()
                .setScheme("http")
                .setHost("localhost:8080")
                .setPath("/ttt/state")
                .setParameter("format", "png")
                .build();
        HttpGet httpGet3 = new HttpGet(uri3);
        CloseableHttpResponse response3 = httpclient.execute(httpGet3);
        try {
            String contentType = response3.getHeaders("Content-Type")[0].toString();
            // Asssert status code recieved is 200 OK
            assertEquals(HttpStatus.SC_OK, response3.getStatusLine().getStatusCode());
            // Assert that the corrrect content type is recieved, in this case txt/html
            assertEquals("Content-Type: text/html;charset=UTF-8", contentType);
        } finally {
            response3.close();
        }
    }

    private static void testStateTxt(CloseableHttpClient httpclient) throws URISyntaxException, IOException {
        URI uri2 = new URIBuilder()
                .setScheme("http")
                .setHost("localhost:8080")
                .setPath("/ttt/state")
                .setParameter("format", "txt")
                .build();
        HttpGet httpGet = new HttpGet(uri2);
        CloseableHttpResponse response2 = httpclient.execute(httpGet);
        try {
            String contentType = response2.getHeaders("Content-Type")[0].toString();
            // Asssert status code recieved is 200 OK
            assertEquals(HttpStatus.SC_OK, response2.getStatusLine().getStatusCode());
            // Assert that the corrrect content type is recieved, in this case txt/plain
            assertEquals("Content-Type: text/plain;charset=UTF-8", contentType);
        } finally {
            response2.close();
        }
    }
}
