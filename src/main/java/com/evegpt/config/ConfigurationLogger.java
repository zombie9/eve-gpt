package com.evegpt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ConfigurationLogger {
    
    private static final Logger logger = LoggerFactory.getLogger(ConfigurationLogger.class);
    
    @Value("${openai.api.key}")
    private String openAiApiKey;
    
    @Value("${openai.api.url}")
    private String openAiApiUrl;
    
    @Value("${openai.model}")
    private String openAiModel;
    
    @EventListener(ApplicationReadyEvent.class)
    public void logConfiguration() {
        logger.info("=== Configuration Check ===");
        logger.info("OpenAI API URL: {}", openAiApiUrl);
        logger.info("OpenAI Model: {}", openAiModel);
        logger.info("OpenAI API Key loaded: {}", openAiApiKey != null && !openAiApiKey.trim().isEmpty() ? "YES (length: " + openAiApiKey.length() + ")" : "NO");
        if (openAiApiKey != null && !openAiApiKey.trim().isEmpty()) {
            logger.info("API Key starts with: {}...", openAiApiKey.substring(0, Math.min(10, openAiApiKey.length())));
        }
        logger.info("===========================");
    }
}