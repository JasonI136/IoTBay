#!/bin/sh

. /app/env.sh

NEW_PASSWORD=$1
# reset the admin password to empty
echo "admin;{SSHA}WQVj8i9CLECCiv+w6ZxGgMrcfPqHPoXZW+2Jdw==;asadmin" > "$PAYARA_DIR"/glassfish/domains/domain1/config/admin-keyfile

touch /tmp/asadmin_pw.txt
echo "AS_ADMIN_PASSWORD=" > /tmp/asadmin_pw.txt
echo "AS_ADMIN_NEWPASSWORD=$NEW_PASSWORD" >> /tmp/asadmin_pw.txt

$ASADMIN_BIN --user admin --passwordfile=/tmp/asadmin_pw.txt change-admin-password

rm /tmp/asadmin_pw.txt