package com.evegpt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Binds OpenAI related properties:
 * openai.api.key, openai.api.url, openai.model
 */
@Component
@ConfigurationProperties(prefix = "openai")
public class OpenAIProperties {

    /** Nested API grouping for key + url (openai.api.*) */
    private final Api api = new Api();
    /** Model name (openai.model) */
    private String model;

    public Api getApi() { return api; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public static class Api {
        /** API key (openai.api.key) */
        private String key;
        /** Endpoint URL (openai.api.url) */
        private String url;

        public String getKey() { return key; }
        public void setKey(String key) { this.key = key; }
        public String getUrl() { return url; }
        public void setUrl(String url) { this.url = url; }
    }
}
