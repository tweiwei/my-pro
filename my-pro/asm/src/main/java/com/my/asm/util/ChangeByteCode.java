package com.my.asm.util;

import com.my.asm.classLoader.MyClassLoader;
import com.my.asm.visitor.ChangeVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ChangeByteCode {

    public static void main(String[] args){
        redefineClass();
    }

    private static void redefineClass(){

        String className = "com.my.asm.entity.HelloWord";

        try {
            InputStream inputStream = new FileInputStream("F:\\ideaworkspace\\ideaworkspace-my\\my-pro\\my-pro\\asm\\src\\main\\java\\com\\my\\asm\\entity\\HelloWord.class");

            ClassReader reader = new ClassReader(inputStream);
            ClassWriter writer = new ClassWriter(reader, ClassWriter.COMPUTE_MAXS);
            ClassVisitor change = new ChangeVisitor(writer);
            reader.accept(change, ClassReader.EXPAND_FRAMES);

            Class clazz = new MyClassLoader().defineClass(className, writer.toByteArray());
            Object obj = clazz.newInstance();
            Method sayMethod = clazz.getDeclaredMethod("sayHello", null);
            sayMethod.invoke(obj, null);

            System.out.println("Success！");

            byte[] code = writer.toByteArray();
            try{
                FileOutputStream fous = new FileOutputStream("F:\\ideaworkspace\\ideaworkspace-my\\my-pro\\my-pro\\asm\\src\\main\\java\\com\\my\\asm\\entity\\HelloWord2.class");
                fous.write(code);
                fous.close();
            }catch (Exception e){
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


    }

}
