package Andreea.Bican.impl.Interceptors;

import Andreea.Bican.impl.Exception.ClassIdTooSmallException;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by andre on 18.10.2016.
 */
public class ClassEndpointInterceptor implements HandlerInterceptor {

    private static final Logger logger = Logger.getLogger("AuthInterceptor");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info(" Pre handle ");
        String id = request.getHeader("id");
        if(id != null){
            try{
                int intId = Integer.parseInt(id);
                if( intId < 0){
                    throw new ClassIdTooSmallException(id);
                }
            }catch(NumberFormatException ex){
                ex.printStackTrace();
            } catch (ClassIdTooSmallException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info(" Post handle ");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
