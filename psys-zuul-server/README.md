# psys-zuul
## 描述
  zuul网关服务器

## 参考
史上最简单的SpringCloud教程 | 第五篇: 路由网关(zuul)  
https://blog.csdn.net/forezp/article/details/69939114  
  
## 测试
  搭建好后postan进行测试。
  获取游客菜单(免登录验证，测试ok).http://localhost:19080/zuul-server/api-sec/sec-server/sys/resourcetourist/query/getTouristMenu
  查询数据字典(根据id,测试没通过，待解决)http://localhost:19080/zuul-server/api-sec/sec-server/sys/code/query/getById/1

