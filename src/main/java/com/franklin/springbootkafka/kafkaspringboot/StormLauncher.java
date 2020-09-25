package com.franklin.springbootkafka.kafkaspringboot;

import com.franklin.springbootkafka.kafkaspringboot.topology.WordCountTopologySpringBoot;
import com.franklin.springbootkafka.kafkaspringboot.utils.BeanUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author fanli
 */
@Component
public class StormLauncher implements ApplicationListener<ContextRefreshedEvent> {

  private static Logger logger = LoggerFactory.getLogger(StormLauncher.class);

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    WordCountTopologySpringBoot wordCountTopologySpringBoot = BeanUtil.getBean(WordCountTopologySpringBoot.class);

    try {
      wordCountTopologySpringBoot.submitTopology();
    } catch (Exception exception ) {
      logger.error("[StromLauncher]error");
    }

  }

}
