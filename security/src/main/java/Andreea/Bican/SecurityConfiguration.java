
package Andreea.Bican;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.*;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.filter.*;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.filter.*;

import java.util.*;
import javax.servlet.*;


    @Configuration
    @EnableOAuth2Client
    public class SecurityConfiguration extends WebSecurityConfigurerAdapter
    {

        @Autowired
        OAuth2ClientContext oauth2ClientContext;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/**")
                        .authorizeRequests()
                        .antMatchers("/", "/login**", "/webjars/**")
                        .permitAll()
                    .and()
                        .logout()
                        .logoutSuccessUrl("/")
                        .permitAll()
                    .and()
                    .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
        }

        /* Redirect from the app to Facebook and Google+*/
        private Filter ssoFilter() {
            CompositeFilter filter = new CompositeFilter();
            List<Filter> filters = new ArrayList<>();

            OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google");
            OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
            googleFilter.setRestTemplate(googleTemplate);
            googleFilter.setTokenServices(new CustomUserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId()));
            filters.add(googleFilter);

            filter.setFilters(filters);
            return filter;

        }
        /*
        * The @Bean annotation tells Spring that a method will return an object that should be
        * registered as a bean in the Spring application context( in this case the OAuth2ClientContext)
        */

        @Bean
        @ConfigurationProperties("google.client")
        OAuth2ProtectedResourceDetails google(){ return new AuthorizationCodeResourceDetails(); }

        @Bean
        @ConfigurationProperties("google.resource")
        ResourceServerProperties googleResource(){ return new ResourceServerProperties(); }

        @Bean
        public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
            FilterRegistrationBean registration = new FilterRegistrationBean();
            registration.setFilter(filter);
            registration.setOrder(-100);
            return registration;
        }
    }
