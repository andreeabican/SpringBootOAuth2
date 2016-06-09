package Andreea.Bican.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Andreea.Bican.model.Authorities;
import Andreea.Bican.service.AuthoritiesService;
import Andreea.Bican.persistence.AuthoritiesRepository;
import Andreea.Bican.persistence.impl.AuthoritiesRepositoryHardcoded;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

    @Autowired
    private AuthoritiesRepository userRepository;

    public List<Authorities> getAuthorities(String uuid) {
        return userRepository.getAuthorities(uuid);
    }
}
