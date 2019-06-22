ps -ef|grep psys-auth-server.jar|grep -v grep|head -1|awk '{print $2}'|xargs -ti kill -9 {}
nohup java -jar /usr/local/app/psys-auth-server.jar > auth_log.file 2>&1 &
tail -f /usr/local/app/sh/auth_log.file
