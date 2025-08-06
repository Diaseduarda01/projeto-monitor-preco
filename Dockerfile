# Etapa de build
FROM amazoncorretto:21-alpine3.20-jdk AS builder
WORKDIR /builder

# Instala Maven
RUN apk --no-cache add maven

# Copia o projeto
COPY pom.xml ./
COPY . .

# Build do projeto
RUN mvn -B -e clean install -DskipTests=true
RUN cp ./target/*.jar ./application.jar

# Extrai camadas do Spring Boot
RUN java -Djarmode=tools -jar application.jar extract --layers --destination extracted

# Etapa final (runtime)
FROM bellsoft/liberica-openjre-debian:21-cds
WORKDIR /application

# Copia as camadas do Spring Boot
COPY --from=builder /builder/extracted/dependencies/ ./
COPY --from=builder /builder/extracted/spring-boot-loader/ ./
COPY --from=builder /builder/extracted/snapshot-dependencies/ ./
COPY --from=builder /builder/extracted/application/ ./

# Cria usuário não-root
RUN adduser --system --group spring
USER spring:spring

ENTRYPOINT ["java", "-XX:SharedArchiveFile=application.jsa", "-jar", "application.jar"]