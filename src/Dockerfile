FROM maven:3.8.5-openjdk-17
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/employee_management-0.0.1-SNAPSHOT.jar employee_management.jar
EXPOSE 8082
ENTRYPOINT ["java","-jar","employee_management.jar"]