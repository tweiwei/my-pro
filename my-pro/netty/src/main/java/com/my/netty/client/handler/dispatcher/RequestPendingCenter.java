package com.my.netty.client.handler.dispatcher;

import com.my.netty.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestPendingCenter {

    Map<Long, OperationResultFuture> map = new ConcurrentHashMap<>();

    public void add(long streamId, OperationResultFuture future){
        map.put(streamId, future);
    }

    public void set(long streamId, OperationResult result){
        OperationResultFuture future = map.get(streamId);
        if(null == future)
            return;

        future.setSuccess(result);
    }

}
