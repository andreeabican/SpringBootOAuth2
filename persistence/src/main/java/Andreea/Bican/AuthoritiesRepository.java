package Andreea.Bican;

/**
 * Created by andre on 23.06.2016.
 */
import java.util.List;

public interface AuthoritiesRepository {
    public List<Authorities> getAuthorities(String uuid);
}