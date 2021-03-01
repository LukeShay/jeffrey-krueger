FROM openjdk:11-jdk as BUILD

ARG TARGET=shadowJar
ARG SHA=asdf12

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon ${TARGET} -Dapp.version=${SHA}

FROM openjdk:11-jre

ARG SHA=asdf12

COPY --from=BUILD /src/build/libs/jeffery-krueger-${SHA}-all.jar /bin/runner/app.jar
WORKDIR /bin/runner

CMD ["sh"]
ENTRYPOINT ["java", "-jar", "app.jar"]