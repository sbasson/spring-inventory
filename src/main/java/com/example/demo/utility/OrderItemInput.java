package com.example.demo.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemInput {

    private BigInteger orderId;
    private BigInteger itemId;
    private BigInteger productId;
    private int quantity;
    private int unitPrice;
}
