package com.my.netty.common.keepalive;

import com.my.netty.common.OperationResult;
import lombok.Data;

@Data
public class KeepAliveOperationResult extends OperationResult {

    private final long time;

    public KeepAliveOperationResult(long time){
        this.time = time;
    }
}
