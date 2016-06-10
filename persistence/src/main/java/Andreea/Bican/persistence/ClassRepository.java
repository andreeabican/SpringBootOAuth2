package Andreea.Bican.persistence;

import java.util.List;
import Andreea.Bican.model.Class;

public interface ClassRepository {
    public Class getClass(int classId);
    public List<Class> getClasses();
}
