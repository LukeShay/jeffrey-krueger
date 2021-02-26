FROM openjdk:11-jdk as BUILD

ARG TARGET=build

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon ${TARGET}

FROM openjdk:11-jre

COPY --from=BUILD /src/build/distributions/discord-bot-0.0.1-SNAPSHOT.tar /bin/runner/run.tar
WORKDIR /bin/runner

RUN tar -xvf run.tar && \
    cp -r discord-bot-0.0.1-SNAPSHOT/* . && \
    rm -r discord-bot-0.0.1-SNAPSHOT.tar run.tar

CMD ["sh"]
ENTRYPOINT ["./bin/discord-bot"]