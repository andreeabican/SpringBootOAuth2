package Andreea.Bican.BrowserViews;

import Andreea.Bican.Class;
import Andreea.Bican.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/**
 * Created by andre on 24.06.2016.
 */

@RestController
public class ClassesView {

    @Autowired
    private ClassService classService;

    @RequestMapping("/classes")
    public String classes(Principal principal) {
        List<Class> classes = classService.getClasses();
        if (classes == null) {
            return "null";
        }
        String outputString = "";
        for (Class classs : classes) {
            outputString = outputString +
                    "class_id: " + Integer.toString(classs.getId()) +
                    "\n name: " + classs.getName() +
                    "\n style: " + classs.getStyle().getName() +
                    "\n description: " + classs.getDescription() +
                    "\n";
        }
        return outputString;
    }
}
