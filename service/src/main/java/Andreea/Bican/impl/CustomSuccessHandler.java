package Andreea.Bican.impl;

/**
 * Created by andre on 02.08.2016.
 */

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;

public class CustomSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request,
                                        final HttpServletResponse response, final Authentication authentication)
            throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);

        HttpSession session = request.getSession(true);

        Principal principal = (Principal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Principal info: " + principal.getName());
    }

}
