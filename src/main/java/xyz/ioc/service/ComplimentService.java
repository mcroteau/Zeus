package xyz.ioc.service;

import org.springframework.beans.factory.annotation.Value;

public class ComplimentService {

    @Value("${facebook.app.id}")
    private String facebookAppId;

    @Value("${facebook.api.key}")
    private String facebookApiKey;

    public String getFacebookAppId() {
        return facebookAppId;
    }

    public String getFacebookApiKey() {
        return facebookApiKey;
    }

}
