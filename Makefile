APP_NAME=eve-chatgpt-proxy
MVN=mvn
JAR=$(shell ls target/$(APP_NAME)-*.jar 2>/dev/null | head -n1)

.PHONY: help build dev test clean

help:
	@echo "Available targets:";
	@echo "  make build    - Clean and package the application";
	@echo "  make dev      - Run Spring Boot app in dev mode (spring-boot:run)";
	@echo "  make test     - Run the test suite";
	@echo "  make clean    - Remove build artifacts";
	@echo "";
	@echo "Examples:";
	@echo "  OPENAI_API_KEY=xxx make dev";

build:
	$(MVN) clean package

# Dev mode with automatic restart (via spring-boot-devtools)
dev:
	$(MVN) spring-boot:run

test:
	$(MVN) test

clean:
	$(MVN) clean
