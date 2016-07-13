package Andreea.Bican.WebApiViews;

import Andreea.Bican.CurrentUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * Created by andre on 13.07.2016.
 */
@RestController
public class UserAndSessionView {

    @Autowired
    CurrentUsersService currentUsersService;

    @RequestMapping(value = "/userAndSession/{providedSessionId}", method = RequestMethod.GET)
    public String getUserAndSessionId(Principal principal,
                                      HttpServletRequest httpServletRequest, @PathVariable("providedSessionId") String sessionID) {
        // Session ID
        String sessionId = httpServletRequest.getRequestedSessionId();

        System.out.println("The provided by user session id is " + sessionID);
        System.out.println("The browser session id is " + sessionId);

        String email = currentUsersService.getEmailBySessionId(sessionID);
        System.out.println("The email is " + email);
        if(sessionId.equals(sessionID)) {
            // Username
            return "This is the name " + sessionID;
        }
        return sessionId;
    }
}
