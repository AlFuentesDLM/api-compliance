FROM openjdk:11
ENV APP_HOME=/usr/app
COPY . usr/app
EXPOSE 8080
WORKDIR /usr/app
ENV DB_USER=root
ENV DB_PASS=password
ENV DB_URL=host.docker.internal:3306
ENV DB_SCHEMA=db
ENV USE_SSL=false
CMD ["java", "-jar","./target/apicompliance-0.0.1-SNAPSHOT.jar"]