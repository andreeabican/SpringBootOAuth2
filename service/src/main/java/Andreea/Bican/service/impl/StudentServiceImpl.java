package Andreea.Bican.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Andreea.Bican.model.Student;
import Andreea.Bican.service.StudentService;
import Andreea.Bican.persistence.StudentRepository;
import Andreea.Bican.persistence.impl.StudentRepositoryHardcoded;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student getStudent(int studentId) {
        return studentRepository.getStudent(studentId);
    }

}
