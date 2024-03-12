# Stage 1 - build jar
FROM gradle:8.6.0-jdk17 AS base
WORKDIR /app
COPY . .
RUN gradle build

# Create production image for jar run
FROM openjdk:17-alpine AS prod
WORKDIR /app
COPY --from=base /app/build/libs/shop-cart-java-1.0.jar /app/shop-cart-java-1.0.jar
CMD ["java", "-jar", "shop-cart-java-1.0.jar"]
