package Andreea.Bican.BrowserViews;

import Andreea.Bican.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 14.07.2016.
 */
@RestController
public class GetToken {

    @Autowired
    TokenService tokenService;

    @RequestMapping("/loginGoogle")
    public String getTokenAuto() throws Exception {
        return output = "Here is your access token :" + tokenService.getAccessTokenByGoogleCredential();
    }
}
