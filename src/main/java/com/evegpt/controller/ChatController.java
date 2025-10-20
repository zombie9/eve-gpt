package com.evegpt.controller;

import com.evegpt.model.ChatRequest;
import com.evegpt.model.ChatResponse;
import com.evegpt.service.ChatService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*") // Configure appropriately for production
public class ChatController {
    
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    
    private final ChatService chatService;
    
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
    
    @PostMapping("/chat")
    public Mono<ResponseEntity<ChatResponse>> chat(@Valid @RequestBody ChatRequest request) {
        logger.info("Received chat request");
        
        return chatService.processChat(request)
                .map(ResponseEntity::ok)
                .doOnNext(response -> logger.info("Chat request processed successfully"))
                .doOnError(error -> logger.error("Error processing chat request", error))
                .onErrorReturn(ResponseEntity.internalServerError().build());
    }
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("EVE ChatGPT Proxy API is running");
    }
    
    @GetMapping("/info")
    public ResponseEntity<ApiInfo> info() {
        ApiInfo apiInfo = new ApiInfo(
            "EVE Online ChatGPT Proxy",
            "1.0.0",
            "REST API proxy for ChatGPT with EVE Online expertise",
            new String[]{"wiki.eveuniversity.org", "eveonline.com"}
        );
        return ResponseEntity.ok(apiInfo);
    }
    
    public static class ApiInfo {
        public final String name;
        public final String version;
        public final String description;
        public final String[] supportedSources;
        
        public ApiInfo(String name, String version, String description, String[] supportedSources) {
            this.name = name;
            this.version = version;
            this.description = description;
            this.supportedSources = supportedSources;
        }
    }
}