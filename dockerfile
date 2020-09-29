# # Pull base image.
# FROM maven:3.6.2-jdk-8  as build
# COPY src /app/src
# COPY pom.xml /app
# RUN mvn dependency:go-offline
# RUN mvn -f /app/pom.xml clean package 


# ## Stage 2 : create the docker final image
# FROM openjdk:8-jre-alpine
# WORKDIR /work/
# COPY --from=build app/target/lib /work/application/lib
# COPY --from=build app/target/*-runner.jar /work/application/app.jar

# # set up permissions for user `1001`
# RUN chmod 775 /work /work/application \
#   && chown -R 1001 /work \
#   && chmod -R "g+rwX" /work \
#   && chown -R 1001:root /work

# # EXPOSE 8080
# USER 1001

# ENTRYPOINT ["java","-Dquarkus.http.host=0.0.0.0","-Dquarkus.http.port=${PORT}", "-jar", "/work/application/app.jar"]   



# Step : Test and package
FROM maven:3.6.2-jdk-8 as target
WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline

COPY src/ /build/src/
RUN mvn package

# Step : Package image
FROM openjdk:8-jre-alpine
COPY --from=target /build/target/lib /app/lib
COPY --from=target /build/target/*-runner.jar /app/app.jar
ENTRYPOINT ["java","-Dquarkus.http.host=0.0.0.0","-Dquarkus.http.port=${PORT}", "-jar", "/app/app.jar"]   
