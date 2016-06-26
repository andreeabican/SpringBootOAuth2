package Andreea.Bican.BrowserViews;

import Andreea.Bican.StudentList;
import Andreea.Bican.StudentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by andre on 24.06.2016.
 */

@RestController
public class StudentListView {

    @Autowired
    private StudentListService studentListService;

    @RequestMapping("/studentlists")
    public String studentList() {
        //TODO: add appropriate user authority checking here (Authorized user has authority which is in set of authorities for list of lists)
        int hardcodedClassId = 3;
        List<StudentList> studentLists = studentListService.getStudentLists(hardcodedClassId); //hardcoded example
        if (studentLists == null) {
            return "null";
        }
        String outputString = "student lists which have class id " + Integer.toString(hardcodedClassId) + ":";
        for (StudentList studentList : studentLists) {
            outputString = outputString +
                    "<br>&nbsp;" + Integer.toString(studentList.getStudentListId()) + ", (required authority : in";
            //Iterator<String> authorityIterator = studentList.getAuthorities().iterator();
            outputString = outputString + " " + studentList.getAuthorities();
            outputString = outputString + ")";
        }
        return outputString;
    }

}
