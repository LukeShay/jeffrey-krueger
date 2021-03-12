FROM openjdk:11-jdk as BUILD

ARG PROJECT=jeffery-bot
ARG TARGET=shadowJar
ARG SHA=asdf12

COPY . /src
WORKDIR /src
RUN ./gradlew ${PROJECT}:${TARGET} --no-daemon -Dapp.version=${SHA}

FROM openjdk:11-jre

ARG PROJECT=jeffery-bot
ARG TARGET=shadowJar
ARG SHA=asdf12

COPY --from=BUILD /src/${PROJECT}/build/libs/${PROJECT}-${SHA}-all.jar /bin/runner/app.jar
WORKDIR /bin/runner

CMD ["sh"]
ENTRYPOINT ["java", "-jar", "app.jar"]