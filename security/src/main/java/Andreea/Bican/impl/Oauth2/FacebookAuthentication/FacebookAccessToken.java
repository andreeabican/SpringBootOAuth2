package Andreea.Bican.impl.Oauth2.FacebookAuthentication;

import Andreea.Bican.impl.AccessToken;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by andre on 21.06.2016.
 */
public class FacebookAccessToken implements AccessToken{

    private final String USER_AGENT = "Mozilla/5.0";

    private final String URL = "graph.facebook.com/debug_token?input_token=";

    public String sendGet(String requestMessage) throws Exception {

        URL obj = new URL(requestMessage);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        String responseMessage = con.getResponseMessage();
        System.out.println("\nSending 'GET' request to URL : " + requestMessage);
        System.out.println("Response Code : " + responseCode);
        return responseMessage;
    }

    public boolean checkToken(String token) throws Exception {
        String responseMessage = sendGet(URL + token);
        if(responseMessage.equals("OK")) {
            return true;
        }
        return false;
    }
}
