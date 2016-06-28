package Andreea.Bican.impl;

import Andreea.Bican.StudentList;
import Andreea.Bican.StudentListRepository;
import Andreea.Bican.StudentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class StudentListServiceImpl implements StudentListService {

    @Autowired
    private StudentListRepository studentListRepository;

    public List<StudentList> getStudentLists(int classId) {
        return studentListRepository.getStudentLists(classId);
    }

    public boolean hasUserAuthorities(Set<String> userAuthorities, StudentList studentList){
        List<String> reqiredAuthorities = studentList.getAuthorities();
        for(String requiredAuthority : reqiredAuthorities){
            for(String userAuthority : userAuthorities){
                if(userAuthority.equals(requiredAuthority)){
                    return true;
                }
            }
        }
        return false;
    }

}
