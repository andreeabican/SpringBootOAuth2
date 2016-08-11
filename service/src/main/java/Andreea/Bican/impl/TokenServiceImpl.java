package Andreea.Bican.impl;

import Andreea.Bican.*;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
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
    private final String USER_AGENT = "Mozilla/5.0";

    private final String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=";

    @Autowired
    UserService userService;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    public String getGoogleAuthorizationCode() throws Exception {
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
        res.getRefreshToken();
        String accessToken = res.getAccessToken();

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
        String refreshToken = res.getRefreshToken();

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
                    .setServiceAccountUser(ClientAppDetails.getServiceAccountUser())
                    .build();

            credential.refreshToken();
            String token = credential.getAccessToken();
            String email = getEmailFromGoogleAccessToken(token);

            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            User user = userService.getUser(email);
            AuthenticatedUser authUser = new AuthenticatedUser(user);
            Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, authUser.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
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

    @Override
    public String getUsernameFromRepository(String email){
        User user = userService.getUser(email);
        return user.getName();
    }

    public boolean checkToken(String token) throws Exception {

        String URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=" + token;
        String responseMessage = sendGet(URL);
        if (responseMessage.equals("OK")) {
            return true;
        }
        return false;
    }

    @Override
    public String getEmailFromGoogleAccessToken(String token) throws IOException, ParseException {

        URL obj = new URL(url + token);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        con.setRequestProperty("User-Agent", USER_AGENT);

        con.connect();
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
        String email = (String)jsonObject.get("email");

        return email;
    }

}
