

# EHCache 使用方法

> MAVEN 依赖 jars

```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-support</artifactId>
    <version>4.2.5.RELEASE</version>
</dependency>
<dependency>
	<groupId>net.sf.ehcache</groupId>
	<artifactId>ehcache-core</artifactId>
	<version>2.6.11</version>
</dependency>
```


> 配置 参考 app-ehcache.xml ehcache.xml

> 使用例如： @Cacheable(value = "testEhCache", key = "#schoolId")

