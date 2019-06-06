/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package assignment2_test_19037806;

import java.net.URI;
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
public class PossibleMovesNotFoundTest {
    public static void main(String [] args) throws Exception {
        testPossiblemoves();
    }
    
    private static void testPossiblemoves() throws Exception {
        // Send a request without initialising a game session
        // Should result in 404 Not Found error
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder()
                .setScheme("http")
                .setHost("localhost:8080")
                .setPath("/ttt/possiblemoves")
                .build();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response2 = httpclient.execute(httpGet);
        try {
            // Assert status code recieved is equal to 404 Not Found
            assertEquals(HttpStatus.SC_NOT_FOUND, response2.getStatusLine().getStatusCode());
        } finally {
            response2.close();
        } //To change body of generated methods, choose Tools | Templates.
    }
}
