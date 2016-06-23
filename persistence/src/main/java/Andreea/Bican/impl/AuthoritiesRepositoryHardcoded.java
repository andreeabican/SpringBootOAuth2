package Andreea.Bican.impl;

import Andreea.Bican.Authorities;
import Andreea.Bican.AuthoritiesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class AuthoritiesRepositoryHardcoded implements AuthoritiesRepository {

    @Autowired
    private AuthoritiesRepositoryHardcodedProperties authoritiesRepostioryHarcodedProperties;

    public List<Authorities> getAuthorities(String authoritiesUserUuid) {
        HashMap<String,AuthoritiesRepositoryHardcodedPropertyAuthorities> authorities = authoritiesRepostioryHarcodedProperties.getAuthorities();
        if (authorities == null) {
            return null; // no authorities properties were set in .yml
        }
        ArrayList<Authorities> foundAuthorities = new ArrayList<Authorities>();
        for (String authoritiesKey : authorities.keySet()) {
            if (authorities.get(authoritiesKey).getUserUuid().equals(authoritiesUserUuid)) {
                Authorities foundAuthority = new Authorities();
                foundAuthority.setPk(authorities.get(authoritiesKey).getPk());
                foundAuthority.setUuid(authorities.get(authoritiesKey).getUserUuid());
                foundAuthority.setAuthority(authorities.get(authoritiesKey).getAuthority());
                foundAuthorities.add(foundAuthority);
            }
        }
        return foundAuthorities;
    }
}
