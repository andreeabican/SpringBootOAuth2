package Andreea.Bican.impl;

import Andreea.Bican.ClientAppDetails;
import Andreea.Bican.TokenService;
import Andreea.Bican.User;
import com.google.api.client.auth.oauth2.TokenResponseException;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by andre on 03.07.2016.
 */
@Service
public class TokenServiceImpl implements TokenService {

    List<String> scopes;
    HttpTransport transport;
    JsonFactory jsonFactory;
    private final String USER_AGENT = "Mozilla/5.0";

    private final String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=";

    @Autowired
    @Qualifier("listOfUsers")
    HashMap<String, User> loggedUsersList;


    @Override
    public String getGoogleAuthorizationCode() throws Exception {
        System.out.println("\nSending 'GET' request to URL : ");
        List<String> scopes = new ArrayList<String>();
        scopes.add("email");

        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory,
                ClientAppDetails.getGoogleClientId(),
                ClientAppDetails.getGoogleClientSecret(), scopes).build();
        GoogleAuthorizationCodeRequestUrl url = flow.newAuthorizationUrl();
        url.setRedirectUri("http://localhost:8181");
        url.setApprovalPrompt("force");
        url.setAccessType("offline");

        String authorize_url = url.build();

        // paste into browser to get code
        System.out.println("Put this url into your browser and paste in the access token:");
        System.out.println(authorize_url);
        return authorize_url;
    }

    @Override
    public String getGoogleAccessToken(String code) throws IOException {

        scopes = new ArrayList<String>();
        scopes.add("email");
        transport = new NetHttpTransport();
        jsonFactory = new JacksonFactory();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory,
                ClientAppDetails.getGoogleClientId(), ClientAppDetails.getGoogleClientSecret(), scopes).build();
        GoogleTokenResponse res = flow.newTokenRequest(code).setRedirectUri("http://localhost:8181").execute();
        System.out.println("Refresh access token:");
        System.out.println(res.getRefreshToken());

        String accessToken = res.getAccessToken();
        System.out.println("Token expires in " + res.getExpiresInSeconds());

        System.out.println("access:");
        System.out.println(accessToken);

        return accessToken;
    }

    @Override
    public String getGoogleRefreshToken(String code) throws IOException {

        scopes = new ArrayList<String>();
        scopes.add("email");
        transport = new NetHttpTransport();
        jsonFactory = new JacksonFactory();
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory,
                ClientAppDetails.getGoogleClientId(), ClientAppDetails.getGoogleClientSecret(), scopes).build();
        GoogleTokenResponse res = flow.newTokenRequest(code).setRedirectUri("http://localhost:8181").execute();
        System.out.println("Refresh access token:");
        String refreshToken = res.getRefreshToken();
        System.out.println(refreshToken);

        return refreshToken;
    }

    @Override
    public String getGoogleAccessTokenByRefreshToken(String refreshToken) throws IOException {
        scopes = new ArrayList<String>();
        scopes.add("email");
        transport = new NetHttpTransport();
        jsonFactory = new JacksonFactory();

        GoogleTokenResponse res = new GoogleTokenResponse();
        res.setRefreshToken(refreshToken);

        GoogleRefreshTokenRequest refreshTokenRequest = new GoogleRefreshTokenRequest(transport, jsonFactory, refreshToken,
              ClientAppDetails.getGoogleClientId(), ClientAppDetails.getGoogleClientSecret());
        GoogleTokenResponse googleTokenResponse = refreshTokenRequest.execute();
        String accessToken = googleTokenResponse.getAccessToken();
        return accessToken;
    }

    public String sendGet(String url) throws Exception {

        java.net.URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();
        String responseMessage = con.getResponseMessage();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        return responseMessage;
    }

    @Override
    public String getAccessTokenByGoogleCredential() {
        scopes = new ArrayList<String>();
        jsonFactory = new JacksonFactory();
        transport = new NetHttpTransport();

        scopes.add("email");
        scopes.add("profile");

        try {
            GoogleCredential credential = new GoogleCredential.Builder()
                    .setTransport(transport)
                    .setJsonFactory(jsonFactory)
                    .setServiceAccountId(ClientAppDetails.getServiceAccountId())
                    .setServiceAccountScopes(scopes)
                    .setServiceAccountPrivateKeyFromP12File(new File(ClientAppDetails.getP12FileName()))
                    .build();
            credential.refreshToken();
            String token = credential.getAccessToken();
            getEmailFromGoogleAccessToken(token);
            return  token;
        }catch (TokenResponseException tokenResponseException){
            return "The credentials are wrong";
        }catch (FileNotFoundException fileNotFoundException){
            return "The file is not found";
        } catch (GeneralSecurityException e) {
            return "General Security Exception";
        } catch (IOException e) {
            return "IO Exception";
        } catch (ParseException e) {
            return "Couldn't parse the information from token";
        }
    }

    public boolean checkToken(String token) throws Exception {
        User user = loggedUsersList.get(token);
        String URL = null;
        if(user.getProvider() != null) {
            if (user.getProvider().equals("Facebook")) {
                URL = "graph.facebook.com/debug_token?input_token=" + token;
            } else if (user.getProvider().equals("Google")) {
                URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + token;
            }
        }else{
            return false;
        }
        String responseMessage = sendGet(URL);
        if(responseMessage.equals("OK")) {
            return true;
        }
        return false;
    }

    @Override
    public void storeEmailAndRefreshToken(String email, String refreshToken) {

    }

    @Override
    public String getEmailFromGoogleAccessToken(String token) throws IOException, ParseException {

        URL obj = new URL(url + token);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        con.connect();
        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line+"\n");
        }
        br.close();
        System.out.println(sb);

        JSONParser parser = new JSONParser();
        Object object = parser.parse(String.valueOf(sb));
        JSONObject jsonObject = (JSONObject) object;
        String email = (String)jsonObject.get("email");
        return (String) jsonObject.get("email");
    }
}
