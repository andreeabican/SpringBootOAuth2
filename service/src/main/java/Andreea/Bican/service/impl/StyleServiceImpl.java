package Andreea.Bican.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Andreea.Bican.model.Style;
import Andreea.Bican.service.StyleService;
import Andreea.Bican.persistence.StyleRepository;
import Andreea.Bican.persistence.impl.StyleRepositoryHardcoded;

@Service
public class StyleServiceImpl implements StyleService {

    @Autowired
    private StyleRepository styleRepository;

    public Style getStyle(int styleId) {
        return styleRepository.getStyle(styleId);
    }

}
