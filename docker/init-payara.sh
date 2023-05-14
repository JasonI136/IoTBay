#!/bin/sh

. /app/env.sh

/app/reset-as-admin-pw.sh 123

touch /tmp/asadmin_pw.txt
echo "AS_ADMIN_PASSWORD=123" > /tmp/asadmin_pw.txt

$ASADMIN_BIN start-domain
$ASADMIN_BIN --user admin --passwordfile=/tmp/asadmin_pw.txt enable-secure-admin
$ASADMIN_BIN restart-domain
$ASADMIN_BIN --user admin --passwordfile=/tmp/asadmin_pw.txt deploy --contextroot / "$APP_DIR"/IoTBay.war
$ASADMIN_BIN stop-domain

rm /tmp/asadmin_pw.txt
