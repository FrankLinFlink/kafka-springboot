package com.franklin.springbootkafka.kafkaspringboot.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

/**
 * @author fanli
 */

/**
 * SplitSentence处理spout产生的语句,将橘子按照空格分割不同的单词,并发送到下面的处理单元
 */
public class SplitSentence extends BaseBasicBolt {
  /**
   *
   * @param outputFieldsDeclarer
   *  此对象声明对应的Bolt输出的消息所包含的域
   */
  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare(new Fields("word"));
  }

  /**
   *
   * @param tuple
   * @param basicOutputCollector
   * 此方法处理bolt接收到的tuple,将收到的语句进行分割,得到不同的单词,通过basicOutputCollector将单词发送到下一处理单元
   */
  @Override
  public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
    String sentence = tuple.getString(0);
    String[] words = sentence.split("");
    for (String word:words) {
      basicOutputCollector.emit(new Values(word));
    }
  }
}
