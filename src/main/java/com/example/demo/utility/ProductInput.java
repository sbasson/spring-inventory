package com.example.demo.utility;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class ProductInput {

    private BigInteger productId;
    private String productName;
    private String description;
    private Integer standardCost;
    private Integer listPrice;
    private BigInteger productCategoryId;
}
