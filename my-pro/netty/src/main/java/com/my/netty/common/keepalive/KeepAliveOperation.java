package com.my.netty.common.keepalive;

import com.my.netty.common.Operation;
import com.my.netty.common.OperationResult;
import lombok.Data;

@Data
public class KeepAliveOperation extends Operation {

    private long time;

    public KeepAliveOperation(){
        this.time = System.nanoTime();
    }

    @Override
    public OperationResult execute() {
        return new KeepAliveOperationResult(this.time);
    }
}
