package com.evegpt.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "eve.sources")
public class EveSourcesProperties {

    private Wiki wiki = new Wiki();
    private Official official = new Official();

    public Wiki getWiki() { return wiki; }
    public void setWiki(Wiki wiki) { this.wiki = wiki; }
    public Official getOfficial() { return official; }
    public void setOfficial(Official official) { this.official = official; }

    public static class Wiki {
        /** Base URL for EVE University Wiki */
        private String baseUrl;
        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    }

    public static class Official {
        /** Base URL for official EVE Online site */
        private String baseUrl;
        public String getBaseUrl() { return baseUrl; }
        public void setBaseUrl(String baseUrl) { this.baseUrl = baseUrl; }
    }
}
