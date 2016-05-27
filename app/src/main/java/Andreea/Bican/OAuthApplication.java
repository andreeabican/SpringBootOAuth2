
package Andreea.Bican;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.filter.CompositeFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

    @SpringBootApplication
    @RestController
    @EnableAuthorizationServer
    @EnableOAuth2Client
    public class OAuthApplication extends WebSecurityConfigurerAdapter {
    @Autowired
    OAuth2ClientContext oauth2ClientContext;
    UserRepository userRepository = new UserRepository();

    @RequestMapping("/user")
    public Principal user(Principal principal) throws IOException, ParseException {
        if(principal == null) {
            CurrentUser.logout();
            return null;
        }else{
            convertPrincipalObjectToJSON(principal);
            User loggedUser = getLoggedUser();
            User user = userRepository.getUser(loggedUser.getId());
            if(user != null) {
                CurrentUser.login(user);
            }else{
                loggedUser.setAuthority(false);
                CurrentUser.login(loggedUser);
            }
            return principal;
        }
    }

    @RequestMapping("/danceclasses")
    public String danceClasses() {
        if(CurrentUser.isAuthenticated()){
            if(CurrentUser.getUser().getAuthority()){
                return "Welcome to your dance class";
            }else{
                return "You currently don't have a dance class";
            }
        }else{
            return "You need to authenticate";
        }
    }

    public String convertPrincipalObjectToJSON(Principal principal) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("principal.json"), principal);
        String jsonToString = mapper.writeValueAsString(principal);
        return jsonToString;
    }

    public User getLoggedUser() throws IOException, ParseException {

        FileReader reader  = new FileReader("principal.json");
        JSONParser parser = new JSONParser();
        User user = new User();
        try{
            JSONObject jsonObject = (JSONObject)parser.parse(reader);

            JSONObject userAuthJSON = (JSONObject)jsonObject.get("userAuthentication");
            JSONObject detailsJSON = (JSONObject)userAuthJSON.get("details");
            String userName = (String)detailsJSON.get("displayName");
            user.setUserName(userName);

            JSONArray emailsJSON = (JSONArray)detailsJSON.get("emails");
            JSONObject emailJSON = (JSONObject) emailsJSON.get(0);
            String email = (String) emailJSON.get("value");
            user.setEmail(email);

            String id = (String) detailsJSON.get("id");
            user.setId(id);
            user.setUuid();
            return user;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @RequestMapping("/greeting")
    public String greeting() {
        if(CurrentUser.isAuthenticated()) {
            return "Hello user " + CurrentUser.getUser().getUserName() + ".\n"
                    + "Your email is: " + CurrentUser.getUser().getEmail() + ".\n"
                    + "Your UUID is " + CurrentUser.getUser().getId();
        }else{
           return "Hello, annonymous";
        }
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .antMatcher("/**")
                    .authorizeRequests()
                    .antMatchers("/", "/login**", "/webjars/**")
                    .permitAll()
                  //  .anyRequest()
                //   .authenticated()
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .permitAll()
                .and()
                    .csrf().csrfTokenRepository(csrfTokenRepository())
                .and()
                /* This is a filter which creates a cookie of XSRF-TOKEN type*/
                    .addFilterAfter( csrfHeaderFilter(), CsrfFilter.class)
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class);
    }

    private Filter csrfHeaderFilter() {
        return new OncePerRequestFilter() {

            @Override
            protected void doFilterInternal(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException
            {
                CsrfToken csrf = (CsrfToken) request
                        .getAttribute(CsrfToken.class.getName());
                if (csrf != null)
                {
                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
                    String token = csrf.getToken();
                    if (cookie == null
                        || token != null
                        && !token.equals(cookie.getValue()))
                    {
                        cookie = new Cookie("XSRF-TOKEN", token);
                        cookie.setPath("/");
                        response.addCookie(cookie);
                    }
                }
                filterChain.doFilter(request, response);
            }
        };
    }

            /* Redirect from the app to Facebook and Google+*/
    private Filter ssoFilter() {
        CompositeFilter filter = new CompositeFilter();
        List<Filter> filters = new ArrayList<>();

        OAuth2ClientAuthenticationProcessingFilter facebookFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/facebook");
        OAuth2RestTemplate facebookTemplate = new OAuth2RestTemplate(facebook(), oauth2ClientContext);
        facebookFilter.setRestTemplate(facebookTemplate);
        facebookFilter.setTokenServices(new UserInfoTokenServices(facebookResource().getUserInfoUri(), facebook().getClientId()));
        filters.add(facebookFilter);

        OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/google");
        OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
        googleFilter.setRestTemplate(googleTemplate);
        googleFilter.setTokenServices(new UserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId()));
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
    @ConfigurationProperties("facebook.client")
    OAuth2ProtectedResourceDetails facebook(){ return new AuthorizationCodeResourceDetails(); }

    @Bean
    @ConfigurationProperties("facebook.resource")
    ResourceServerProperties facebookResource(){ return new ResourceServerProperties(); }

    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(
            OAuth2ClientContextFilter filter){
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
    private CsrfTokenRepository csrfTokenRepository(){
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }

    public static void main(String[] args){ SpringApplication.run(OAuthApplication.class, args); }

}
