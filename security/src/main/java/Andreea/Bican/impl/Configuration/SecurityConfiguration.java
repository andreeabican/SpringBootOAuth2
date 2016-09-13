package Andreea.Bican.impl.Configuration;

import Andreea.Bican.impl.Oauth2.FacebookAuthentication.FacebookFilter;
import Andreea.Bican.impl.Oauth2.Filters.CSRFHeaderFilter;
import Andreea.Bican.impl.Oauth2.Filters.ProvidersCompositeFilter;
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
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.saml.SAMLAuthenticationProvider;
import org.springframework.security.saml.SAMLEntryPoint;
import org.springframework.security.saml.metadata.MetadataGeneratorFilter;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Configuration
@Import({ FacebookFilter.class, GoogleFilter.class, ProvidersCompositeFilter.class, CSRFHeaderFilter.class, OneLoginFilter.class})
@EnableOAuth2Client
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("facebook")
    OAuth2ClientAuthenticationProcessingFilter facebookFilter;

    @Autowired
    @Qualifier("google")
    OAuth2ClientAuthenticationProcessingFilter googleFilter;

   @Autowired
    @Qualifier("csrfHeaderFilter")
    Filter csrfHeaderFilter;

    @Autowired
    @Qualifier("samlEntryPoint")
    SAMLEntryPoint samlEntryPoint;

    @Autowired
    @Qualifier("samlFilter")
    FilterChainProxy samlFilter;

    @Autowired
    @Qualifier("metadataFilter")
    MetadataGeneratorFilter metadataGeneratorFilter;

    @Autowired
    @Qualifier("samlAuthenticationProvider")
    SAMLAuthenticationProvider authenticationProvider;


    public CompositeFilter compositeFilter(){
        CompositeFilter compositeFilter = new CompositeFilter();
        ArrayList<Filter> list = new ArrayList<>();
    //    FacebookFilter facebookFilter = new FacebookFilter();
        list.add(facebookFilter);

       // GoogleFilter googleFilter = new GoogleFilter();
        list.add(googleFilter);
        list.add(metadataGeneratorFilter);
        CompositeFilter filter = new CompositeFilter();
        filter.setFilters(list);
        return filter;
    }

    public CompositeFilter afterCompositeFilter() throws Exception {
        CompositeFilter compositeFilter = new CompositeFilter();
        ArrayList<Filter> list = new ArrayList<>();
        list.add(csrfHeaderFilter);
        list.add(samlFilter);
        compositeFilter.setFilters(list);
        return compositeFilter;
    }

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
                .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                .addFilterAfter(afterCompositeFilter(), CsrfFilter.class)
                .addFilterBefore(compositeFilter(), BasicAuthenticationFilter.class);
                    //.userDetailsService((UserDetailsService) detailsService);
            //        .authenticationProvider(samlAuthenticationProvider())

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

    @Bean(name="listOfFilters")
    public ArrayList<Filter> createListOfFilters(){
        return new ArrayList<Filter>();
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