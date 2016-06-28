package Andreea.Bican;

import java.util.List;
import java.util.Set;

public interface StudentListService {
    public List<StudentList> getStudentLists(int classId);
    public boolean hasUserAuthorities(Set<String> userAuthorities, StudentList studentList);
}
