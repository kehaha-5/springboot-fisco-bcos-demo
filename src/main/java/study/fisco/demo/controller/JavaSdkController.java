package study.fisco.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.fisco.demo.result.Result;
import study.fisco.demo.service.JavaSdkAccountManager;
import study.fisco.demo.service.JavaSdkService;

import java.math.BigInteger;

@RestController
@RequestMapping("/javaSdk")
public class JavaSdkController {

    @Autowired
    private JavaSdkService javaSdkService;

    @Autowired
    private JavaSdkAccountManager javaSdkAccountManager;

    @GetMapping("/getBlockNumber")
    public Result getBlockNumber(){
        return javaSdkService.getBlockNumber();
    }

    @GetMapping("/queryAccountAmount")
    public Result queryAccountAmount(@RequestParam("account") String account) throws Exception {
        return javaSdkService.queryAccountAmount(account);
    }

    @GetMapping("/registerAccount")
    public Result registerAccount(@RequestParam("account") String account,@RequestParam("amount") BigInteger amount) throws Exception {
        return javaSdkService.registerAccount(account,amount);
    }

    @GetMapping("/transfer")
    public Result transfer(@RequestParam("fromAccount") String fromAccount,@RequestParam("toAccount") String toAccount,@RequestParam("amount") BigInteger amount) throws Exception {
        return javaSdkService.transfer(fromAccount,toAccount,amount);
    }

    @GetMapping("/createAccount")
    public Result createAccount(){
        return javaSdkAccountManager.createAccount();
    }

    @GetMapping("/loadAccount")
    public Result loadAccount(@RequestParam("accountAddress") String accountAddress){
        return javaSdkAccountManager.loadAccountByPemFile(accountAddress);
    }

}
