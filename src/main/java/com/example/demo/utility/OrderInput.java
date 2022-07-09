package com.example.demo.utility;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

public record OrderInput(BigInteger orderId,
                         String status,
                         LocalDate orderDate,
                         BigInteger customerId,
                         BigInteger salesManId,
                         List<OrderItemInput> orderItems) { }
