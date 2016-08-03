FROM java:8
COPY target/customerservice-1.0-SNAPSHOT.jar /
WORKDIR /
EXPOSE 9998
CMD ["java", "-cp",  "customerservice-1.0-SNAPSHOT.jar",  "se.symsoft.cc2016.customerservice.CustomerService"]