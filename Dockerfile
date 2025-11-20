
# Build stage
FROM maven:3.9.5-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies (for better caching)
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy the built JAR from build stage
COPY --from=build /app/target/Employee_Management-0.0.1-SNAPSHOT.jar app.jar

# Render expects the app to listen on the PORT environment variable
# Default to 8082 if PORT is not set
ENV PORT=8082

# Expose port (dynamic for Render)
EXPOSE ${PORT}

# Run the application with dynamic port binding
ENTRYPOINT ["sh", "-c", "java -jar -Dserver.port=${PORT} app.jar"]