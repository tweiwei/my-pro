package com.my.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class StringTest {

    public static void main(String[] args){
        String s = null;
        System.out.println(Arrays.asList(s));

        Map<String, String> map = new HashMap<>();
        map.put("1", "2");
        System.out.println(map.containsKey("1"));
    }

}
