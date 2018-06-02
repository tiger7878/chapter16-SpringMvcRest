package spittr.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import spittr.data.SpittleRepository;
import spittr.domain.Spittle;

import java.util.Date;
import java.util.List;

/**
 * User: monkey
 * Date: 2018-06-01 15:08
 */
@Controller
@RequestMapping("/spittle")
public class SpittleApiController {

    private static final String MAX_LONG_AS_STRING = "9223372036854775807";

    @Autowired
    private SpittleRepository spittleRepository;


    @RequestMapping(value = "/spittles",method = RequestMethod.GET)
    @ResponseBody
    public List<Spittle> spittles(
            @RequestParam(value = "max",defaultValue = MAX_LONG_AS_STRING) long max,
            @RequestParam(value = "count",defaultValue = "20") int count){

        for (int i=0;i<30;i++){
            Spittle spittle=new Spittle(null,"monkey"+i,new Date(),Double.valueOf(i),Double.valueOf(i));
            spittleRepository.save(spittle);
        }

        return spittleRepository.findSpittles(max,count);
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public Spittle saveSpittle(Spittle spittle){
        return spittleRepository.save(spittle);
    }

}
