FROM openjdk:8

COPY target/report-generation-standalone.jar .

WORKDIR .

CMD ["java", "-jar", "report-generation-standalone.jar"]
