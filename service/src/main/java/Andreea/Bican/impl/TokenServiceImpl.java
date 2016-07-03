package Andreea.Bican.impl;

import Andreea.Bican.TokenService;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andre on 03.07.2016.
 */
@Service
public class TokenServiceImpl implements TokenService {

    List<String> scopes;
    HttpTransport transport;
    JsonFactory jsonFactory;

    @Override
    public String getGoogleAuthorizationCode() throws Exception {
        System.out.println("\nSending 'GET' request to URL : ");
        List<String> scopes = new ArrayList<String>();
        scopes.add("email");

        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory,
                "client_id",
                "client_secret", scopes).build();
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
    public String getAccessToken(String code) throws IOException {

        scopes = new ArrayList<String>();
        scopes.add("email");
        transport = new NetHttpTransport();
        jsonFactory = new JacksonFactory();

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(transport, jsonFactory,
                "client_id", "client_secret", scopes).build();
        GoogleTokenResponse res = flow.newTokenRequest(code).setRedirectUri("http://localhost:8181").execute();
        String accessToken = res.getAccessToken();
        System.out.println("Token expires in " + res.getExpiresInSeconds());

        System.out.println("access:");
        System.out.println(accessToken);

        return accessToken;
    }
}
