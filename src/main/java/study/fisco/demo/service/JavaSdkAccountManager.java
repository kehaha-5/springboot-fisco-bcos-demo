package study.fisco.demo.service;


import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.crypto.CryptoSuite;
import org.fisco.bcos.sdk.crypto.keypair.CryptoKeyPair;
import org.fisco.bcos.sdk.model.CryptoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import study.fisco.demo.result.Result;

import java.util.HashMap;

@Service
public class JavaSdkAccountManager {

    @Autowired
    private Client client;

//    生成非国密账号
    //https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.7.0/docs/sdk/java_sdk/key_tool.html#id5
    public Result createAccount(){
//        设置为非国密
        CryptoSuite cryptoSuite = new CryptoSuite(CryptoType.ECDSA_TYPE);
//      随机生成非国密公私钥对
        CryptoKeyPair keyPair = cryptoSuite.createKeyPair();
        // 账户文件名为${accountAddress}.pem
//        注意如果要保存在账号配置的${keyStoreDir}指定目录下要先keyPair.setConfig();设置一个config给它
//        否者默认保存在account/ecdsa/${accountAddress}.${accountFileFormat}
//        https://fisco-bcos-documentation.readthedocs.io/zh_CN/release-2.7.0/docs/sdk/java_sdk/configuration.html#id9
        keyPair.storeKeyPairWithPemFormat();
        return Result.success(new HashMap<String,String>(){{
//            获取账户地址
            put("accountAddress",keyPair.getAddress());
        }});
    }

//    加载生成的账号
    public Result loadAccountByPemFile(String accountAddress){
        String pemAccountFilePath = String.format("account/ecdsa/%s.pem", accountAddress);
        // 通过client获取CryptoSuite对象
        CryptoSuite cryptoSuite = client.getCryptoSuite();
        // 加载pem账户文件
        cryptoSuite.loadAccount("pem", pemAccountFilePath, null);
        return Result.success();
    }
}
