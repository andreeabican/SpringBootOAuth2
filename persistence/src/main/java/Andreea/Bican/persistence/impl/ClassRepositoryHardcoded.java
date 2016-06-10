package Andreea.Bican.persistence.impl;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import Andreea.Bican.model.Class;
import Andreea.Bican.model.Style;
import Andreea.Bican.persistence.ClassRepository;
import Andreea.Bican.persistence.StyleRepository;

@Repository
public class ClassRepositoryHardcoded implements ClassRepository {

    @Autowired
    private ClassRepositoryHardcodedProperties classRepostioryHarcodedProperties;
    @Autowired
    private StyleRepository styleRepository;


    public Class getClass(int classId) {
        HashMap<String,ClassRepositoryHardcodedPropertyClass> classes = classRepostioryHarcodedProperties.getClasses();
        if (classes == null) {
            return null; // no class properties were set in .yml
        }
        for (String classKey : classes.keySet()) {
            if (classes.get(classKey).getClassId() == classId) {
                Class foundClass = new Class();
                foundClass.setId(classes.get(classKey).getClassId());
                foundClass.setName(classes.get(classKey).getName());
                int styleId = classes.get(classKey).getStyleId();
                Style foundStyle = styleRepository.getStyle(styleId);
                foundClass.setStyle(foundStyle);
                foundClass.setDescription(classes.get(classKey).getDescription());
                return foundClass;
            }
        }
        return null;
    }

    public List<Class> getClasses() {
        HashMap<String,ClassRepositoryHardcodedPropertyClass> classes = classRepostioryHarcodedProperties.getClasses();
        if (classes == null) {
            return null; // no class properties were set in .yml
        }
        List<Class> foundClasses = new ArrayList<Class>(classes.size());
        for (String classKey : classes.keySet()) {
            Class foundClass = new Class();
            foundClass.setId(classes.get(classKey).getClassId());
            foundClass.setName(classes.get(classKey).getName());
            int styleId = classes.get(classKey).getStyleId();
            Style foundStyle = styleRepository.getStyle(styleId);
            foundClass.setStyle(foundStyle);
            foundClass.setDescription(classes.get(classKey).getDescription());
            foundClasses.add(foundClass);
        }
        return foundClasses;
    }
}
