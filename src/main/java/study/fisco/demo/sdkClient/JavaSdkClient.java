package study.fisco.demo.sdkClient;

import org.fisco.bcos.sdk.BcosSDK;
import org.fisco.bcos.sdk.client.Client;
import org.fisco.bcos.sdk.config.ConfigOption;
import org.fisco.bcos.sdk.config.exceptions.ConfigException;
import org.fisco.bcos.sdk.config.model.ConfigProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import study.fisco.demo.contract.javaSdk.Asset;
import study.fisco.demo.sdkClient.JavaSdkConfig.JavaSdkConfig;

import java.util.HashMap;

@Configuration
public class JavaSdkClient {

    Logger logger = LoggerFactory.getLogger(JavaSdkClient.class);

    @Autowired
    private JavaSdkConfig javaSdkConfig;

    @Value("${contractAddress}")
    private String contractAddress;

    @Bean
    public Client getClient() throws ConfigException {
        ConfigProperty configProperty = loadConfig();
        try {
            ConfigOption configOption = new ConfigOption(configProperty);
            Client client = new BcosSDK(configOption).getClient(javaSdkConfig.getConnectGroupId());
            logger.info("JavaSdk实例化成功");
            return client;
        }catch (ConfigException e){
            logger.error("JavaSdk合约配置异常:{}",e.getMessage());
            throw e;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("JavaSdk实例化失败:{}",e.getMessage());
            throw e;
        }
    }

    public ConfigProperty loadConfig(){
        ConfigProperty configProperty = new ConfigProperty();
//        设置账号
        configProperty.setAccount(javaSdkConfig.getAccount());
//        设置节点网络
        configProperty.setNetwork(new HashMap<String,Object>(){{
            put("peers",javaSdkConfig.getNetwork().get("peers"));
        }});
//        设置连接节点的证书
        configProperty.setCryptoMaterial(javaSdkConfig.getCryptoMaterial());
        return  configProperty;
    }

    @Bean("javaSdkAsset")
    public Asset getAsset(Client client){
        /*
        * contractAddress 已经部署了的合约地址
        * client 通过配置实例化的client
        * */
        Asset asset = Asset.load(contractAddress, client, client.getCryptoSuite().getCryptoKeyPair());
        logger.info("JavaSdk--合约加载成功");
        return asset;
    }

}
