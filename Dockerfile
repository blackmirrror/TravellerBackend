FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
ENV DATABASE_URL jdbc:postgresql://dpg-d0b0bpadbo4c73c9qjvg-a.oregon-postgres.render.com/traveler_ld4c
ENV DATABASE_USER traveler_ld4c_user
ENV DATABASE_PASSWORD LcifQvyVKFkW8EeZsWzGVmHgox4nO28p
EXPOSE 9090
