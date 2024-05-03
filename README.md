# spring-logback-seq
spring boot 3 使用logback 写入seq

``` java
logger.info("Hello {}","World");
logger.info("Hello world={}","World");
logger.info("log config={}",Map.of("k1","v1"));
logger.info("log config={}",Map.of("key",new String[]{"value1","value2"}));
logger.info("log config={}",Map.of("key",Map.of("k1","v1")));
```
如果配置正确的话需要可以看到日志
![image](https://github.com/lic0914/spring-logback-seq/assets/32569971/770733a7-a1b9-4fd8-a320-e85b37e70caf)

