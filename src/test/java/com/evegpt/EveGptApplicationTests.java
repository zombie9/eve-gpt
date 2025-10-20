package com.evegpt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
    "openai.api.key=test-key",
    "eve.sources.wiki.base-url=https://wiki.eveuniversity.org",
    "eve.sources.official.base-url=https://www.eveonline.com"
})
class EveGptApplicationTests {

    @Test
    void contextLoads() {
    }
}