package Andreea.Bican.BrowserViews;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 21.06.2016.
 */
@RestController
public class GreetingView {

    @RequestMapping("/greeting")
    public String greeting() {
        return "Hello";
    }
}
