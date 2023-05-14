FROM alpine:latest

RUN mkdir /app
RUN adduser -D -g '' appuser
RUN apk update
RUN apk add openjdk17-jre-headless python3 py3-pip

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

COPY docker/* /app/
RUN chmod +x /app/*.sh

#start the derby server and create the database
RUN chmod +x /app/initdb.sh
RUN /app/initdb.sh

# deploy the war file
COPY ./target/IoTBay.war /app/
RUN /app/init-payara.sh

# expose the ports
EXPOSE 4848 8009 8080 8181

# cleanup
RUN rm -rf /tmp/*
RUN chown -R appuser /app

# run container
USER appuser
ENTRYPOINT ["/app/start.sh"]

