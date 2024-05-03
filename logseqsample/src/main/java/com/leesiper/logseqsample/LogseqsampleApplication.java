package com.leesiper.logseqsample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class LogseqsampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(LogseqsampleApplication.class, args);

        Logger logger = LoggerFactory.getLogger(LogseqsampleApplication.class);

        logger.info("Hello {}","World");
        logger.info("Hello world={}","World");
        logger.info("log config={}",Map.of("k1","v1"));
        logger.info("log config={}",Map.of("key",new String[]{"value1","value2"}));
        logger.info("log config={}",Map.of("key",Map.of("k1","v1")));

    }

}
