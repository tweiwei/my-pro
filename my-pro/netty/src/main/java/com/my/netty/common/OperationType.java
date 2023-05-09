package com.my.netty.common;

import com.my.netty.auth.AuthOperation;
import com.my.netty.auth.AuthOperationResult;
import com.my.netty.keepalive.KeepAliveOperation;
import com.my.netty.keepalive.KeepAliveOperationResult;
import com.my.netty.order.OrderOperation;
import com.my.netty.order.OrderOperationResult;

import java.util.function.Predicate;

public enum OperationType {

    AUTH(1, AuthOperation.class, AuthOperationResult.class),

    KEEPALIVE(2, KeepAliveOperation.class, KeepAliveOperationResult.class),

    ORDER(3, OrderOperation.class, OrderOperationResult.class);

    private int opCode;

    private Class<? extends Operation> operationClazz;

    private Class<? extends OperationResult> operationResultClazz;

    OperationType(int opCode, Class<? extends Operation> operationClazz, Class<? extends OperationResult> operationResultClazz){
        this.opCode = opCode;
        this.operationClazz = operationClazz;
        this.operationResultClazz = operationResultClazz;
    }

    public int getOpCode() {
        return opCode;
    }

    public Class<? extends Operation> getOperationClazz() {
        return operationClazz;
    }

    public Class<? extends OperationResult> getOperationResultClazz() {
        return operationResultClazz;
    }

    public static OperationType fromOpCode(int type){
        return getOperationType(requestType -> requestType.opCode == type);
    }

    public static OperationType fromOperation(Operation operation){
        return getOperationType(requestType -> requestType.operationClazz == operation.getClass());
    }
    private static OperationType getOperationType(Predicate<OperationType> predicate){
        OperationType[] values = values();

        for(OperationType operationType : values){
            if(predicate.test(operationType)){
                return operationType;
            }
        }

        throw new AssertionError("not found type");
    }
}
