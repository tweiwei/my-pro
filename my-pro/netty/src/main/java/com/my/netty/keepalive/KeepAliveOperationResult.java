package com.my.netty.keepalive;

import com.my.netty.common.OperationResult;
import lombok.Data;

@Data
public class KeepAliveOperationResult extends OperationResult {

    private final long time;

}
