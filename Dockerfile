FROM adoptopenjdk:15-jre-hotspot

RUN mkdir /app

WORKDIR /app

ADD ./api/target/billing-api-1.0-SNAPSHOT.jar /app

EXPOSE 8081

CMD ["java", "-jar", "billing-api-1.0-SNAPSHOT.jar"]
#ENTRYPOINT ["java", "-jar", "image-catalog-api-1.0.0-SNAPSHOT.jar"]
#CMD java -jar image-catalog-api-1.0.0-SNAPSHOT.jar