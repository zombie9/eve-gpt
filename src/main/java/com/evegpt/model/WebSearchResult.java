package com.evegpt.model;

public class WebSearchResult {
    
    private String url;
    private String title;
    private String content;
    private String source; // "eve-university" or "eve-online"
    private int relevanceScore;
    
    // Constructors
    public WebSearchResult() {}
    
    public WebSearchResult(String url, String title, String content, String source) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.source = source;
    }
    
    // Getters and Setters
    public String getUrl() {
        return url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public int getRelevanceScore() {
        return relevanceScore;
    }
    
    public void setRelevanceScore(int relevanceScore) {
        this.relevanceScore = relevanceScore;
    }
}