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
public class MoveNotFoundTest {
    public static void main(String [] args) throws Exception {
        testMove();
    }

    private static void testMove() throws URISyntaxException, IOException {
        // Try to make a move without initialiseing a session which 
        //should result in a 404 game not found error
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost:8080")
                .setPath("/ttt/move/x1y1")
                .build();
        HttpPost httpPost = new HttpPost(uri);
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            // Make sure status code recieved is equal to 404 Not Found
            assertEquals(HttpStatus.SC_NOT_FOUND, response.getStatusLine().getStatusCode());
        } finally {
            response.close();
        }
    }
}
