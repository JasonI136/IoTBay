# IoTBay

## About
This project was developed as part of Assigment 1 & 2 for the 41025 Introduction to Software Development course from the University of Technology Sydney. The application was written using Java, using Jakarta EE 10 (formerly Java EE) and Apache Derby 10.10.2.0 database.

## Setup
1. In the project root directory, create a file called 'secrets.properties'. The contents of the file should be as follows:
```
stripe.api.key = sk_test_XXXXXXXXXXXXXXXXXXXXXXXX
stripe.api.publishable.key = pk_test_XXXXXXXXXXXXXXXXXXXXXXXX
```
2. Run using docker below.

**Note:** It is recommended to only allow port 4848 to be accessible from localhost.
### Docker
```shell
docker run \
    --name iotbay \
    -p 8080:8080 \
    -p 127.0.0.1:4848:4848 \
    -v /path/to/iotbay/secrets.properties:/app/payara/glassfish/domains/domain1/config/secrets.properties \
    -d \
    --rm \
    mclarence/iotbay:latest
```
### Docker Compose
```yaml
version: "3.9"
services:
  iotbay:
    image: mclarence/iotbay:latest
    ports:
      - 8080:8080
      - 127.0.0.1:4848:4848
    volumes:
      - /path/to/secrets.properties:/app/payara/glassfish/domains/domain1/config/secrets.properties
    restart: always
```
Replace `/path/to/secrets.properties` with the path to the `secrets.properties` file created in the setup step 1.
