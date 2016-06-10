package Andreea.Bican.service;

import java.util.List;
import Andreea.Bican.model.Authorities;

public interface AuthoritiesService {
    public List<Authorities> getAuthorities(String uuid);
}
