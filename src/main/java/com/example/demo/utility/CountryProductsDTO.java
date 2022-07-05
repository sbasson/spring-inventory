package com.example.demo.utility;

import com.example.demo.persistance.entity.Country;
import com.example.demo.persistance.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CountryProductsDTO {

    Country country;
    List<Product> products;
}
