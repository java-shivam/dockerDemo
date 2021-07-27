FROM openjdk:8
WORKDIR /usr/demo
COPY ./ ./
CMD ["java","-jar","/usr/demo/target/dockerDemo-2.4.0.jar"]