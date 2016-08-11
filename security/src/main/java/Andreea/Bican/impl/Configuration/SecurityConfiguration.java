package Andreea.Bican.impl.Configuration;

import Andreea.Bican.User;
import Andreea.Bican.impl.CurrentUsersServiceImpl;
import Andreea.Bican.impl.Oauth2.AutoFilter;
import Andreea.Bican.impl.Oauth2.FacebookAuthentication.FacebookFilter;
import Andreea.Bican.impl.Oauth2.Filters.CSRFHeaderFilter;
import Andreea.Bican.impl.Oauth2.Filters.ProvidersCompositeFilter;
import Andreea.Bican.impl.Oauth2.GoogleAuthentication.GoogleFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.HashMap;


@Configuration
@Import({FacebookFilter.class, GoogleFilter.class, ProvidersCompositeFilter.class, AutoFilter.class, CSRFHeaderFilter.class})
@EnableOAuth2Client
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("csrfHeaderFilter")
    Filter csrfHeaderFilter;

    @Autowired
    @Qualifier("compositeFilter")
    CompositeFilter compositeFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**").permitAll()
                .antMatchers("/userAndSession/**").authenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .permitAll()
                .and()
                     .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                    .addFilterAfter(csrfHeaderFilter, CsrfFilter.class)
                    .addFilterBefore(compositeFilter, BasicAuthenticationFilter.class);
    }

    @Bean(name = "listOfUsers")
    public HashMap<String, User> createListOfUsers(){
        return new HashMap<String, User>();
    }


    @Bean(name="listOfFilters")
    public ArrayList<Filter> createListOfFilters(){
        return new ArrayList<Filter>();
    }

    @Bean(name="listOfUsersAndSessionIds")
    public HashMap<String, String> createListOfUsersAndIds(){ return new HashMap<String, String>(); }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
    /*
    * Spring uses HttpSessionCsrfTokenRepository which by default
    * gives header name for CSRF as X-XSRF-TOKEN, but in order to use
    * Angular it needs to be converted to X-XSRF-TOKEN
     */
    private CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
}