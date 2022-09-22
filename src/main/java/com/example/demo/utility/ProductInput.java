package com.example.demo.utility;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductInput {

    private BigInteger productId;
    private String productName;
    private String description;
    private Integer standardCost;
    private Integer listPrice;
    private BigInteger productCategoryId;
}
