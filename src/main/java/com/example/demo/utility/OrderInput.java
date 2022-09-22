package com.example.demo.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderInput {

    private BigInteger orderId;
    private String status;
    private LocalDate orderDate;
    private BigInteger customerId;
    private BigInteger salesManId;
    private List<OrderItemInput> orderItems;
}
