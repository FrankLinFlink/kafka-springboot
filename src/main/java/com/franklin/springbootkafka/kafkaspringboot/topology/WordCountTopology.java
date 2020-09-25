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

/**
 * @author fanli
 */

/**
 *  TopologyBuilder用来创建拓扑,决定Strom各个节点如何安排,以及他们交换数据的方式.
 *  在spout和bolts之间通过shuffleGrouping方法连接.这种分组方式决定了Strom会以随机分配方式从源节点想目标节点发送消息
 *  创建Config对象:拓扑配置,它会在运行时与集群配置合并
 *
 *  对于拓扑的运行:
 *      通常可以通过StromSubmitter.submitTopologyWithProgressBar() 将拓扑部署在集群中,
 *      也可以在主类中穿件拓扑和一个本地集群对象LocalCluster,以便于在本地调试和测试,
 *      LocalCluster可以通过Config对象,使用不同的集群配置.
 */
public class WordCountTopology {

  public  void main(String[] args) throws InvalidTopologyException, AuthorizationException, AlreadyAliveException, InterruptedException {
    TopologyBuilder topologyBuilder = new TopologyBuilder();

    topologyBuilder.setSpout("spout",new RandomSentenceSpout(),5);
    topologyBuilder.setBolt("split",new SplitSentence(),8).shuffleGrouping("spout");
    topologyBuilder.setBolt("count",new WordCount(),12).fieldsGrouping("split",new Fields("word"));

    Config config = new Config();
    config.setDebug(true);

    if (args != null && args.length > 0 ) {
      config.setNumWorkers(3);
      StormSubmitter.submitTopologyWithProgressBar(args[0],config,topologyBuilder.createTopology());
    } else {
      config.setMaxTaskParallelism(3);
      LocalCluster cluster = new LocalCluster();
      cluster.submitTopology("word-count",config,topologyBuilder.createTopology());

      Thread.sleep(10_000);

      cluster.shutdown();
    }

  }

}
