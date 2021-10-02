FROM java:8-jdk

ENV WIREMOCK_VERSION 2.10.0

RUN mkdir /wireMock/ \
    && wget https://repo1.maven.org/maven2/com/github/tomakehurst/wiremock-standalone/${WIREMOCK_VERSION}/wiremock-standalone-$WIREMOCK_VERSION.jar \
    -O /wireMock/wiremock-standalone.jar

RUN mkdir -p /wireMock/mappings
COPY mappings /wireMock/mappings/

EXPOSE 7080

WORKDIR /wireMock

ENTRYPOINT ["java", "-jar", "wiremock-standalone.jar", "--port", "7080", "--root-dir", "mappings", "--verbose"]
