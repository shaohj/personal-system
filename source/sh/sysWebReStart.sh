ps -ef|grep psys-sys-web-server.jar|grep -v grep|head -1|awk '{print $2}'|xargs -ti kill -9 {}
nohup java -jar /usr/local/app/psys-sys-web-server.jar > sys-web_log.file 2>&1 &
tail -f /usr/local/app/sh/sys-web_log.file
