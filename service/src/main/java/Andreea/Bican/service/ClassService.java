package Andreea.Bican.service;

import java.util.List;
import Andreea.Bican.model.Class;

public interface ClassService {
    public Class getClass(int classId);
    public List<Class> getClasses();
}
