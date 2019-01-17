# securitydemo
1.基于springboot 整合的cas 和security认证实例 
2.cas server的代码在https://github.com/18353153007/cas-server-webapp
3.如果想要多个sso client配置可以参照https://github.com/18353153007/mycasdemo
4.由于没有做登录页面，用户的插入请执行/user/insert自动出入用户。
5.不同的applicationContext-security文件有不通的作用，具体作用参照注释。如需更换文件请在SecuritydemoApplication中更换ImportResource
