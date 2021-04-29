FROM openjdk:8-jre-alpine
WORKDIR /home/application/java
RUN apk update && apk add bash
ADD entrypoint.sh entrypoint.sh
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh ./wait-for-it.sh
RUN ["chmod", "+x", "./wait-for-it.sh"]

COPY build/libs/notification-1.0.jar notification.jar

EXPOSE 8080

CMD [ "entrypoint.sh" ]
ENTRYPOINT ["sh"]
