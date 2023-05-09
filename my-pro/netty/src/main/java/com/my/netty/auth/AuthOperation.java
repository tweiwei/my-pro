package com.my.netty.auth;

import com.my.netty.common.Operation;
import com.my.netty.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperation extends Operation {

    private final String userName;

    private final String password;

    @Override
    public OperationResult execute() {
        if("admin".equals(this.userName)){
            return new AuthOperationResult(true);
        }
        return new AuthOperationResult(false);
    }
}
