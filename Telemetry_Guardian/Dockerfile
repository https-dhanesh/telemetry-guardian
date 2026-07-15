FROM maven:3.9.9-eclipse-temurin-24-alpine AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:24-jre-alpine
WORKDIR /app
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
COPY --from=builder /app/target/telemetry-guardian-1.0-SNAPSHOT.jar app.jar
USER appuser
ENTRYPOINT ["java", "-cp", "app.jar", "com.guardian.telemetry.Main"]