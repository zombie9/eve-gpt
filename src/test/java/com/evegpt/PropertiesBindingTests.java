package com.evegpt;

import com.evegpt.config.OpenAIProperties;
import com.evegpt.config.WebScrapingProperties;
import com.evegpt.config.EveSourcesProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PropertiesBindingTests {

    @Autowired
    private OpenAIProperties openAIProperties;
    @Autowired
    private WebScrapingProperties webScrapingProperties;
    @Autowired
    private EveSourcesProperties eveSourcesProperties;

    @Test
    void openAiPropertiesShouldBind() {
    assertThat(openAIProperties.getApi().getUrl()).isNotBlank();
    assertThat(openAIProperties.getModel()).isNotBlank();
    }

    @Test
    void webScrapingPropertiesShouldBind() {
        assertThat(webScrapingProperties.getTimeout()).isNotNull();
    }

    @Test
    void eveSourcesPropertiesShouldBind() {
        assertThat(eveSourcesProperties.getWiki().getBaseUrl()).isNotBlank();
        assertThat(eveSourcesProperties.getOfficial().getBaseUrl()).isNotBlank();
    }
}
