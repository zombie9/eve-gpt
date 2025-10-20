package com.evegpt.service;

import com.evegpt.model.WebSearchResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class WebScrapingService {
    
    private static final Logger logger = LoggerFactory.getLogger(WebScrapingService.class);
    
    @Value("${eve.sources.wiki.base-url}")
    private String eveWikiBaseUrl;
    
    @Value("${eve.sources.official.base-url}")
    private String eveOfficialBaseUrl;
    
    @Value("${web.scraping.timeout}")
    private int timeout;
    
    @Value("${web.scraping.user-agent}")
    private String userAgent;
    
    public List<WebSearchResult> searchEveWiki(String query) {
        List<WebSearchResult> results = new ArrayList<>();
        
        try {
            String searchUrl = eveWikiBaseUrl + "/index.php?search=" + 
                             URLEncoder.encode(query, StandardCharsets.UTF_8);
            
            logger.debug("Searching EVE Wiki: {}", searchUrl);
            
            Document doc = Jsoup.connect(searchUrl)
                    .userAgent(userAgent)
                    .timeout(timeout)
                    .get();
            
            // Parse search results from EVE University Wiki
            Elements searchResults = doc.select(".mw-search-result");
            
            for (Element result : searchResults) {
                Element titleElement = result.selectFirst(".mw-search-result-heading a");
                Element snippetElement = result.selectFirst(".searchresult");
                
                if (titleElement != null) {
                    String title = titleElement.text();
                    String url = eveWikiBaseUrl + titleElement.attr("href");
                    String content = snippetElement != null ? snippetElement.text() : "";
                    
                    WebSearchResult searchResult = new WebSearchResult(url, title, content, "eve-university");
                    results.add(searchResult);
                }
                
                // Limit results
                if (results.size() >= 5) break;
            }
            
        } catch (IOException e) {
            logger.error("Error searching EVE Wiki for query: {}", query, e);
        }
        
        return results;
    }
    
    public List<WebSearchResult> searchEveOnline(String query) {
        List<WebSearchResult> results = new ArrayList<>();
        
        try {
            // Use Google site search for EVE Online official site
            String searchUrl = "https://www.google.com/search?q=site:eveonline.com+" + 
                             URLEncoder.encode(query, StandardCharsets.UTF_8);
            
            logger.debug("Searching EVE Online official site: {}", searchUrl);
            
            Document doc = Jsoup.connect(searchUrl)
                    .userAgent(userAgent)
                    .timeout(timeout)
                    .get();
            
            // Parse Google search results
            Elements searchResults = doc.select("div.g");
            
            for (Element result : searchResults) {
                Element titleElement = result.selectFirst("h3");
                Element linkElement = result.selectFirst("a");
                Element snippetElement = result.selectFirst(".VwiC3b");
                
                if (titleElement != null && linkElement != null) {
                    String title = titleElement.text();
                    String url = linkElement.attr("href");
                    String content = snippetElement != null ? snippetElement.text() : "";
                    
                    // Only include EVE Online official results
                    if (url.contains("eveonline.com")) {
                        WebSearchResult searchResult = new WebSearchResult(url, title, content, "eve-online");
                        results.add(searchResult);
                    }
                }
                
                // Limit results
                if (results.size() >= 3) break;
            }
            
        } catch (IOException e) {
            logger.error("Error searching EVE Online for query: {}", query, e);
        }
        
        return results;
    }
    
    public String extractPageContent(String url) {
        try {
            logger.debug("Extracting content from: {}", url);
            
            Document doc = Jsoup.connect(url)
                    .userAgent(userAgent)
                    .timeout(timeout)
                    .get();
            
            // Remove script and style elements
            doc.select("script, style").remove();
            
            // Extract main content based on common selectors
            Element content = doc.selectFirst("#mw-content-text, .article-content, main, .content");
            if (content == null) {
                content = doc.body();
            }
            
            return content != null ? content.text() : "";
            
        } catch (IOException e) {
            logger.error("Error extracting content from URL: {}", url, e);
            return "";
        }
    }
}