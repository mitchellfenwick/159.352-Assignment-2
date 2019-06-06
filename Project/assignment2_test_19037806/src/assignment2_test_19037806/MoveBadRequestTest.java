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
public class MoveBadRequestTest {
    public static void main(String [] args) throws Exception {
        startGame();
    }
    
    private static void startGame() throws Exception {
        // Initialise a session
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
            .setScheme("http")
            .setHost("localhost:8080")
            .setPath("/ttt/istart")
            .build();
        HttpPost httpPost = new HttpPost(uri);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            // With session initialised send a move request
            testMove(httpclient, response);
        } finally {
            response.close();
        }
    } 

    private static void testMove(CloseableHttpClient httpclient, CloseableHttpResponse response) throws URISyntaxException, IOException {
        URI uri2 = new URIBuilder()
                .setScheme("http")
                .setHost("localhost:8080")
                // Move is out of range and should result in 400 error
                .setPath("/ttt/move/x4y4")
                .build();
        HttpPost httpPost2 = new HttpPost(uri2);
        CloseableHttpResponse response2 = httpclient.execute(httpPost2);
        try {
            // Assert error recieved is equal to Bad Request status code
            assertEquals(HttpStatus.SC_BAD_REQUEST, response2.getStatusLine().getStatusCode());
        } finally {
            response.close();
        }
    }
}
