package Andreea.Bican;

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
    public String getClassMusic(@RequestHeader("id")int id, @RequestHeader("details")String details){
        Class clas =  classService.getClass(id);
        if(clas == null){
            return "Class not found";
        }
        if(details.equals("style")){
            return clas.getStyle().getName();
        }else if(details.equals("name")){
            return clas.getName();
        }else if(details.equals("description")){
            return clas.getDescription();
        }

        return "";
    }
}
