package study.fisco.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import study.fisco.demo.entety.User;
import study.fisco.demo.result.Result;
import study.fisco.demo.service.JdbcService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/Jdbc")
public class JdbcController {

    @Autowired
    private JdbcService jdbcService;

    @PostMapping("/Save")
    public Result addUser(@RequestParam String name,
                          @RequestParam String email,
                          @RequestParam int age){
        User user = new User();
        user.setAge(age);
        user.setEmail(email);
        user.setName(name);
        jdbcService.addUser(user);
        return Result.success();
    }

    @GetMapping("/selectAll")
    public Result selectAll(){
        return Result.success(jdbcService.select());
    }


    @GetMapping("/selectWithPage")
    public Result selectWithPage(@RequestParam int page,
                                 @RequestParam int limit,
                                 @RequestParam(defaultValue = "0",required=false) int id,
                                 @RequestParam(defaultValue = "",required = false) String name
                                 ){
        return Result.success(jdbcService.select(page,limit,id,name));
    }

    @PostMapping("/updateById")
    public Result updateById(@RequestParam int id,
                             @RequestParam int age,
                             @RequestParam String name,
                             @RequestParam String email){
        jdbcService.updateById(id,name,age,email);
        return Result.success();
    }

    @PostMapping("/batchDeleteById")
    public Result batchDeleteById(@RequestParam String ids){
        List<Integer> idS = Arrays.stream(ids.split(",")).map(i->Integer.parseInt(i)).collect(Collectors.toList());
        jdbcService.batchDeleteById(idS);
        return Result.success();
    }
}
