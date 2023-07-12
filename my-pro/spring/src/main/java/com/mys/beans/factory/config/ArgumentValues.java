package com.mys.beans.factory.config;

import java.util.*;

public class ArgumentValues {
    private final Map<Integer, ArgumentValue> indexArgumentValues = new HashMap<>();
    private final List<ArgumentValue> genericArgumentValues = new LinkedList<>();

    public ArgumentValues(){
    }

    private void addArgumentValue(Integer key, ArgumentValue newValue){
        this.indexArgumentValues.put(key, newValue);
    }

    public boolean hasIndexedArgumentValue(int index){
        return this.indexArgumentValues.containsKey(index);
    }

    public ArgumentValue getIndexedArgumentValue(int index){
        return this.indexArgumentValues.get(index);
    }

    public void addGenericArgumentValue(Object value, String type){
        this.genericArgumentValues.add(new ArgumentValue(value, type));
    }

    private void addGenericArgumentValue(ArgumentValue newValue){
        if(newValue.getName() != null){
            for(Iterator<ArgumentValue> it = this.genericArgumentValues.iterator(); it.hasNext();){
                ArgumentValue currentValue = it.next();
                if(newValue.getName().equals(currentValue.getName())){
                    it.remove();
                }
            }
        }

        this.genericArgumentValues.add(newValue);
    }

    public ArgumentValue getGenericArgumentValue(String requireName){
        for(ArgumentValue valueHolder : this.genericArgumentValues){
            if(valueHolder.getName() != null && (requireName == null || !valueHolder.getName().equals(requireName))){
                continue;
            }

            return valueHolder;
        }

        return null;
    }

    public int getArgumentCount(){
        return this.genericArgumentValues.size();
    }

    public boolean isEmpty(){
        return this.genericArgumentValues.isEmpty();
    }
}
