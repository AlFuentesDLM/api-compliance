FROM openjdk:11
ENV APP_HOME=/usr/app
WORKDIR /usr/app
COPY target/apicompliance-0.0.1-SNAPSHOT.jar usr/app
ENV DB_USER=""
ENV DB_PASS=""
ENV DB_URL=""
ENV SCHEMA=""
ENV USE_SSL=""
EXPOSE 8080
CMD ["java", "-jar","apicompliance-0.0.1-SNAPSHOT.jar"]