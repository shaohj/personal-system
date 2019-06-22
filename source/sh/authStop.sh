ps -ef|grep psys-auth-server.jar|grep -v grep|head -1|awk '{print $2}'|xargs -ti kill -9 {}
