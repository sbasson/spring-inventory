package com.example.demo.utility;

import com.example.demo.persistance.entity.Country;
import com.example.demo.persistance.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CountryProducts {

    private Country country;
    private List<Product> products;


}
