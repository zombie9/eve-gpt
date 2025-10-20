# EVE Online ChatGPT Proxy API

A Spring Boot REST API that serves as an intelligent proxy for ChatGPT conversations, specializing in EVE Online knowledge with enhanced web search capabilities from authoritative EVE sources.

## 🚀 Features

- **ChatGPT Integration**: Seamless proxy to OpenAI's GPT-4 API
- **EVE Online Expertise**: Specialized system prompts for accurate EVE Online information
- **Intelligent Web Search**: Automatic scraping of wiki.eveuniversity.org and eveonline.com
- **Enhanced Responses**: Context-aware responses using up-to-date EVE data
- **RESTful API**: Clean, well-documented endpoints
- **Spring Boot**: Modern Java framework with excellent tooling support

## 🛠 Technology Stack

- **Java 17+** - Modern Java features
- **Spring Boot 3.2** - Application framework
- **Spring WebFlux** - Reactive web framework for HTTP clients
- **JSoup** - HTML parsing and web scraping
- **Maven** - Dependency management and build tool
- **Jackson** - JSON processing

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+
- OpenAI API key
- Internet connection for web scraping

## 🔧 Setup & Installation

### 1. Clone or Download the Project
The project is already set up in your current directory: `/Users/dbs13/projects/personal/eve-gpt`

### 2. Configure Environment Variables
Create a `.env` file or set environment variables:

```bash
export OPENAI_API_KEY="your-openai-api-key-here"
```

### 3. Update Configuration
Edit `src/main/resources/application.properties`:

```properties
# Replace with your actual OpenAI API key
openai.api.key=${OPENAI_API_KEY:your-openai-api-key-here}
```

### 4. Build the Project
```bash
mvn clean compile
```

### 5. Run the Application
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8083`

## 📖 API Documentation

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
Create a `Dockerfile`:
```dockerfile
FROM openjdk:17-jdk-slim
COPY target/eve-chatgpt-proxy-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 🔐 Security Considerations

- **API Key Security**: Never commit API keys to version control
- **CORS Configuration**: Currently allows all origins - configure for production
- **Rate Limiting**: Consider implementing rate limiting for production use
- **Input Validation**: Basic validation is implemented, enhance as needed

## 🐛 Troubleshooting

### Common Issues

**Port Already in Use**
- The application uses port 8083 by default
- Change in `application.properties`: `server.port=8084`

**OpenAI API Errors**
- Verify your API key is valid
- Check your OpenAI account has sufficient credits
- Ensure the API key has chat completion permissions

**Web Scraping Issues**
- Some sites may block automated requests
- Adjust timeout settings in `application.properties`
- Check internet connectivity

## 📝 Example Usage

### Simple Query
```bash
curl -X POST http://localhost:8083/api/v1/chat \
  -H "Content-Type: application/json" \
  -d '{"prompt": "What is the best mining ship for a beginner?"}'
```

### Complex Query with Custom Parameters
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

This project is open source. Add your preferred license here.

## 🆘 Support

For issues or questions:
1. Check the troubleshooting section
2. Review application logs
3. Create an issue in the repository

---

**Happy flying in New Eden! o7**