package spittr.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import spittr.domain.Spitter;

/**
 * @author: monkey
 * @date: 2018/6/4 21:25
 */
@Controller
@RequestMapping("/spitter")
public class SpitterController {

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(){
        return "register";
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public Spitter register(Spitter spitter){
        return spitter;
    }

}
