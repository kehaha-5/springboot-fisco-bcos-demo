package study.fisco.demo.service;

import org.fisco.bcos.web3j.protocol.core.methods.response.TransactionReceipt;
import org.fisco.bcos.web3j.tuples.generated.Tuple2;
import org.fisco.bcos.web3j.tx.txdecode.BaseException;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoder;
import org.fisco.bcos.web3j.tx.txdecode.TransactionDecoderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.fisco.demo.contract.web3Sdk.Asset;
import study.fisco.demo.contract.web3Sdk.AssetContract;
import study.fisco.demo.handleException.ServiceException;
import study.fisco.demo.result.Result;
import study.fisco.demo.sdkClient.Web3JConfig.TransactionReceiptEntity;
import study.fisco.demo.service.returEnum.AssetTransferEnum;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;


@Service
public class Web3jService extends BaseService<Asset> {

    Logger logger = LoggerFactory.getLogger(Web3jService.class);

    @Autowired
    private org.fisco.bcos.web3j.protocol.Web3j web3j;

    @Autowired
    private Asset asset;

    public BigInteger getBlockNumber() throws IOException {
        return web3j.getBlockNumber().send().getBlockNumber();
    }

    public Result queryAccountAmount(String account) throws Exception {
        //调用合约中的select方法
        Tuple2<BigInteger, BigInteger> select = asset.select(account).send();
        if(select.getValue1().equals(new BigInteger("-1"))){
            throw new ServiceException("账号不存在");
        }
        return Result.success(new HashMap<String,BigInteger>() {{
            put("amount",select.getValue2());
        }});
    }

    public Result registerAccount(String account,BigInteger amount) throws Exception {
        TransactionReceipt receipt = asset.register(account, amount).send();
        validateTransaction(receipt);
        TransactionReceiptEntity transactionReceiptEntity = parseOutAndInputRes(receipt);
        if(transactionReceiptEntity.getOut().getResult().get(0).getData().equals(new BigInteger("-1"))){
            throw new ServiceException("资产账户已存在");
        }
        if(transactionReceiptEntity.getOut().getResult().get(0).getData().equals(new BigInteger("-2"))){
            throw new ServiceException("服务器错误");
        }
        return Result.success();
    }

    public Result transfer(String fromAccount,String toAccount,BigInteger amount) throws Exception {
        TransactionReceipt receipt = asset.transfer(fromAccount, toAccount, amount).send();
        validateTransaction(receipt);
        TransactionReceiptEntity transactionReceiptEntity = parseOutAndInputRes(receipt);
//        通过枚举类判断返回是否存在问题
        for(AssetTransferEnum itemEnum : AssetTransferEnum.values()){
            if(transactionReceiptEntity.getOut().getResult().get(0).getData().equals(new BigInteger(String.valueOf(itemEnum.getCode())))){
                throw new ServiceException(itemEnum.getMsg());
            }
        }
        return Result.success();
    }


//    解析交易返回
//    https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.7.0/docs/sdk/java_sdk.html#id13
    public TransactionReceiptEntity parseOutAndInputRes(TransactionReceipt receipt) throws IOException, BaseException {
//        通过编译出来的abi实例化解析方法
        TransactionDecoder transactionDecoder = TransactionDecoderFactory.buildTransactionDecoder(AssetContract.getAbi(),AssetContract.getBin());
        TransactionReceiptEntity transactionReceiptEntity = new TransactionReceiptEntity();
//        解析inupt
        transactionReceiptEntity.setInput(transactionDecoder.decodeInputReturnObject(receipt.getInput()));
//        解析out
        transactionReceiptEntity.setOut(transactionDecoder.decodeOutputReturnObject(receipt.getInput(),receipt.getOutput()));
//        解析log（solidity中的event方法）
        transactionReceiptEntity.setLogs(transactionDecoder.decodeEventReturnObject(receipt.getLogs()));
        return transactionReceiptEntity;
    }

//    验证交易是否成功 判断交易中出现了revert指令异常，或者其他异常 主要在web3jdk中要优先验证是否出错，否者后面解析out可能会出现异常
//    https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/api.html#id3
    public void validateTransaction(TransactionReceipt receipt) throws ServiceException{
        if(!(receipt.getStatus().equals("0x0") || receipt.getStatus().equals(0))){
            throw new ServiceException("交易执行错误："+receipt.getMessage());
        }
    }
}
