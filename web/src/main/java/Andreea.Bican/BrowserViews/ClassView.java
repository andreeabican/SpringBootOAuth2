package Andreea.Bican.BrowserViews;

import Andreea.Bican.Class;
import Andreea.Bican.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by andre on 24.06.2016.
 */
@RestController
public class ClassView {

    @Autowired
    private ClassService classService;

    @RequestMapping("/class")
    public String classs(Principal principal) {
        //no user lookup needed -- eveybody can see a class listing
        Class exampleClass = classService.getClass(3);
        if (exampleClass == null) {
            return "null";
        }
        return "class_id: " + Integer.toString(exampleClass.getId()) +
                "<br>name: " + exampleClass.getName() +
                "<br>style: " + exampleClass.getStyle().getName() +
                "<br>description: " + exampleClass.getDescription();
    }
}
