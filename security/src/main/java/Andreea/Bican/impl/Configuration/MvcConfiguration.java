package Andreea.Bican.impl.Configuration;

import Andreea.Bican.impl.Interceptors.ClassEndpointInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by andre on 15.09.2016.
 */
@Configuration
public class MvcConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ClassEndpointInterceptor()).addPathPatterns("/class/**").excludePathPatterns("/admin/**");
    }
}
