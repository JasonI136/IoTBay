#!/bin/sh

. /app/env.sh

# check if ASADMIN_PASSWORD is set
if [ -z "$AS_ADMIN_PASSWORD" ]; then
  echo "AS_ADMIN_PASSWORD is not set"
  exit 1
fi

/app/reset-as-admin-pw.sh $AS_ADMIN_PASSWORD

# start supervisord
/usr/bin/supervisord -c "$APP_DIR"/supervisord.conf
