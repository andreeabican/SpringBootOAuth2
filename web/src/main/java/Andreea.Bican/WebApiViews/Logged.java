package Andreea.Bican.WebApiViews;

import org.springframework.web.bind.annotation.*;

/**
 * Created by andre on 25.08.2016.
 */
@RestController
public class Logged {

    @RequestMapping(value="/logged", method = RequestMethod.POST )
    public @ResponseBody
    String createMessage(@RequestBody String message ) {
        return message;
    }
}
