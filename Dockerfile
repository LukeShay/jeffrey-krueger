FROM openjdk:11-jdk as BUILD

ARG TARGET=shadowJar

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon ${TARGET}

FROM openjdk:11-jre

ARG SHA=asdf12

COPY --from=BUILD /src/build/libs/discord-bot-${SHA}-all.jar /bin/runner/app.jar
WORKDIR /bin/runner

CMD ["sh"]
ENTRYPOINT ["java", "-jar", "app.jar"]