package com.evegpt.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChatRequest {
    
    @NotBlank(message = "User prompt cannot be blank")
    @Size(max = 4000, message = "User prompt cannot exceed 4000 characters")
    @JsonProperty("prompt")
    private String userPrompt;
    
    @JsonProperty("include_web_search")
    private boolean includeWebSearch = true;
    
    @JsonProperty("max_tokens")
    private Integer maxTokens = 1500;
    
    @JsonProperty("temperature")
    private Double temperature = 0.7;
    
    // Constructors
    public ChatRequest() {}
    
    public ChatRequest(String userPrompt) {
        this.userPrompt = userPrompt;
    }
    
    // Getters and Setters
    public String getUserPrompt() {
        return userPrompt;
    }
    
    public void setUserPrompt(String userPrompt) {
        this.userPrompt = userPrompt;
    }
    
    public boolean isIncludeWebSearch() {
        return includeWebSearch;
    }
    
    public void setIncludeWebSearch(boolean includeWebSearch) {
        this.includeWebSearch = includeWebSearch;
    }
    
    public Integer getMaxTokens() {
        return maxTokens;
    }
    
    public void setMaxTokens(Integer maxTokens) {
        this.maxTokens = maxTokens;
    }
    
    public Double getTemperature() {
        return temperature;
    }
    
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}