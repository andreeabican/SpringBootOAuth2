package Andreea.Bican.impl.Oauth2.Filters;

import Andreea.Bican.impl.IFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by andre on 22.06.2016.
 */
public class CSRFHeaderFilter implements IFilter{


    @Bean(name="csrfHeaderFilter")
    public Filter createFilter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {
                CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
// Spring Security will allow the Token to be included in this header name
                if(token != null) {
                        response.setHeader("X-CSRF-HEADER", token.getHeaderName());
// Spring Security will allow the token to be included in this parameter name
                        response.setHeader("X-CSRF-PARAM", token.getParameterName());
// this is the value of the token to be included as either a header or an HTTP parameter
                        response.setHeader("X-CSRF-TOKEN", token.getToken());
                }
                CsrfToken csrf = (CsrfToken) request
                        .getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token2 = csrf.getToken();
                    if (cookie == null
                            || token2 != null
                            && !token2.equals(cookie.getValue())) {
                        cookie = new Cookie("XSRF-TOKEN", token2);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }
}
