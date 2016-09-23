package Andreea.Bican.impl.Configuration;

import Andreea.Bican.impl.Oauth2.FacebookAuthentication.FacebookFilter;
import Andreea.Bican.impl.Oauth2.Filters.CSRFHeaderFilter;
import Andreea.Bican.impl.Oauth2.Filters.FilterAfter;
import Andreea.Bican.impl.Oauth2.Filters.FilterBefore;
import Andreea.Bican.impl.Oauth2.GoogleAuthentication.GoogleFilter;
import Andreea.Bican.impl.SAML.OneLoginAuthentication.OneLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLEntryPoint;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@Import({ FacebookFilter.class, GoogleFilter.class, FilterBefore.class, CSRFHeaderFilter.class, OneLoginFilter.class, FilterAfter.class})
@EnableOAuth2Client
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("samlEntryPoint")
    SAMLEntryPoint samlEntryPoint;

    @Autowired
    @Qualifier("samlAuthenticationProvider")
    SAMLAuthenticationProvider authenticationProvider;

    @Autowired
    @Qualifier("filterBefore")
    CompositeFilter filterBefore;

    @Autowired
    @Qualifier("filterAfter")
    CompositeFilter filterAfter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .authenticationEntryPoint(samlEntryPoint);
        http
                .antMatcher("/**").authorizeRequests()
                .antMatchers("/", "/login**", "/webjars/**", "/saml**").permitAll()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .permitAll()
                .and()
                .addFilterBefore(filterBefore, ChannelProcessingFilter.class)
                .addFilterAfter(filterAfter, BasicAuthenticationFilter.class);

                    
       http.csrf().disable();
    }
    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }


    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider);
    }

    @Bean(name="listOfFiltersBefore")
    public ArrayList<Filter> createListOfFiltersBefore(){
        return new ArrayList<Filter>();
    }

    @Bean(name="listOfFiltersAfter")
    public ArrayList<Filter> createListOfFiltersAfter()  { return new ArrayList<Filter>();  }
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

    /**
     * Returns the authentication manager currently used by Spring.
     * It represents a bean definition with the aim allow wiring from
     * other classes performing the Inversion of Control (IoC).
     *
     * @throws  Exception
     */
    @Bean(name="authenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



}