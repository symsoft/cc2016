FROM java:8
COPY target/orderservice-1.0-SNAPSHOT.jar /
WORKDIR /
EXPOSE 9997
CMD ["java", "-cp",  "orderservice-1.0-SNAPSHOT.jar",  "se.symsoft.cc2016.orderservice.OrderService"]