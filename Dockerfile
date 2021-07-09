ARG BUILD_IMAGE=maven:3.5-jdk-11
ARG RUNTIME_IMAGE=openjdk:11-jdk-slim

FROM ${BUILD_IMAGE} as dependencies
WORKDIR /app
COPY pom.xml ./
RUN mvn -B dependency:go-offline

FROM dependencies as build
WORKDIR /app
COPY src ./src
RUN mvn -B clean package

FROM ${RUNTIME_IMAGE}
COPY --from=build /app/target/demo.jar /app/demo.jar
CMD ["java", "-jar", "/app/demo.jar"]
