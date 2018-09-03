package com.psys.hobb;

import com.psys.hobb.auth.config.security.SecAuthenticationProvider;
import org.apache.catalina.filters.RequestDumperFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;
import java.util.Arrays;

/**
 * http://localhost:18090/login
 * 编  号：
 * 名  称：AuthApplication
 * 描  述：
 * 完成日期：2018/6/9 23:57
 * 编码作者：SHJ
 */
@SpringBootApplication
@EnableAuthorizationServer
@EnableDiscoveryClient
@EnableFeignClients
@EnableHystrix
public class AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

    @Bean
    @Primary //表示该bean为此类型的默认bean，在其他地方引用的时候用@Autowired即可按照类型注入，不受同类型多个对象影响
    @ConfigurationProperties("db.first.datasource")
    public DataSourceProperties firstDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("db.first.datasource")
    public DataSource securityDbConfig() {
        DataSource ds = firstDataSourceProperties().initializeDataSourceBuilder().build();
        return ds;
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(securityDbConfig());
    }

    @Profile("!cloud")
    @Bean
    RequestDumperFilter requestDumperFilter() {
        return new RequestDumperFilter();
    }

    private @Autowired SecAuthenticationProvider provider;//自定义验证

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(provider));
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
