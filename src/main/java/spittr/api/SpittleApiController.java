package spittr.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import spittr.data.SpittleRepository;
import spittr.domain.Spittle;
import spittr.exception.SpittleNotFoundException;

import java.util.Date;
import java.util.List;

/**
 * User: monkey
 * Date: 2018-06-01 15:08
 */
@Controller
//控制器类上使用@RestController来代替@Controller的话，Spring将会为该控制器的所有处理方法应用消息转换功能。我们不必为每个方法都添加@ResponseBody了
//@RestController
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

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Spittle findOne(@PathVariable Long id){
        Spittle spittle=spittleRepository.findOne(id);

        //方案一：
//        HttpStatus status=spittle!=null?HttpStatus.OK:HttpStatus.NOT_FOUND;
//        return new ResponseEntity<Spittle>(spittle,status);

        //方案二：
//        if (spittle==null){
//            Error error = new Error(4, "spittle [" + id + "] not found");
//            return new ResponseEntity<Error>(error,HttpStatus.NOT_FOUND);
//        }
//        return new ResponseEntity<Spittle>(spittle,HttpStatus.OK);

        //方案三：
        if (spittle==null){
            throw new SpittleNotFoundException(id);
        }
        return spittle;
    }

    //@ExceptionHandler注解能够用到控制器方法中，用来处理特定的异常。
    // 这里，它表明如果在控制器的任意处理方法中抛出SpittleNotFoundException异常，
    // 就会调用spittleNotFound()方法来处理异常
    @ExceptionHandler(SpittleNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error spittleNotFound(SpittleNotFoundException e){
        Long id=e.getSpittleId();
        Error error=new Error(4,"spittle [" + id + "] not found");
//        return new ResponseEntity<Error>(error,HttpStatus.NOT_FOUND);
        return error;
    }
}
