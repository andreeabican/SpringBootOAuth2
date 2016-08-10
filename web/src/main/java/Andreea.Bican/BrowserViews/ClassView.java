package Andreea.Bican.BrowserViews;

import Andreea.Bican.Class;
import Andreea.Bican.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by andre on 24.06.2016.
 */
@RestController
public class ClassView {

    @Autowired
    private ClassService classService;

    @RequestMapping("/class")
    public String classs(@RequestHeader(value = "id")String id) {
        //no user lookup needed -- eveybody can see a class listing
        Class exampleClass = classService.getClass(Integer.parseInt(id));
        if (exampleClass == null) {
            return "null";
        }
        return "class_id: " + Integer.toString(exampleClass.getId()) +
                "\n name: " + exampleClass.getName() +
                "\n style: " + exampleClass.getStyle().getName() +
                "\n description: " + exampleClass.getDescription();
    }
}
