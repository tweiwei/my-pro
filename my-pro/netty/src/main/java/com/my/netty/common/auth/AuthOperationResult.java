package com.my.netty.common.auth;

import com.my.netty.common.OperationResult;
import lombok.Data;

@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;

    public AuthOperationResult(boolean passAuth){
        this.passAuth = passAuth;
    }

}
