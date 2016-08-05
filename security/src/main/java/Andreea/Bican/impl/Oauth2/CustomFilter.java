package Andreea.Bican.impl.Oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by andre on 01.08.2016.
 */
@WebFilter("/loginGoogle")
public class CustomFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

     //   System.out.println("This is the context path ");

        Enumeration<String> enumeration = request.getHeaderNames();
        String requestURI = request.getRequestURI();
      //  System.out.println("Request uri is " + requestURI);
/*
        while(enumeration.hasMoreElements())
        {
            System.out.println(enumeration.nextElement());
        }*/
        chain.doFilter(req, res);
    }
}
