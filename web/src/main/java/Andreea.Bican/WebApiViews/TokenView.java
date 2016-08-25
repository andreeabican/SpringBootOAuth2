package Andreea.Bican.WebApiViews;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 01.07.2016.
 */
@RestController
public class TokenView {

    private final String URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=";

    @Autowired
    private Andreea.Bican.TokenService tokenService;


    @RequestMapping("/getGoogleAccessToken")
    public String token(@RequestHeader(value = "code")String code) throws Exception {
        String token = tokenService.getGoogleAccessToken(code);
        String email = tokenService.getEmailFromGoogleAccessToken(token);
        return token;
    }
}
