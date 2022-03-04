package study.fisco.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import study.fisco.demo.result.Result;
import study.fisco.demo.service.RedisService;

@RestController
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @PostMapping("/set")
    public Result set(@RequestParam String key,
                      @RequestParam String value){
        redisService.setKey(key, value);
        return Result.success();
    }

    @GetMapping("/get")
    public Result get(@RequestParam String key){
        return Result.success(redisService.getKey(key));
    }
}
