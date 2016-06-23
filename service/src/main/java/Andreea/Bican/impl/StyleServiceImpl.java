package Andreea.Bican.impl;

import Andreea.Bican.Style;
import Andreea.Bican.StyleRepository;
import Andreea.Bican.StyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StyleServiceImpl implements StyleService {

    @Autowired
    private StyleRepository styleRepository;

    public Style getStyle(int styleId) {
        return styleRepository.getStyle(styleId);
    }

}
