ps -ef|grep psys-sys-web-server.jar|grep -v grep|head -1|awk '{print $2}'|xargs -ti kill -9 {}
