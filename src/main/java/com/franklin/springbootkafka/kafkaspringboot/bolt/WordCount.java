package com.franklin.springbootkafka.kafkaspringboot.bolt;

import org.apache.storm.topology.BasicOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fanli
 */
public class WordCount extends BaseBasicBolt {

  Map<String,Integer> counts = new HashMap<>();


  @Override
  public void execute(Tuple tuple, BasicOutputCollector basicOutputCollector) {
    String word = tuple.getString(0);
    Integer count = counts.get(word);
    if (count == null) {
      count = 0;
    }
    count++;
    counts.put(word,count);
    basicOutputCollector.emit(new Values(word,count));
  }
  @Override
  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

  }


}
