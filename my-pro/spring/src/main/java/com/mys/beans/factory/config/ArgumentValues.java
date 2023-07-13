package com.mys.beans.factory.config;

import java.util.*;

public class ArgumentValues {
    private final List<ArgumentValue> argumentValueList = new LinkedList<>();

    public ArgumentValues(){
    }

    private void addArgumentValue(ArgumentValue argumentValue){
        this.argumentValueList.add(argumentValue);
    }

    public ArgumentValue getIndexedArgumentValue(int index){
        return this.argumentValueList.get(index);
    }

    public int getArgumentCount(){
        return this.argumentValueList.size();
    }

    public boolean isEmpty(){
        return this.argumentValueList.isEmpty();
    }
}
