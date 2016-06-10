package Andreea.Bican.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Andreea.Bican.model.Class;
import Andreea.Bican.service.ClassService;
import Andreea.Bican.persistence.ClassRepository;
import Andreea.Bican.persistence.impl.ClassRepositoryHardcoded;

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
