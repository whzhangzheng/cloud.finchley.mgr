#!/bin/bash

echo "开始启动注册中心"
echo "启动端口：$1"
echo "注册到uri：$2"
echo "生效配置: $3"

nohup java -Duser.timezone=GMT+08 -jar /registry.jar --server.port=$1 --eureka.client.service-url.defaultZone=$2 --spring.profiles.active=$3 > /dev/null 2>&1 &
echo "启动注册中心完成"

tail -f /dev/null
