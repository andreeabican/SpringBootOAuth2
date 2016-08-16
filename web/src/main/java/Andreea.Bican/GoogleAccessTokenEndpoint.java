package Andreea.Bican;

import Andreea.Bican.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 14.07.2016.
 */
@RestController
public class GoogleAccessTokenEndpoint {

    @Autowired
    TokenService tokenService;

    @RequestMapping("/loginGoogle")
    public String getTokenAuto() throws Exception {

        String token = tokenService.getAccessTokenByGoogleCredential();
        String email = tokenService.getEmailFromGoogleAccessToken(token);
        String username = tokenService.getUsernameFromRepository(email);

        String output = "Output: " + "\n Token " + token + "\n Email " + email + "\n Username " + username;

        return output;
    }
}
