FROM openjdk:11-jdk as BUILD

COPY . /src
WORKDIR /src
RUN ./gradlew --no-daemon distTar

FROM openjdk:11-jre

COPY --from=BUILD /src/build/distributions/discord-bot.tar /bin/runner/run.tar
WORKDIR /bin/runner

RUN tar -xvf run.tar && \
    cp -r discord-bot/* . && \
    rm -r discord-bot run.tar

CMD ["sh"]
ENTRYPOINT ["./bin/discord-bot"]