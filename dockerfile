FROM alpine:latest

RUN mkdir /app
RUN adduser -D -g '' appuser
RUN apk update
RUN apk add openjdk17-jre-headless

# install supervisor using python. Install python and pip first
RUN apk add python3
RUN apk add py3-pip

# install supervisor
RUN pip3 install supervisor

# copy the supervisord.conf file to /app/supervisord.conf
COPY supervisord.conf /app/supervisord.conf

RUN wget 'https://s3.eu-west-1.amazonaws.com/payara.fish/Payara+Downloads/6.2023.4/payara-6.2023.4.zip' -O /tmp/payara.zip
#COPY payara.zip /tmp/payara.zip
RUN wget 'https://dlcdn.apache.org//db/derby/db-derby-10.16.1.1/db-derby-10.16.1.1-bin.zip' -O /tmp/db-derby.zip

# unzip the file to /app/payara
RUN unzip /tmp/payara.zip -d /app

RUN unzip /tmp/db-derby.zip -d /app
RUN mv /app/payara* /app/payara
RUN mv /app/db-derby* /app/db-derby

# unzip the derby driver to /app/payara/glassfish/domains/domain1/lib/
COPY derby_driver.zip /tmp/db-derby.zip
RUN unzip /tmp/db-derby.zip -d /app/payara/glassfish/domains/domain1/lib/

#start the derby server and create the database
COPY initdb.sh /app/initdb.sh
RUN chmod +x /app/initdb.sh
RUN /app/initdb.sh

COPY ./target/IoTBay-1.0-SNAPSHOT.war /app/

# deploy the war file
RUN /app/payara/bin/asadmin start-domain && \
    /app/payara/bin/asadmin deploy --contextroot / /app/IoTBay-1.0-SNAPSHOT.war

RUN chown -R appuser /app

# expose the ports
EXPOSE 4848 8009 8080 8181

# cleanup
RUN rm -rf /tmp/*

# run supervisord
USER appuser
CMD ["supervisord", "-c", "/app/supervisord.conf", "-n"]

