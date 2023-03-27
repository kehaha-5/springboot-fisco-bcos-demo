package study.fisco.demo.sdkClient.Web3JConfig;

import org.fisco.bcos.channel.client.Service;
import org.fisco.bcos.channel.handler.GroupChannelConnectionsConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="web3j-channel-service")
public class ServiceConfig {
    private int groupId;

    @Bean
    public Service getService(GroupChannelConnectionsConfig groupChannelConnectionsConfig) {
        Service channelService = new Service();
        channelService.setGroupId(groupId);
        channelService.setAllChannelConnections(groupChannelConnectionsConfig);
        return channelService;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
}
