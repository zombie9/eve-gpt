package com.evegpt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;

public class ChatResponse {
    
    @JsonProperty("response")
    private String response;
    
    @JsonProperty("sources_consulted")
    private List<String> sourcesConsulted;
    
    @JsonProperty("web_search_performed")
    private boolean webSearchPerformed;
    
    @JsonProperty("timestamp")
    private LocalDateTime timestamp;
    
    @JsonProperty("tokens_used")
    private Integer tokensUsed;
    
    @JsonProperty("processing_time_ms")
    private Long processingTimeMs;
    
    // Constructors
    public ChatResponse() {
        this.timestamp = LocalDateTime.now();
    }
    
    public ChatResponse(String response) {
        this();
        this.response = response;
    }
    
    // Getters and Setters
    public String getResponse() {
        return response;
    }
    
    public void setResponse(String response) {
        this.response = response;
    }
    
    public List<String> getSourcesConsulted() {
        return sourcesConsulted;
    }
    
    public void setSourcesConsulted(List<String> sourcesConsulted) {
        this.sourcesConsulted = sourcesConsulted;
    }
    
    public boolean isWebSearchPerformed() {
        return webSearchPerformed;
    }
    
    public void setWebSearchPerformed(boolean webSearchPerformed) {
        this.webSearchPerformed = webSearchPerformed;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public Integer getTokensUsed() {
        return tokensUsed;
    }
    
    public void setTokensUsed(Integer tokensUsed) {
        this.tokensUsed = tokensUsed;
    }
    
    public Long getProcessingTimeMs() {
        return processingTimeMs;
    }
    
    public void setProcessingTimeMs(Long processingTimeMs) {
        this.processingTimeMs = processingTimeMs;
    }
}