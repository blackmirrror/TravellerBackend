FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
ENV DATABASE_URL jdbc:postgresql://dpg-co0u5ui0si5c73fk8bag-a.oregon-postgres.render.com:5432/traveller
ENV DATABASE_USER postgre
ENV DATABASE_PASSWORD 3hai4bY5OrHexuHpQns7vk6wUvA36EZO
EXPOSE 9090
