# EVE Online ChatGPT Proxy API

A Spring Boot REST API that serves as an intelligent proxy for ChatGPT conversations, specialising in EVE Online knowledge with enhanced web search capabilities from authoritative EVE sources.

## Features

- **ChatGPT Integration**: Seamless proxy to OpenAI's GPT-4 API
- **EVE Online Expertise**: Specialized system prompts for accurate EVE Online information
- **Intelligent Web Search**: Automatic scraping of wiki.eveuniversity.org and eveonline.com
- **Enhanced Responses**: Context-aware responses using up-to-date EVE data
- **RESTful API**: Clean, well-documented endpoints
- **Spring Boot**: Modern Java framework with excellent tooling support

## Technology Stack

- **Java 21** - Latest LTS (virtual threads, pattern matching, improved performance)
- **Spring Boot 3.5.6** - Modern framework, AOT & observability improvements
- **Spring WebFlux** - Reactive HTTP client/server support
- **JSoup 1.17.1** - HTML parsing & scraping
- **Jackson Databind 2.19.0** - JSON serialization/deserialization
- **Project Reactor 3.7.6** - Reactive streams foundation
- **spring-dotenv 4.0.0** - Optional .env support for local development
- **Maven** - Build & dependency management

## Prerequisites

- Java 21
- Maven 3.9+
- OpenAI API key (sk- or sk-proj- prefixed)
- Internet connection for web scraping

## Setup & Installation

### 1. Clone or Download the Project
The project is already set up in your current directory: `/Users/dbs13/projects/personal/eve-gpt`

### 2. Configure Environment Variables
You can either export variables or use a `.env` file (loaded by `spring-dotenv` at runtime if present):

```bash
echo 'OPENAI_API_KEY=your-openai-api-key-here' > .env
export OPENAI_API_KEY=your-openai-api-key-here   # alternative
```

### 3. Configuration Properties
The application uses type-safe `@ConfigurationProperties` classes. Key properties:

```properties
# OpenAI
openai.api.key=${OPENAI_API_KEY}
openai.api.url=https://api.openai.com/v1/chat/completions
openai.model=gpt-4o-mini

# Web scraping
web.scraping.timeout=30000            # milliseconds
web.scraping.user-agent=Mozilla/5.0 (compatible; EVE-GPT-Bot/1.0)

# EVE Online sources
eve.sources.wiki.base-url=https://wiki.eveuniversity.org
eve.sources.official.base-url=https://www.eveonline.com
```

Binding classes:
- `OpenAIProperties` → prefix `openai.*` (nested `api.key`, `api.url`, and `model`)
- `WebScrapingProperties` → prefix `web.scraping.*`
- `EveSourcesProperties` → prefix `eve.sources.*`

All custom properties generate metadata (via `spring-boot-configuration-processor`) for IDE completion.

### 4. Build the Project
```bash
mvn clean compile
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8083`

## API Documentation

### Health Check
```http
GET /api/v1/health
```
Returns: `"EVE ChatGPT Proxy API is running"`

### Application Info
```http
GET /api/v1/info
```
Returns application metadata and supported sources.

### Chat Endpoint
```http
POST /api/v1/chat
Content-Type: application/json

{
  "prompt": "How do I fit a Drake for ratting in null-sec?",
  "include_web_search": true,
  "max_tokens": 1500,
  "temperature": 0.7
}
```

**Response:**
```json
{
  "response": "For ratting in null-sec with a Drake...",
  "sources_consulted": [
    "Drake fitting guide (https://wiki.eveuniversity.org/...)",
    "Ratting in Null Security Space (https://wiki.eveuniversity.org/...)"
  ],
  "web_search_performed": true,
  "timestamp": "2025-10-16T...",
  "processing_time_ms": 2500
}
```

## 🎮 EVE Online Integration

The API specializes in EVE Online topics including:
- **Ship Fittings & Loadouts**
- **Combat Mechanics** (PvP/PvE)
- **Market Analysis & Trading**
- **Industry & Manufacturing**
- **Exploration & Wormholes**
- **Corporation Management**
- **Player Politics & Meta-game**

### Intelligent Source Prioritization
1. **EVE University Wiki** - Comprehensive game mechanics
2. **EVE Online Official** - Latest updates and official information
3. **ChatGPT Knowledge** - General EVE expertise

## 🔬 Development

### VS Code Tasks
The project includes pre-configured VS Code tasks:
- **Build Project**: Compiles the application
- **Run Application**: Starts the Spring Boot server

### Makefile Commands
You can use the `Makefile` for common workflows:

```
make help          # List available targets
make build         # Clean and package the application (jar in target/)
make dev           # Run the app with spring-boot:run
make test          # Execute the test suite
make clean         # Remove build artifacts
```

Examples:
```
OPENAI_API_KEY=sk-xxxxx make dev
make build && java -jar target/eve-chatgpt-proxy-0.0.1-SNAPSHOT.jar
```

If you change the artifact name or version, adjust `APP_NAME` in the `Makefile` accordingly.

### Project Structure
```
src/
├── main/
│   ├── java/com/evegpt/
│   │   ├── EveGptApplication.java       # Main application class
│   │   ├── controller/
│   │   │   └── ChatController.java      # REST endpoints
│   │   ├── service/
│   │   │   ├── ChatService.java         # Main orchestration
│   │   │   ├── OpenAIService.java       # ChatGPT integration
│   │   │   └── WebScrapingService.java  # Web scraping logic
│   │   ├── model/
│   │   │   ├── ChatRequest.java         # Request DTOs
│   │   │   ├── ChatResponse.java        # Response DTOs
│   │   │   └── WebSearchResult.java     # Search result model
│   │   └── config/
│   │       └── WebClientConfig.java     # HTTP client configuration
│   └── resources/
│       └── application.properties       # Configuration
└── test/
    └── java/com/evegpt/
        └── EveGptApplicationTests.java  # Basic tests
```

## 🚀 Deployment

### Local Development
```bash
mvn spring-boot:run
```

### Production Build
```bash
mvn clean package
java -jar target/eve-chatgpt-proxy-0.0.1-SNAPSHOT.jar
```

### Docker (Optional)
Create a `Dockerfile` optimized for Java 21 runtime size:
```dockerfile
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/eve-chatgpt-proxy-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
# Pass OPENAI_API_KEY at runtime: docker run -e OPENAI_API_KEY=xxx ...
ENTRYPOINT ["java", "-jar", "app.jar"]
```

## 🔐 Security Considerations

- **API Key Security**: Never commit API keys to version control
- **CORS Configuration**: Currently allows all origins - configure for production
- **Rate Limiting**: Consider implementing rate limiting for production use
- **Input Validation**: Basic validation is implemented, enhance as needed

## 📦 Dependency Versions

| Dependency | Version |
|------------|---------|
| Spring Boot | 3.5.6 |
| Java | 21 |
| JSoup | 1.17.1 |
| Jackson Databind | 2.19.0 |
| Reactor (reactor-test) | 3.7.6 |
| spring-dotenv | 4.0.0 |

## 🐛 Troubleshooting

### Common Issues

**Port Already in Use**
- The application uses port 8083 by default
- Change in `application.properties`: `server.port=8084`

**OpenAI API Errors**
- Verify API key export or `.env` presence (check logs for key length + prefix)
- Confirm model name (`openai.model`) matches available tier
- Adjust `openai.api.url` if using Azure OpenAI (e.g., custom endpoint + deployment name)

**Web Scraping Issues**
- Some sites may block automated requests
- Adjust timeout settings in `application.properties`
- Check internet connectivity

## 📝 Example Usage

### Simple Query (No Web Search)
```bash
curl -X POST http://localhost:8083/api/v1/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt": "What is the best mining ship for a beginner?"}'
```

### Query With Web Search Enabled
```bash
curl -X POST http://localhost:8083/api/v1/chat \
  -H "Content-Type: application/json" \
  -d '{
    "prompt": "Explain wormhole mechanics and how to scan them down",
    "include_web_search": true,
    "max_tokens": 2000,
    "temperature": 0.5
  }'
```

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## 📄 License

This project is open source under the MIT license.

## 🆘 Support

For issues or questions:
1. Check the troubleshooting section
2. Review application logs
3. Create an issue in the repository

---

**Happy flying in New Eden! o7**