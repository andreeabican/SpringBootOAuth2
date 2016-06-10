package Andreea.Bican.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Andreea.Bican.model.StudentList;
import Andreea.Bican.service.StudentListService;
import Andreea.Bican.persistence.StudentListRepository;
import Andreea.Bican.persistence.impl.StudentListRepositoryHardcoded;

@Service
public class StudentListServiceImpl implements StudentListService {

    @Autowired
    private StudentListRepository studentListRepository;

    public List<StudentList> getStudentLists(int classId) {
        return studentListRepository.getStudentLists(classId);
    }

}
