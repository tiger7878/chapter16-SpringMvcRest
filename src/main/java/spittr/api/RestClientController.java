package spittr.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import spittr.domain.Spittle;

/**
 * 使用RestTemplate编写rest客户端
 * @author: monkey
 * @date: 2018/6/4 22:30
 */
@Controller
@RequestMapping("/restClient")
public class RestClientController {

    @RequestMapping(value ="/fetchSpittle",method = RequestMethod.GET)
    public Spittle fetchSpittle(long id){
        RestTemplate rest=new RestTemplate();
        ResponseEntity<Spittle> response=rest.getForEntity("http://localhost:8080/spittle/{id}",Spittle.class,id);
        if (response.getStatusCode()== HttpStatus.NOT_MODIFIED){

        }
        return response.getBody();
    }

}
