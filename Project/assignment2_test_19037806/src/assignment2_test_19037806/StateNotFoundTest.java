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
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author Mitch
 */
public class StateNotFoundTest {
    public static void main(String [] args) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // Request state without initialising game session
        testStatePng(httpclient);
        testStateTxt(httpclient);
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
            // Assert status code recieved is equal to 404 Not Found
            assertEquals(HttpStatus.SC_NOT_FOUND, response3.getStatusLine().getStatusCode());
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
            // Assert status code recieved is equal to 404 Not Found
            assertEquals(HttpStatus.SC_NOT_FOUND, response2.getStatusLine().getStatusCode());
        } finally {
            response2.close();
        }
    }
}
