package spittr.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import spittr.data.SpittleRepository;
import spittr.domain.Spittle;
import spittr.exception.SpittleNotFoundException;

import java.net.URI;
import java.text.SimpleDateFormat;
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

    @RequestMapping(value = "/save",method = RequestMethod.GET)
    public String save(){
        return "save";
    }

    @RequestMapping(value ="/save",method = RequestMethod.POST)
//    @ResponseBody
//    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Spittle> save(Spittle spittle, UriComponentsBuilder ucb){
        //Spring提供了 UriComponentsBuilder，可以给我们一些帮助。
        //它是一个构建类，通过逐步指定URL中的各种组成部分（如host、端口、路径以及 查询），我们能够使用它来构建UriComponents实例
        Spittle spittle1 = spittleRepository.save(spittle);

        //响应头部信息
        HttpHeaders headers=new HttpHeaders();
//        URI uri=URI.create("http://localhost:8080/spittle/"+spittle1.getId());//硬编码不好

        //计算uri
        URI uri=ucb.path("/spittle/")
                .path(String.valueOf(spittle1.getId()))
                .build()
                .toUri();
        headers.setLocation(uri);//设置location

        ResponseEntity<Spittle> responseEntity=new ResponseEntity<Spittle>(spittle1,headers,HttpStatus.CREATED);

        return responseEntity;
    }

    //处理日期
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true)); // true:允许输入空值，false:不能为空值
    }

}
