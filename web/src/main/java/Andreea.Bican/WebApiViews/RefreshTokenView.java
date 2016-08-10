package Andreea.Bican.WebApiViews;

import Andreea.Bican.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 07.07.2016.
 */
@RestController
public class RefreshTokenView {

    @Autowired
    TokenService tokenService;

    @RequestMapping("/getGoogleRefreshToken")
    public String token(@RequestHeader(value = "code")String code) throws Exception {

        String refreshToken = tokenService.getGoogleRefreshToken(code);
        String token = tokenService.getGoogleAccessTokenByRefreshToken(refreshToken);
        String email = tokenService.getEmailFromGoogleAccessToken(token);

        String output = "Refresh token: " + refreshToken +
                "\n Access token " + token +
                "\n Email " + email;

        return  output;
    }
}
