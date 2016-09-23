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

public class FilterBefore {

    @Autowired
    @Qualifier("listOfFiltersBefore")
    ArrayList<Filter> listOfFilters;

    @Bean(name="filterBefore")
    @DependsOn(value = {"facebookFilter", "googleFilter", "metadataFilter"})
    public CompositeFilter createCompositeFilter()
    {
        CompositeFilter filter = new CompositeFilter();
        filter.setFilters(listOfFilters);
        return filter;
    }
}
