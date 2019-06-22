ps -ef|grep psys-sec-server.jar|grep -v grep|head -1|awk '{print $2}'|xargs -ti kill -9 {}
nohup java -jar /usr/local/app/psys-sec-server.jar > sec_log.file 2>&1 &
tail -f /usr/local/app/sh/sec_log.file
