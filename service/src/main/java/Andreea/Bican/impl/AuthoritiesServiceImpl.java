package Andreea.Bican.impl;

import Andreea.Bican.Authorities;
import Andreea.Bican.AuthoritiesRepository;
import Andreea.Bican.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

    @Autowired
    private AuthoritiesRepository userRepository;

    public List<Authorities> getAuthorities(String uuid) {
        return userRepository.getAuthorities(uuid);
    }
}
