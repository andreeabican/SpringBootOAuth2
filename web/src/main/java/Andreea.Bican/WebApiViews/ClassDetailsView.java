package Andreea.Bican.WebApiViews;

import Andreea.Bican.Class;
import Andreea.Bican.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 24.06.2016.
 */
@RestController
public class ClassDetailsView {

    @Autowired
    private ClassService classService;

    @RequestMapping(value = "/classdetails", method = RequestMethod.GET)
    public String getDetails(@RequestHeader(value = "id")int id,
                                @RequestHeader(value = "details", required = false)String details){
        Class clas =  classService.getClass(id);
        if(clas == null){
            return "Class not found";
        }
        if(details == null){
            return "class_id: " + id +
                    "name: " + clas.getName() +
                    "style: " + clas.getStyle().getName() +
                    "description: " + clas.getDescription();
        }else if(details.equals("style")){
            return clas.getStyle().getName();
        }else if(details.equals("name")){
            return clas.getName();
        }else if(details.equals("description")){
            return clas.getDescription();
        }

        return "Requested details don't exist";
    }
}
