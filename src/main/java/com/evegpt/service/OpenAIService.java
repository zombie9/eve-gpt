package com.evegpt.service;

import com.evegpt.model.ChatRequest;
import com.evegpt.model.WebSearchResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OpenAIService {
    
    private static final Logger logger = LoggerFactory.getLogger(OpenAIService.class);
    
    @Value("${openai.api.key}")
    private String openAiApiKey;
    
    @Value("${openai.api.url}")
    private String openAiApiUrl;
    
    @Value("${openai.model}")
    private String model;
    
    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    
    public OpenAIService(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }
    
    public Mono<String> generateResponse(ChatRequest request, List<WebSearchResult> searchResults) {
        String enhancedPrompt = buildEnhancedPrompt(request.getUserPrompt(), searchResults);
        
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("messages", List.of(
            Map.of("role", "system", "content", getSystemPrompt()),
            Map.of("role", "user", "content", enhancedPrompt)
        ));
        requestBody.put("max_tokens", request.getMaxTokens());
        requestBody.put("temperature", request.getTemperature());
        
        logger.debug("Sending request to OpenAI API");
        
        return webClient.post()
                .uri(openAiApiUrl)
                .header("Authorization", "Bearer " + openAiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .map(this::extractResponseContent)
                .doOnError(error -> logger.error("Error calling OpenAI API", error));
    }
    
    private String getSystemPrompt() {
        return """
                You are an expert assistant specializing in EVE Online, the massively multiplayer online game.
                
                Your expertise includes:
                - Game mechanics (combat, exploration, industry, trade)
                - Ships and fittings
                - Market analysis and economics
                - Corporation and alliance management
                - PvP and PvE strategies
                - Mining and industry
                - Wormhole space (J-space)
                - Null security space mechanics
                - Player-driven politics and meta-game
                
                Always provide accurate, up-to-date information about EVE Online. When referencing specific game
                mechanics, ships, or systems, be precise and detailed. If you're unsure about current information,
                indicate that the information should be verified in-game or through official sources.
                
                Use the provided web search results to ensure your responses are current and accurate.
                """;
    }
    
    private String buildEnhancedPrompt(String originalPrompt, List<WebSearchResult> searchResults) {
        StringBuilder enhancedPrompt = new StringBuilder();
        enhancedPrompt.append("User Query: ").append(originalPrompt).append("\n\n");
        
        if (!searchResults.isEmpty()) {
            enhancedPrompt.append("Recent EVE Online Information from Web Sources:\n");
            
            for (WebSearchResult result : searchResults) {
                enhancedPrompt.append("\nSource: ").append(result.getSource())
                            .append(" (").append(result.getUrl()).append(")\n")
                            .append("Title: ").append(result.getTitle()).append("\n")
                            .append("Content: ").append(result.getContent()).append("\n");
            }
            
            enhancedPrompt.append("\nPlease provide a comprehensive answer based on the user's query and the above sources. ");
            enhancedPrompt.append("Reference specific sources when applicable and ensure the information is current.\n");
        }
        
        return enhancedPrompt.toString();
    }
    
    private String extractResponseContent(String apiResponse) {
        try {
            JsonNode root = objectMapper.readTree(apiResponse);
            JsonNode choices = root.get("choices");
            
            if (choices != null && choices.isArray() && choices.size() > 0) {
                JsonNode firstChoice = choices.get(0);
                JsonNode message = firstChoice.get("message");
                JsonNode content = message.get("content");
                
                return content != null ? content.asText() : "No response generated";
            }
            
            return "Unable to parse response from OpenAI";
            
        } catch (Exception e) {
            logger.error("Error parsing OpenAI response", e);
            return "Error processing response from OpenAI";
        }
    }
}