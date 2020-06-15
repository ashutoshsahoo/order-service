package com.ashu.order.auth.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Setter
@Getter
@NoArgsConstructor
public class JwtConfigProperties {

    private String secret;

    private String issuer;

    private String type;

    private String audience;

    private long tokenValidity;
}
