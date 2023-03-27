package study.fisco.demo.sdkClient.Web3JConfig;

import org.fisco.bcos.web3j.crypto.Credentials;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "web3j-account-config")
public class CredentialsConfig {

    private String privateKey;

    @Bean
    public Credentials getCredentials(){
        //通过指定外部账户私钥使用指定的外部账户
        return Credentials.create(privateKey);
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
