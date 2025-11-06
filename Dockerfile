FROM openjdk:26-ea-slim

# Set working directory (docker creates a folder /app)
WORKDIR /app

# Copy the JAR file to created folder
COPY build/libs/miro-mcp-server.jar .

# Default entrypoint (token should be provided via -e environment variables)
ENTRYPOINT ["java", "-jar", "/app/miro-mcp-server.jar", "--token=${MIRO_TOKEN}"]

# ./gradlew build
# docker login
# docker image ls
# docker build -t maratmingazovr/miro-mcp-server:1.0 .
# docker push maratmingazovr/miro-mcp-server:1.0
# docker pull maratmingazovr/miro-mcp-server:1.0
# echo "DOCKER_TOKEN" | docker login -u maratmingazovr --password-stdin
# docker run -e MIRO_TOKEN=abc123 maratmingazovr/miro-mcp-server:1.0