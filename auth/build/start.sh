#!/bin/bash

echo "开始启动路由应用"
echo "启动端口：$1"
echo "注册到uri：$2"
echo "生效配置: $3"

nohup java -Duser.timezone=GMT+08 -jar /auth.jar --server.port=$1 --eureka.client.service-url.defaultZone=$2 --spring.profiles.active=$3 > /dev/null 2>&1 &
echo "启动路由应用完成"

tail -f /dev/null
