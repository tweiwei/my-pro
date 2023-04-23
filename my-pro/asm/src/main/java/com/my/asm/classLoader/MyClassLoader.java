package com.my.asm.classLoader;

public class MyClassLoader extends ClassLoader{

    public final Class<?> defineClass(String name, byte[] b)
            throws ClassFormatError
    {
        return super.defineClass(name, b, 0, b.length, null);
    }
}
