package Andreea.Bican.BrowserViews;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.context.request.RequestContextHolder.currentRequestAttributes;

/**
 * Created by andre on 13.07.2016.
 */
@RestController
public class JSessionIdView {

    @RequestMapping("/jsessionid")
    public String getJSessionId(){
        String jsessionId = currentRequestAttributes().getSessionId();
        if(jsessionId == null){
            return "No session id";
        }else{
            return jsessionId;
        }
    }
}
