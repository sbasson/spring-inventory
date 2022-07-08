package com.example.demo.utility;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

public record ProductInput(BigInteger productId,
                           String productName,
                           String description,
                           Integer standardCost,
                           Integer listPrice,
                           BigInteger productCategoryId) {}
