package Andreea.Bican.service;

import java.util.List;
import org.springframework.stereotype.Service;
import Andreea.Bican.model.Authorities;

public interface AuthoritiesService {
    public List<Authorities> getAuthorities(String uuid);
}
