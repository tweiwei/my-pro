package com.my.algorithm;

public class Test {

    public static void main(String[] args){
        int mqAllSize = 16;
        int cidAllSize = 17;
        System.out.println("selected: ");
        for(int index=0; index<cidAllSize; index++) {
            for (int i = index; i < mqAllSize; ++i) {
                if (i % cidAllSize == index) {
                    System.out.print(" " + i);
                }
            }
            System.out.println("");
        }
    }

}
