package com.my.asm.entity;

public class HelloWord {

    public void sayHello(){
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
