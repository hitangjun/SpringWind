
# SpringWind 说明


> 简单介绍

```
该项目为 SSM 核心库 spring-wind 演示项目

http://git.oschina.net/juapk/spring-wind
```


> 运行项目配置说明

```
1、根据 /SpringWind/src/main/resources/properties/jdbc.properties 配置数据库

2、导入数据库 /SpringWind/src/test/resources/springwind.sql

3、导入Quartz 相关表
   a) MySql MyISAM引擎（默认）：/SpringWind/src/test/resources/quartz/quartz_mysql.sql
   b) MySql InnoDB引擎：       /SpringWind/src/test/resources/quartz/quartz_mysql_innodb.sql
   c) 其他数据库版本请到 http://www.quartz-scheduler.org/downloads/ 下载

4、配置 host 为  127.0.0.1 demo.baomidou.com

5、访问：http://demo.baomidou.com:8080 登录账户默认：  admin 管理员，密码 123 ，普通会员 test 密码  123

```


> 测试权限

```
1、test 账户登录系统

2、访问地址：http://demo.baomidou.com:8080/role/list.html
```

> Quartz 使用说明
```
1、 使用QuartzJobManager类进行Job的添加和删除操作

2、 所有的Job必须继承QuartzJobSupport类

```