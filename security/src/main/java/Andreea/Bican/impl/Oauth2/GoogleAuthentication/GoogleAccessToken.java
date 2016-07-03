package Andreea.Bican.impl.Oauth2.GoogleAuthentication;

import Andreea.Bican.impl.IAccessToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by andre on 21.06.2016.
 */
@Service
public class GoogleAccessToken implements IAccessToken {

    private final String USER_AGENT = "Mozilla/5.0";

    private final String URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=";

    public String sendGet(String requestMessage) throws Exception {

        URL obj = new URL(requestMessage);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);


        int responseCode = con.getResponseCode();
        String responseMessage = con.getResponseMessage();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();

        JSONParser parser = new JSONParser();
        Object object = parser.parse(String.valueOf(sb));
        JSONObject jsonObject = (JSONObject) object;
        System.out.println("Email " + jsonObject.get("email"));
        System.out.println("\nSending 'GET' request to URL : " + URL);
        System.out.println("Response Code : " + responseMessage);
        return responseMessage;
    }

    public boolean checkToken(String token) throws Exception {
        String responseMessage = sendGet(URL+token);
        if(responseMessage.equals("OK")) {
            return true;
        }
        return false;
    }
}
