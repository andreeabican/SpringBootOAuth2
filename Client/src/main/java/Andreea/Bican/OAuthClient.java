/**
 * Created by andre on 31.07.2016.
 */
package Andreea.Bican;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class OAuthClient {

    private static final String targetURL = "http://localhost:8181/loginGoogle";

    public static void main(String[] args) throws IOException {

        if (args.length > 2) {
            OAuthApplication.main(args);
        }
        try {

            URL restServiceURL = new URL(targetURL);

            HttpURLConnection httpConnection = (HttpURLConnection) restServiceURL.openConnection();
            httpConnection.setRequestMethod("GET");

            if (httpConnection.getResponseCode() != 200) {
                throw new RuntimeException("HTTP GET Request Failed with Error code : "
                        + httpConnection.getResponseCode());
            }

            BufferedReader responseBuffer = new BufferedReader(new InputStreamReader(
                    (httpConnection.getInputStream())));

            String output;
            System.out.println("Output from Server:  \n");

            while ((output = responseBuffer.readLine()) != null) {
                System.out.println(output);
            }

            httpConnection.disconnect();

        } catch (MalformedURLException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }
    }
}
