package com.my.netty.order;

import com.my.netty.common.OperationResult;
import lombok.Data;

@Data
public class OrderOperationResult extends OperationResult {

    final int tableId;

    final String dish;

    final boolean complete;

}
