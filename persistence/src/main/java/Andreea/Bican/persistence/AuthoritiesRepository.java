package Andreea.Bican.persistence;

import java.util.List;
import Andreea.Bican.model.Authorities;

public interface AuthoritiesRepository {
    public List<Authorities> getAuthorities(String uuid);
}
