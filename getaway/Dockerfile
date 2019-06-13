FROM openjdk:8-jdk-alpine
VOLUME /tmp/log
ADD ./build/start.sh /bin/
ADD ./target/*.jar /getaway.jar
RUN cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN chmod a+x /bin/start.sh
EXPOSE 81
ENTRYPOINT ["/bin/start.sh"]