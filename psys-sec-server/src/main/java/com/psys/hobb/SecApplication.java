package com.psys.hobb;

import com.psys.hobb.sec.config.RestWebMvcConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;

@SpringBootApplication
@EnableResourceServer
@EnableEurekaClient
@EnableJpaAuditing  //Spring JPA 使用@CreatedDate、@CreatedBy、@LastModifiedDate、@LastModifiedBy 自动生成时间和修改者
@Import({RestWebMvcConfigurer.class})
public class SecApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecApplication.class, args);
    }

    @Configuration
    public class CorsConfig {
        private CorsConfiguration buildConfig() {
            CorsConfiguration corsConfiguration = new CorsConfiguration();
            corsConfiguration.addAllowedOrigin("*");
            corsConfiguration.addAllowedHeader("*");
            corsConfiguration.addAllowedMethod("*");
            return corsConfiguration;
        }

        @Bean
        public CorsFilter corsFilter() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            source.registerCorsConfiguration("/**", buildConfig());
            return new CorsFilter(source);
        }
    }
}
