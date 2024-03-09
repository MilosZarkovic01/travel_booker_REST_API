package com.somika.travelbooker.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String prefix;
    private String header;
    private Integer expirationTime;
    private Integer refreshTokenExpirationTime;
}
