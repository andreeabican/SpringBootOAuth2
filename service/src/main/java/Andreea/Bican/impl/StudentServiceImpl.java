package Andreea.Bican.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Andreea.Bican.Student;
import Andreea.Bican.StudentService;
import Andreea.Bican.StudentRepository;
import Andreea.Bican.impl.StudentRepositoryHardcoded;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public Student getStudent(int studentId) {
        return studentRepository.getStudent(studentId);
    }

}
