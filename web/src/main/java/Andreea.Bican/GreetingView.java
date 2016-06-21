package Andreea.Bican;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 21.06.2016.
 */
@RestController
public class GreetingView {

    @RequestMapping("/greeting")
    public String greeting() {
        if(CurrentUser.isAuthenticated()) {
            return "Hello user " + CurrentUser.getUserName();
        }else{
            return "Hello, annonymous";
        }
    }
}
