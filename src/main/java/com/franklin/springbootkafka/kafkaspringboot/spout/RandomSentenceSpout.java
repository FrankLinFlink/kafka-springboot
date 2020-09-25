package com.franklin.springbootkafka.kafkaspringboot.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

/**
 * @author fanli
 * 编写一个产生随机发送语句的Spout,数据来自固定的语句数组
 */

/**
 *  一)第一个被调用的spout方法都是public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector)
 *  接受参数如下:
 *    ①配置对象Map,在定义topology对象时创建
 *    ②TopologyContext,包含所有拓扑数据;
 *    ③SpoutOutputCollector对象,他能让我们发布交给bolt处理的数据
 *   二)public void nextTuple()向bolt发布待处理的数据,nextTuple()会在同一个循环内被ack()和fail()周期性调用.
 *    没有任务时必须释放对线程的控制,其他方法才会有机会得以执行.
 *    因此nextTuple的第一行就要检查是否已处理完成.
 *    如果完成了,为了降低处理器负载,会在返回前休眠一毫秒.
 *    如果任务完成了,文件中的每一行都已经读出并分发了
 */
public class RandomSentenceSpout extends BaseRichSpout  {

  SpoutOutputCollector controller;
  Random random;
  //1.
  @Override
  public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
    this.controller = spoutOutputCollector;
    this.random = new Random();
  }
  //2.
  @Override
  public void nextTuple() {
    Utils.sleep(100);
    String [] sentences  = new String[]{
        "the cow jumped over the moon",
        "an apple a dayy keeps the doctor away",
        "four score and seven years age",
        "snow white and the seven dwarfs",
        "i am at two with nature"
    };

    String sentence = sentences[random.nextInt(sentences.length)];

    controller.emit(new Values(sentence));
  }



  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare(new Fields("word"));
  }

}
