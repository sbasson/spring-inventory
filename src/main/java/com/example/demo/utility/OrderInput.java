package com.example.demo.utility;

import com.example.demo.persistance.entity.Customer;
import com.example.demo.persistance.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class OrderInput {

    private BigInteger orderId;
    private String status;
    private LocalDate orderDate;
    private BigInteger customerId;
    private BigInteger salesManId;
}
