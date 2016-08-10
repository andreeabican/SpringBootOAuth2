package Andreea.Bican.WebApiViews;

import Andreea.Bican.*;
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
    private UserService userService;

    @Autowired
    StudentListMembershipService studentListMembershipService;

    @Autowired
    StudentService studentService;

    @Autowired
    TokenService tokenService;

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
           Set<String> userAuthorities = null;
           if (checkAccessToken(token)) {
               String email = tokenService.getEmailFromGoogleAccessToken(token);
               User user = userService.getUser(email);
               userAuthorities = user.getAuthorities();
               outputString = "student lists which have class id " + Integer.toString(classId) + ":\n";
               for (StudentList studentList : studentLists) {
                   if (studentListService.hasUserAuthorities(userAuthorities, studentList)) {
                       outputString += getNames(studentListMembershipService.getStudentListMemberships(classId));
                   } else {
                       outputString += "You don't have authorities to see "
                               + Integer.toString(studentList.getStudentListId());
                   }
               }
            }else{
                   return "Token is not valid";
           }
           return outputString;
       }
        return "Required token";
    }

    public String getNames(List<StudentListMembership> listOfStudentsEnroledInClass){
        String names = "";
        for( StudentListMembership name : listOfStudentsEnroledInClass){
            System.out.println(studentService.getStudent(name.getStudentId()).getName());
            names += studentService.getStudent(name.getStudentId()).getName() + "\n";
        }
        if (names.equals("")){
            return "No student";
        }
        return names;
    }

    public boolean checkAccessToken(String token) throws Exception {

        if (tokenService.checkToken(token)) {
            return true;
        } else {
            return false;
        }
    }
}
