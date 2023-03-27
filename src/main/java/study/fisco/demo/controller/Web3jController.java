package study.fisco.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import study.fisco.demo.result.Result;
import study.fisco.demo.service.Web3jService;

import java.io.IOException;
import java.math.BigInteger;

@RestController
@RequestMapping("/web3j")
public class Web3jController {

    @Autowired private Web3jService web3jService;

    @GetMapping("/getBlockNumber")
    public BigInteger getBlockNumber() throws IOException {
        return web3jService.getBlockNumber();
    }

    @GetMapping("/queryAccountAmount")
    public Result queryAccountAmount(@RequestParam("account") String account) throws Exception {
        return web3jService.queryAccountAmount(account);
    }

    @GetMapping("/registerAccount")
    public Result registerAccount(@RequestParam("account") String account,@RequestParam("amount") BigInteger amount) throws Exception {
        return web3jService.registerAccount(account,amount);
    }

    @GetMapping("/transfer")
    public Result transfer(@RequestParam("fromAccount") String fromAccount,@RequestParam("toAccount") String toAccount,@RequestParam("amount") BigInteger amount) throws Exception {
        return web3jService.transfer(fromAccount,toAccount,amount);
    }

}
