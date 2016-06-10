package Andreea.Bican.persistence.impl;

import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import Andreea.Bican.model.Style;
import Andreea.Bican.persistence.StyleRepository;

@Repository
public class StyleRepositoryHardcoded implements StyleRepository {

    @Autowired
    private StyleRepositoryHardcodedProperties styleRepostioryHarcodedProperties;

    public Style getStyle(int styleId) {
        HashMap<String,StyleRepositoryHardcodedPropertyStyle> styles = styleRepostioryHarcodedProperties.getStyles();
        if (styles == null) {
            return null; // no style properties were set in .yml
        }
        for (String styleKey : styles.keySet()) {
            if (styles.get(styleKey).getStyleId() == styleId) {
                Style foundStyle = new Style();
                foundStyle.setId(styles.get(styleKey).getStyleId());
                foundStyle.setName(styles.get(styleKey).getName());
                return foundStyle;
            }
        }
        return null;
    }
}
