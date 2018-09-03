package com.psys.hobb.sys.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;

import java.util.List;

@EnableConfigurationProperties(OAuth2Properties.class)
public class OAuth2FeignResourceDetails implements OAuth2ProtectedResourceDetails {

    @Autowired
    private OAuth2Properties properties;

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getClientId() {
        return null;
    }

    @Override
    public String getAccessTokenUri() {
        return properties.getAccessTokenUri();
    }

    @Override
    public boolean isScoped() {
        return false;
    }

    @Override
    public List<String> getScope() {
        return null;
    }

    @Override
    public boolean isAuthenticationRequired() {
        return false;
    }

    @Override
    public String getClientSecret() {
        return properties.getClientSecret();
    }

    @Override
    public AuthenticationScheme getClientAuthenticationScheme() {
        return AuthenticationScheme.valueOf(properties.getClientAuthenticationScheme());
    }

    @Override
    public String getGrantType() {
        return null;
    }

    @Override
    public AuthenticationScheme getAuthenticationScheme() {
        return null;
    }

    @Override
    public String getTokenName() {
        return null;
    }

    @Override
    public boolean isClientOnly() {
        return false;
    }
}