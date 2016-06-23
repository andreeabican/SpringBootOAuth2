package Andreea.Bican.impl;

import Andreea.Bican.Class;
import Andreea.Bican.ClassRepository;
import Andreea.Bican.ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepository classRepository;

    public Class getClass(int classId) {
        return classRepository.getClass(classId);
    }

    public List<Class> getClasses() {
        return classRepository.getClasses();
    }

}
