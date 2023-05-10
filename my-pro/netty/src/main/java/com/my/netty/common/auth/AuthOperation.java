package com.my.netty.common.auth;

import com.my.netty.common.Operation;
import com.my.netty.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperation extends Operation {

    private final String userName;

    private final String password;

    public AuthOperation(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    @Override
    public OperationResult execute() {
        if("admin".equals(this.userName)){
            return new AuthOperationResult(true);
        }
        return new AuthOperationResult(false);
    }
}
