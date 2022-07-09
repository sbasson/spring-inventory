package com.example.demo.utility;

import java.math.BigInteger;

public record OrderItemInput(BigInteger orderId,
                             BigInteger itemId,
                             BigInteger productId,
                             int quantity,
                             int unitPrice) {
}
