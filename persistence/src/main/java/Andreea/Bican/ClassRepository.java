package Andreea.Bican;

import java.util.List;

/**
 * Created by andre on 23.06.2016.
 */
public interface ClassRepository {
    public Class getClass(int classId);
    public List<Class> getClasses();
}