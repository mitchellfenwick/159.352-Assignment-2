/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_test_19037806;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
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
 *
 * @author Mitch
 */
public class MoveTest {
    public static void main(String [] args) throws Exception {
        startGame();
    }
    
    private static void startGame() throws Exception {
        // Initialise a game session
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
            .setScheme("http")
            .setHost("localhost:8080")
            .setPath("/ttt/istart")
            .build();
        HttpPost httpPost = new HttpPost(uri);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            // Send a move request
            testMove(httpclient, response);
        } finally {
            response.close();
        }
    } 

    private static void testMove(CloseableHttpClient httpclient, CloseableHttpResponse response) throws URISyntaxException, IOException {
        URI uri2 = new URIBuilder()
                .setScheme("http")
                .setHost("localhost:8080")
                .setPath("/ttt/move/x1y1")
                .build();
        HttpPost httpPost2 = new HttpPost(uri2);
        CloseableHttpResponse response2 = httpclient.execute(httpPost2);
        try {
            String contentLength = response2.getHeaders("Content-Length")[0].toString();
            // Assert that status code recieved is 200 OK
            assertEquals(HttpStatus.SC_OK, response2.getStatusLine().getStatusCode());
            // Assert that the request is not recieving any content back from the server
            assertEquals("Content-Length: 0", contentLength);
        } finally {
            response2.close();
        }
    }
}
