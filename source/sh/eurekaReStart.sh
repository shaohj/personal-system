ps -ef|grep psys-eureka-server.jar|grep -v grep|head -1|awk '{print $2}'|xargs -ti kill -9 {}
nohup java -jar /usr/local/app/psys-eureka-server.jar > eureka_log.file 2>&1 &
tail -f /usr/local/app/sh/eureka_log.file
