#!/bin/bash

nohup java -Duser.timezone=GMT+08 -jar /app.jar --server.port=$1 --eureka.client.service-url.defaultZone=$2 --spring.profiles.active=$3 > /dev/null 2>&1 &

tail -f /dev/null
