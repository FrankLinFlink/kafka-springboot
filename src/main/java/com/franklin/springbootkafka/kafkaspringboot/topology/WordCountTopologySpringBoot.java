package com.franklin.springbootkafka.kafkaspringboot.topology;

import com.franklin.springbootkafka.kafkaspringboot.bolt.SplitSentence;
import com.franklin.springbootkafka.kafkaspringboot.bolt.WordCount;
import com.franklin.springbootkafka.kafkaspringboot.spout.RandomSentenceSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.StormSubmitter;
import org.apache.storm.generated.AlreadyAliveException;
import org.apache.storm.generated.AuthorizationException;
import org.apache.storm.generated.InvalidTopologyException;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author fanli
 */
@Component
public class WordCountTopologySpringBoot {

  @Autowired
  private Environment environment;

  public void submitTopology() throws InvalidTopologyException, AuthorizationException, AlreadyAliveException, InterruptedException {
    TopologyBuilder topologyBuilder = new TopologyBuilder();

    topologyBuilder.setSpout("spout", new RandomSentenceSpout(), 5);
    topologyBuilder.setBolt("split", new SplitSentence(), 8).shuffleGrouping("spout");
    topologyBuilder.setBolt("count", new WordCount(), 12).fieldsGrouping("split", new Fields("word"));

    Config config = new Config();
    config.setDebug(true);

//    if (args != null && args.length > 0) {
//      config.setNumWorkers(3);
//      StormSubmitter.submitTopologyWithProgressBar(args[0], config, topologyBuilder.createTopology());
//    } else {
//      config.setMaxTaskParallelism(3);
//      LocalCluster cluster = new LocalCluster();
//      cluster.submitTopology("word-count", config, topologyBuilder.createTopology());
//
//      Thread.sleep(10_000);
//
//      cluster.shutdown();
//    }
    config.setNumWorkers(1);
    config.setNumAckers(0);
    LocalCluster cluster = new LocalCluster();
    cluster.submitTopology("WordCountTopologySpringBoot",config,topologyBuilder.createTopology());

  }

}
