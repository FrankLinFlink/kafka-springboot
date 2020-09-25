package com.franklin.springbootkafka.kafkaspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;

/**
 * @author fanli
 * 程序启动入口
 */
@EnableCaching
@SpringBootApplication
@PropertySource(value = "classpath:/application.properties")
public class Application {

  public static void main(String[] args) {
    System.setProperty("app.name","pcopsm");
    System.setProperty("app.env","dev");
    SpringApplication.run(Application.class,args);
  }

}
