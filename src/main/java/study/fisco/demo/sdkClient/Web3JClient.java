package study.fisco.demo.sdkClient;


import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.web3j.crypto.Credentials;
import org.fisco.bcos.web3j.protocol.Web3j;
import org.fisco.bcos.web3j.protocol.channel.ChannelEthereumService;
import org.fisco.bcos.web3j.tx.gas.StaticGasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.fisco.demo.contract.web3Sdk.Asset;

import java.math.BigInteger;

@Configuration
public class Web3JClient {

    @Value("${contractAddress}")
    private String contractAddress;

    @Autowired
    private Service service;

    Logger logger = LoggerFactory.getLogger(Web3JClient.class);

    @Bean
    public Web3j getWeb3j() throws Exception {
        try{
            ChannelEthereumService channelEthereumService = new ChannelEthereumService();
            service.run();
            channelEthereumService.setChannelService(service);
            logger.info("web3j实例化成功");
            //获取Web3j对象
            return Web3j.build(channelEthereumService, service.getGroupId());
        }catch(Exception e){
            logger.error("web3Sdk实例化失败");
            throw e;
        }
    }

    //使用编译好的java对象中的load方法加载合约
    @Bean("web3jAsset")
    public Asset getAsset(Web3j web3j,Credentials credentials){
        /*
        * contractAddress 已经部署了的合约地址
        * web3j 通过配置实例化出来的
        * credentials 通过配置账户私钥创建出来的账号
        * StaticGasProvider 配置账号gas？
        * */
        Asset asset = Asset.load(contractAddress, web3j, credentials, new StaticGasProvider(new BigInteger("300000"), new BigInteger("300000")));
        logger.info("web3j--合约加载成功");
        return asset;
    }

}

