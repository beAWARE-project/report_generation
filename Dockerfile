FROM openjdk:9-jre

COPY target/report-generation-standalone.jar .

WORKDIR .

CMD ["java", "-jar", "report-generation-standalone.jar"]
