FROM eclipse-temurin:17-jdk-alpine
#VOLUME /tmp
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
ENV DATABASE_URL jdbc:postgresql://dpg-cpk1euf109ks73eu9c3g-a.oregon-postgres.render.com/traveller_n9b3
ENV DATABASE_USER traveller_n9b3_user
ENV DATABASE_PASSWORD pWxo5Rd2Pv3EouSKZ9WygfmHfVYwfoQp
EXPOSE 9090
