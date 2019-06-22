ps -ef|grep psys-sec-server.jar|grep -v grep|head -1|awk '{print $2}'|xargs -ti kill -9 {}
