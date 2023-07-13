package com.my;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.CRC32;

public class CRC32Util {

    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.add("3QHB02542500C51");
        list.add("3KSCA55023PYS85");
        list.add("3YSB04610400J02");
        list.add("51010420021322003001");
        list.add("43000000031324002001");
        list.add("43000000031324002002");
        list.add("43000000031324002003");
        list.add("3CGCP2536100003");
        list.add("3ZXCG351640001D");
        list.add("3ZNB0352640001Z");
        list.add("3KSCA55502007AC");
        list.add("3TPCG465020004A");
        list.add("3KSCA55502007AT");

        for(String deviceId : list){
            System.out.println(index(deviceId));
        }
    }

    public static long index(String str){
        CRC32 crc32 = new CRC32();
        crc32.update(str.getBytes(StandardCharsets.UTF_8));

        long crc32Value = crc32.getValue();
        return crc32Value%100;
    }

}
