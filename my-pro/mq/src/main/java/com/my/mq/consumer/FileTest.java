package com.my.mq.consumer;


import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class FileTest {

    public static void main(String[] args) throws IOException {

        Map<String, ConsumerTest> map = loadAllocate(loadSubscribe());
        map.entrySet().stream().forEach(entry -> {
            String key = entry.getKey();
            ConsumerTest value = entry.getValue();

            System.out.println(key);
            System.out.println("      "+value.getAllocates());
            System.out.println("      "+value.getInstanceNames());
        });

    }

    public static Map<String, String> loadSubscribe() throws IOException {
        Map<String, String> map = new HashMap<>();

        Files.readAllLines(Paths.get("C:\\Users\\49242\\Downloads\\subscribe.log")).stream().forEach(line->{
            if(!line.contains("op<subscribe>")){
                return;
            }

            String instanceName = StringUtils.substringBetween(line, "instanceName<",">");
            String namesrv = StringUtils.substringBetween(line, "namesrvAddr<",">");
            String topic = StringUtils.substringBetween(line, "totopic<",">");
            String consumerGroup = StringUtils.substringBetween(line, "consumerGroup<",">");

            String value = namesrv+","+topic+","+consumerGroup;
            map.put(instanceName, value);
        });

        return map;
    }


    public static Map<String, ConsumerTest> loadAllocate(Map<String, String> subscribeMap) throws IOException {
        Map<String, ConsumerTest> map = new TreeMap<>();
        Files.readAllLines(Paths.get("C:\\Users\\49242\\Downloads\\allocate.log")).stream().forEach(line->{
            String consumerGroup = StringUtils.substringBetween(line, "consumerGroup<",">");
            String instanceName = StringUtils.substringBetween(line, "currentCID<10.235.59.0@",">");
            String startIndex = StringUtils.substringBetween(line, "startIndex<",">");
            String range = StringUtils.substringBetween(line, "range<",">");

            String subscribeLog = subscribeMap.get(instanceName);

            if(map.containsKey(subscribeLog)){
                ConsumerTest value = map.get(subscribeLog);
                if(!value.getAllocates().contains(startIndex+","+range))
                    value.getAllocates().add(startIndex+","+range);
                if(!value.getInstanceNames().contains(instanceName))
                    value.getInstanceNames().add(instanceName);
            }else{
                ConsumerTest value = new ConsumerTest();
                value.getAllocates().add(startIndex+","+range);
                value.getInstanceNames().add(instanceName);
                map.put(subscribeLog, value);
            }

        });

        return map;
    }


}
