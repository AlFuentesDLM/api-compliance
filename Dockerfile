FROM openjdk:11
ENV APP_HOME=/usr/app
COPY . usr/app
ENV DB_USER=""
ENV DB_PASS=""
ENV DB_URL=""
ENV DB_SCHEMA=db
ENV USE_SSL=true
EXPOSE 8080
WORKDIR /usr/app
CMD ["java", "-jar","./target/apicompliance-0.0.1-SNAPSHOT.jar"]