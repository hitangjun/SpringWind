

# EHCache 使用方法

> MAVEN 依赖 jars

```
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context-support</artifactId>
    <version>4.2.5.RELEASE</version>
</dependency>
<dependency>
	<groupId>com.google.guava</groupId>
	<artifactId>guava</artifactId>
	<version>19.0</version>
</dependency>
```

> 使用例如： @Cacheable(value = "testCache", key = "#schoolId")

