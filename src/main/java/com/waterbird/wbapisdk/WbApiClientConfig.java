package com.waterbird.wbapisdk;

import com.waterbird.wbapisdk.client.NameApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("wbapi.client")
@Data
@ComponentScan
public class WbApiClientConfig {
    private String accessKey;
    private String secretKey;

    @Bean
    public NameApiClient wbApiClient() {
        return new NameApiClient(accessKey, secretKey);
    }
}
