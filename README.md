# DataManager


* 项目环境搭建
1. MySQL 5.6
2. MyEclipse 10
3. apache-maven-3.2.3



* 部署流程
  CLOCK: [2016-03-27 周日 09:07]--[2016-03-27 周日 09:48] =>  0:41

数据库MySQL 5.5，字符集为utf8_general_ci
内网地址：10.66.112.154:3306
外网地址：56298ec833780.gz.cdb.myqcloud.com:7358
数据库名 test3
用户 test3	
密码 tt3

mysql -h56298ec833780.gz.cdb.myqcloud.com  -P 7358 -utest3 -ptt3

---------

服务器：119.29.102.203
用户 test3	
密码 tt3
我在用户目录下已经配好tomcat7了，对外端口是 22080。请不要改端口，以免冲突。配置了防火墙，改成其他端口访问不了。




ssh test3@119.29.102.203:22080

scp DataManager.war test3@119.29.102.203:/ebs/test3/apache-tomcat-7.0.68/webapps

 
---------
部署数据库

phpadmin 登录上去



---------

119.29.102.203:22080




** DONE 中文字符不见的问题
   - State "DONE"       from ""           [2016-03-27 周日 10:56]
   CLOCK: [2016-03-27 周日 10:01]--[2016-03-27 周日 10:06] =>  0:05

1. mysql 登录的时候，插入数据中文乱码的缘故（就是这个原因）

2. 数据库？

3. tomcat7 ?


