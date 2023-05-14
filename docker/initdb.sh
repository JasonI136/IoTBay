#!/bin/sh

cd /app/db-derby
/app/db-derby/bin/startNetworkServer &
/app/db-derby/bin/ij << EOF
CONNECT 'jdbc:derby:iotbaydb;user=iotbay;password=iotbay;create=true'
EOF

