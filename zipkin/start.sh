#!/bin/bash

RABBIT_ADDRESSES=10.50.11.141:5672 RABBIT_USER=guest RABBIT_PASSWORD=guest STORAGE_TYPE=mysql MYSQL_HOST=10.100.254.185 MYSQL_TCP_PORT=3306 MYSQL_USER=root MYSQL_PASS=bjc2015 MYSQL_DB=zipkin MYSQL_USE_SSL=false nohup java -Duser.timezone=GMT+08 -jar zipkin-server-2.12.9-exec.jar > zipkin.log 2>&1 &

