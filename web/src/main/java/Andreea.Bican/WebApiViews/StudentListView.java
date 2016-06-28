package Andreea.Bican.WebApiViews;

import Andreea.Bican.StudentList;
import Andreea.Bican.StudentListService;
import Andreea.Bican.User;
import Andreea.Bican.impl.Configuration.ProviderAccessToken;
import Andreea.Bican.impl.CurrentContextServiceImpl;
import Andreea.Bican.impl.IAccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * Created by andre on 24.06.2016.
 */

@RestController
public class StudentListView {

    @Autowired
    private StudentListService studentListService;

    @Autowired
    CurrentContextServiceImpl currentContextService;

    @RequestMapping("/studentlists")
    public String studentList(@RequestHeader(value = "classId")int classId,
                              @RequestHeader(value = "token", required = false)String token) throws Exception {
        //TODO: add appropriate user authority checking here (Authorized user has authority which is in set of authorities for list of lists)
        String outputString = "";
       if(token != null) {
           List<StudentList> studentLists = studentListService.getStudentLists(classId); //hardcoded example
           if (studentLists == null) {
               return "null";
           }
           Set<String> authorities = null;
           if (checkAccessToken(token)) {
               User user = currentContextService.getCurrentUser(token);
               authorities = user.getAuthorities();
           }
           outputString = "student lists which have class id " + Integer.toString(classId) + ":";
           for (StudentList studentList : studentLists) {
               if (!authorities.isEmpty() && authorities.contains(studentList.getAuthorities())) {
                   outputString = outputString +
                           "<br>&nbsp;" + Integer.toString(studentList.getStudentListId()) + ", (required authority : in";
                   //Iterator<String> authorityIterator = studentList.getAuthorities().iterator();
                   outputString = outputString + " " + studentList.getAuthorities();
                   outputString = outputString + ")";
               } else {
                   outputString += "You don't have authorities to see "
                           + Integer.toString(studentList.getStudentListId());
               }
           }
       }
        return outputString;
    }

    public boolean checkAccessToken(String token) throws Exception {
        ProviderAccessToken providerAccessToken = new ProviderAccessToken();
        System.out.println(token);
        IAccessToken accessToken = providerAccessToken.getProviderAccessToken(token);
        if (accessToken != null) {
            if (accessToken.checkToken(token)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
