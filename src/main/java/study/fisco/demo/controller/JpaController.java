package study.fisco.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import study.fisco.demo.entety.User;
import study.fisco.demo.result.Result;
import study.fisco.demo.service.JpaService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jpa")
public class JpaController {

    @Autowired
    private JpaService jpaService;

    @GetMapping("/selectAll")
    public Result selectAll(){
        return Result.success(jpaService.selectAll());
    }

    @GetMapping("/selectWithPage")
    public Result select(@RequestParam int page,
                         @RequestParam int limit,
                         @RequestParam(required = false,defaultValue = "") String name,
                         @RequestParam(required = false,defaultValue = "0") int age ){
        return Result.success(jpaService.select(page, limit, age, name));
    }

    @PostMapping("/addUser")
    public Result addUser(@RequestParam String name,
                          @RequestParam int age,
                          @RequestParam String email){
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setAge(age);
        jpaService.Save(user);
        return Result.success();
    }

    @PostMapping("/updateById")
    public Result updateById(@RequestParam int id,
                             @RequestParam String name,
                             @RequestParam int age,
                             @RequestParam String email){
        User user = new User(new Long(id), name, age, email);
        jpaService.Save(user);
        return Result.success();
    }

    @PostMapping("/batchDeleteById")
    public Result batchDeleteById(@RequestParam String ids){
        List<Integer> collect = Arrays.stream(ids.split(",")).map(i -> Integer.parseInt(i)).collect(Collectors.toList());
        jpaService.batchDeleteById(collect);
        return Result.success();
    }

}
