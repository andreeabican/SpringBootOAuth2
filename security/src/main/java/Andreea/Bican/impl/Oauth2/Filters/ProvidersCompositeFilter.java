package Andreea.Bican.impl.Oauth2.Filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.CompositeFilter;

import javax.servlet.Filter;
import java.util.ArrayList;

/**
 * Created by andre on 28.06.2016.
 */
public class ProvidersCompositeFilter {

    @Autowired
    @Qualifier("listOfFilters")
    ArrayList<Filter> listOfFilters;

    @Bean(name="compositeFilter")
    @DependsOn(value = {"facebookFilter", "googleFilter"})
    public CompositeFilter createCompositeFilter()
    {
        CompositeFilter filter = new CompositeFilter();
        filter.setFilters(listOfFilters);
        return filter;
    }
}
