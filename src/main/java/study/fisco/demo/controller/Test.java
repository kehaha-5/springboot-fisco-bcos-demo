package study.fisco.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.fisco.demo.result.Result;

@RestController
@RequestMapping("/test")
public class Test {

    @GetMapping("/intercepted")
    public Result intercepted(){
        return Result.success();
    }
}
