package com.example.demo.utility;

import com.example.demo.persistance.entity.Country;
import com.example.demo.persistance.entity.Product;

import java.util.List;

public record CountryProducts(Country country,
                              List<Product> products) {}
