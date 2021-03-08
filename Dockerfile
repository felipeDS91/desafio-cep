FROM maven:3.6.3-jdk-11-openj9 AS MAVEN_BUILD

COPY pom.xml /build/
COPY src /build/src/

WORKDIR /build/
RUN mvn package

FROM openjdk:11-jre

WORKDIR /app

COPY --from=MAVEN_BUILD /build/target/busca-cep-0.0.1-SNAPSHOT.jar /app/

ENTRYPOINT ["java", "-jar", "busca-cep-0.0.1-SNAPSHOT.jar"]