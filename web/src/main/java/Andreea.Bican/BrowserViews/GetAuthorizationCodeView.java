package Andreea.Bican.BrowserViews;

import Andreea.Bican.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 29.06.2016.
 */
@RestController
public class GetAuthorizationCodeView {

    @Autowired
    private TokenService tokenService;

    @RequestMapping("/getGoogleCode")
    public String log() throws Exception {
        return tokenService.getGoogleAuthorizationCode();
    }
}
