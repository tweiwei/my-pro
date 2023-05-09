package com.my.netty.common.order;

import com.my.netty.common.Operation;
import com.my.netty.common.OperationResult;
import lombok.Data;

@Data
public class OrderOperation extends Operation {

    int tableId;
    String dish;

    public OrderOperation(int tableId, String dish){
        this.tableId = tableId;
        this.dish = dish;
    }

    @Override
    public OperationResult execute() {

        System.out.println("order's executing startup with orderRequest: " + toString());
        //execute order logic
        System.out.println("order's executing complete");

        return new OrderOperationResult(tableId, dish, true);
    }
}
