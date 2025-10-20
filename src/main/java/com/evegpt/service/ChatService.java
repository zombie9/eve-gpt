package com.evegpt.service;

import com.evegpt.model.ChatRequest;
import com.evegpt.model.ChatResponse;
import com.evegpt.model.WebSearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    
    private final WebScrapingService webScrapingService;
    private final OpenAIService openAIService;
    
    public ChatService(WebScrapingService webScrapingService, OpenAIService openAIService) {
        this.webScrapingService = webScrapingService;
        this.openAIService = openAIService;
    }
    
    public Mono<ChatResponse> processChat(ChatRequest request) {
        long startTime = System.currentTimeMillis();
        
        logger.info("Processing chat request: {}", request.getUserPrompt());
        
        if (request.isIncludeWebSearch()) {
            return performWebSearchAndGenerate(request, startTime);
        } else {
            return generateDirectResponse(request, startTime);
        }
    }
    
    private Mono<ChatResponse> performWebSearchAndGenerate(ChatRequest request, long startTime) {
        return Mono.fromCallable(() -> {
            List<WebSearchResult> searchResults = new ArrayList<>();
            
            // Search EVE University Wiki first (prioritized)
            List<WebSearchResult> wikiResults = webScrapingService.searchEveWiki(request.getUserPrompt());
            searchResults.addAll(wikiResults);
            
            // Search official EVE Online site
            List<WebSearchResult> officialResults = webScrapingService.searchEveOnline(request.getUserPrompt());
            searchResults.addAll(officialResults);
            
            // Enhance search results with full page content for top results
            enhanceSearchResults(searchResults);
            
            logger.info("Found {} web search results", searchResults.size());
            
            return searchResults;
        })
        .flatMap(searchResults -> 
            openAIService.generateResponse(request, searchResults)
                .map(response -> buildChatResponse(response, searchResults, startTime, true))
        )
        .doOnError(error -> logger.error("Error in web search and generation", error))
        .onErrorResume(error -> generateDirectResponse(request, startTime));
    }
    
    private Mono<ChatResponse> generateDirectResponse(ChatRequest request, long startTime) {
        return openAIService.generateResponse(request, List.of())
                .map(response -> buildChatResponse(response, List.of(), startTime, false))
                .doOnError(error -> logger.error("Error in direct response generation", error));
    }
    
    private void enhanceSearchResults(List<WebSearchResult> searchResults) {
        // Enhance top 3 results with full page content
        for (int i = 0; i < Math.min(3, searchResults.size()); i++) {
            WebSearchResult result = searchResults.get(i);
            try {
                String fullContent = webScrapingService.extractPageContent(result.getUrl());
                if (!fullContent.isEmpty()) {
                    // Limit content to avoid overwhelming the prompt
                    String limitedContent = fullContent.length() > 2000 ? 
                        fullContent.substring(0, 2000) + "..." : fullContent;
                    result.setContent(limitedContent);
                }
            } catch (Exception e) {
                logger.warn("Failed to enhance search result: {}", result.getUrl(), e);
            }
        }
    }
    
    private ChatResponse buildChatResponse(String aiResponse, List<WebSearchResult> searchResults, 
                                         long startTime, boolean webSearchPerformed) {
        ChatResponse response = new ChatResponse(aiResponse);
        response.setWebSearchPerformed(webSearchPerformed);
        response.setProcessingTimeMs(System.currentTimeMillis() - startTime);
        
        if (!searchResults.isEmpty()) {
            List<String> sources = searchResults.stream()
                    .map(result -> result.getTitle() + " (" + result.getUrl() + ")")
                    .collect(Collectors.toList());
            response.setSourcesConsulted(sources);
        }
        
        return response;
    }
}