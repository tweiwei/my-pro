package com.my.mq.consumer;

import java.util.ArrayList;
import java.util.List;

public class ConsumerTest {
    List<String> allocates = new ArrayList<>();
    List<String> instanceNames = new ArrayList<>();

    public List<String> getAllocates() {
        return allocates;
    }

    public void setAllocates(List<String> allocates) {
        this.allocates = allocates;
    }

    public List<String> getInstanceNames() {
        return instanceNames;
    }

    public void setInstanceNames(List<String> instanceNames) {
        this.instanceNames = instanceNames;
    }
}
