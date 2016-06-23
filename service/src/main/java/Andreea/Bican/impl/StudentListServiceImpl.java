package Andreea.Bican.impl;

import Andreea.Bican.StudentList;
import Andreea.Bican.StudentListRepository;
import Andreea.Bican.StudentListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentListServiceImpl implements StudentListService {

    @Autowired
    private StudentListRepository studentListRepository;

    public List<StudentList> getStudentLists(int classId) {
        return studentListRepository.getStudentLists(classId);
    }

}
