package com.evegpt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "web.scraping")
public class WebScrapingProperties {
    /** Timeout (ms) for web scraping operations */
    private Integer timeout = 30000;
    /** User-Agent header sent with scraping requests */
    private String userAgent;

    public Integer getTimeout() { return timeout; }
    public void setTimeout(Integer timeout) { this.timeout = timeout; }
    public String getUserAgent() { return userAgent; }
    public void setUserAgent(String userAgent) { this.userAgent = userAgent; }
}
