package study.fisco.demo.service;

import org.fisco.bcos.sdk.abi.ABICodecException;
import org.fisco.bcos.sdk.abi.datatypes.generated.tuples.generated.Tuple2;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.model.TransactionReceipt;
import org.fisco.bcos.sdk.transaction.codec.decode.TransactionDecoderService;
import org.fisco.bcos.sdk.transaction.model.exception.ContractException;
import org.fisco.bcos.sdk.transaction.model.exception.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.fisco.demo.contract.javaSdk.Asset;
import study.fisco.demo.contract.javaSdk.AssetContract;
import study.fisco.demo.handleException.ServiceException;
import study.fisco.demo.result.Result;
import study.fisco.demo.sdkClient.JavaSdkConfig.TransactionReceiptEntity;
import study.fisco.demo.service.returEnum.AssetTransferEnum;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;

@Service
public class JavaSdkService extends BaseService{

    @Autowired
    private Client client;

    @Autowired
    private Asset asset;

    public Result getBlockNumber(){
        return Result.success(new HashMap<String,Object>(){{
            put("blockNumber",client.getBlockNumber().getBlockNumber());
        }});
    }

    public Result queryAccountAmount(String account) throws ContractException {
        //调用合约中的select方法
        Tuple2<BigInteger, BigInteger> select = asset.select(account);
        if(select.getValue1().equals(new BigInteger("-1"))){
            throw new ServiceException("账号不存在");
        }
        return Result.success(new HashMap<String,BigInteger>() {{
            put("amount",select.getValue2());
        }});
    }

    public Result registerAccount(String account,BigInteger amount) throws Exception {
        TransactionReceipt register = asset.register(account, amount);
//        交易对象文档 https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/api.html#gettransactionreceipt
        TransactionReceiptEntity entity = parseRes("register", register);
        validateTransaction(entity);
        if(entity.getResponse().getValuesList().get(0).equals(-1)){
            throw new ServiceException("资产账户已存在");
        }
        return Result.success();
    }

    public Result transfer(String fromAccount,String toAccount,BigInteger amount) throws ABICodecException, TransactionException, IOException {
        TransactionReceipt receipt = asset.transfer(fromAccount, toAccount, amount);
        TransactionReceiptEntity entity = parseRes("transfer", receipt);
        validateTransaction(entity);
        //        通过枚举类判断返回是否存在问题 注意如果交易收据receipt中的status不为0 则returnObject，value对象为空
        for(AssetTransferEnum itemEnum : AssetTransferEnum.values()){
            if(entity.getResponse().getReturnObject().get(0).equals(new BigInteger(String.valueOf(itemEnum.getCode())))){
                throw new ServiceException(itemEnum.getMsg());
            }
        }
        return Result.success(entity);
    }

    //    解析交易返回
//    https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.7.0/docs/sdk/java_sdk/transaction_decode.html
    public TransactionReceiptEntity parseRes(String fName, TransactionReceipt receipt) throws IOException, ABICodecException, TransactionException {
        TransactionReceiptEntity receiptEntity = new TransactionReceiptEntity();
        TransactionDecoderService decoderService = new TransactionDecoderService(client.getCryptoSuite());
//      https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.7.0/docs/sdk/java_sdk/transaction_decode.html#
//      解析响应包含方法返回
        receiptEntity.setResponse(decoderService.decodeReceiptWithValues(AssetContract.getAbi(), fName, receipt));
//       解析logs
        receiptEntity.setLogs(decoderService.decodeEvents(AssetContract.getAbi(),receipt.getLogs()));
//       解析交易状态
        receiptEntity.setStatus(decoderService.decodeReceiptStatus(receipt));
        return receiptEntity;
    }


//    验证交易是否成功 判断交易中出现了revert指令异常，或者其他异常
//    https://fisco-bcos-documentation.readthedocs.io/zh_CN/latest/docs/api.html#id3
    public void validateTransaction(TransactionReceiptEntity entity) throws ServiceException{
        if(!entity.getResponse().getTransactionReceipt().isStatusOK()){
            throw new ServiceException("交易执行错误："+entity.getResponse().getReceiptMessages());
        }
    }

}
