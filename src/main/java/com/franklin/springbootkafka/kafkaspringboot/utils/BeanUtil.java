package com.franklin.springbootkafka.kafkaspringboot.utils;


import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author fanli
 */
//实现接口ApplicationContextAware,可以从spring中动态获取需要的类
@Component
public class BeanUtil implements ApplicationContextAware {

  public static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    if (BeanUtil.applicationContext == null ){
      BeanUtil.applicationContext=applicationContext;
    }
  }

  public static <T> T getBean(Class<T> clazz){
    return (T) applicationContext.getBean(clazz);
  }
}
