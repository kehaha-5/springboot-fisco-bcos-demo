package study.fisco.demo.sdkClient.JavaSdkConfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "java-sdk")
public class JavaSdkConfig {

    private Map<String,Object> cryptoMaterial;
    private Map<String,List<String>> network;
    private Map<String, Object> account;
    private int connectGroupId;

    public int getConnectGroupId() {
        return connectGroupId;
    }

    public void setConnectGroupId(int connectGroupId) {
        this.connectGroupId = connectGroupId;
    }

    public Map<String, Object> getCryptoMaterial() {
        return cryptoMaterial;
    }

    public void setCryptoMaterial(Map<String, Object> cryptoMaterial) {
        this.cryptoMaterial = cryptoMaterial;
    }

    public Map<String, List<String>> getNetwork() {
        return network;
    }

    public void setNetwork(Map<String, List<String>> network) {
        this.network = network;
    }

    public Map<String, Object> getAccount() {
        return account;
    }

    public void setAccount(Map<String, Object> account) {
        this.account = account;
    }
}
