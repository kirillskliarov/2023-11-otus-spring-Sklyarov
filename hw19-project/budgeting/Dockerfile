FROM bellsoft/liberica-openjdk-alpine-musl:21.0.1
COPY /target/sso.jar /app/sso.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/sso.jar"]
