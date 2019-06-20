FROM openjdk:8-jdk-slim
VOLUME /tmp/log
ADD ./build/start.sh /bin/
ADD ./target/*.jar /registry.jar
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN chmod a+x /bin/start.sh
EXPOSE 8761
ENTRYPOINT ["/bin/start.sh"]