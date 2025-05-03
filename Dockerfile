FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
# ENV DATABASE_URL jdbc:postgresql://dpg-cpk1euf109ks73eu9c3g-a.oregon-postgres.render.com/traveller_n9b3
# ENV DATABASE_USER traveller_n9b3_user
# ENV DATABASE_PASSWORD pWxo5Rd2Pv3EouSKZ9WygfmHfVYwfoQp
ENV DATABASE_URL jdbc:postgresql://dpg-d0b0bpadbo4c73c9qjvg-a.oregon-postgres.render.com/traveler_ld4c
ENV DATABASE_USER traveler_ld4c_user
ENV DATABASE_PASSWORD LcifQvyVKFkW8EeZsWzGVmHgox4nO28p
EXPOSE 9090
