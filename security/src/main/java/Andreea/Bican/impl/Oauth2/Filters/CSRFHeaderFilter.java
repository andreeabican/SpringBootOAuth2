package Andreea.Bican.impl.Oauth2.Filters;

import Andreea.Bican.impl.IFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.ArrayList;

/**
 * Created by andre on 22.06.2016.
 */
public class CSRFHeaderFilter implements IFilter{

    @Autowired
    @Qualifier("listOfFiltersAfter")
    ArrayList<Filter> listOfFilters;

    @Bean(name="csrfHeaderFilter")
    public Filter createFilter() {
        Filter filter = new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {

                CsrfToken csrf = (CsrfToken) request
                        .getAttribute(CsrfToken.class.getName());
                if (csrf != null) {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token2 = csrf.getToken();
                    if (cookie == null
                            || token2 != null
                            && !token2.equals(cookie.getValue())) {
                        cookie = new Cookie("X-XSRF-TOKEN", token2);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
        listOfFilters.add(filter);
        return  filter;
    }
}
