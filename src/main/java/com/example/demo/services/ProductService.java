package com.example.demo.services;

import com.example.demo.persistance.entity.Country;
import com.example.demo.persistance.entity.Product;
import com.example.demo.persistance.entity.ProductCategory;
import com.example.demo.persistance.repository.CountryRepository;
import com.example.demo.persistance.repository.ProductCategoryRepository;
import com.example.demo.persistance.repository.ProductRepository;
import com.example.demo.utility.CountryProductsDTO;
import com.example.demo.utility.ProductInput;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CountryRepository countryRepository;

    public Product deleteProduct(BigInteger id) {
        Product deleteProduct = new Product();

        Optional<Product> findProduct = productRepository.findById(id);

        if (findProduct.isPresent()) {
            deleteProduct = findProduct.get();
            productRepository.deleteById(id);
        }

        return deleteProduct;
    }

    public Product createProduct(ProductInput input) {

        Product newProduct = fromInput(input);

        Optional<ProductCategory> productCategory = productCategoryRepository.findById(input.productCategoryId());

        if (productCategory.isEmpty()) {
            return newProduct;
        }

        newProduct.setProductCategory(productCategory.get());

        newProduct = productRepository.save(newProduct);


        return newProduct;
    }

    public Product updateProduct(ProductInput input) {

        Product updateProduct = fromInput(input);

        Optional<ProductCategory> productCategory;

        if (input.productCategoryId()!=null) {
            productCategory = productCategoryRepository.findById(input.productCategoryId());

            if (productCategory.isEmpty())
                return updateProduct;
            else
                updateProduct.setProductCategory(productCategory.get());
        }

        updateProduct = productRepository.save(updateProduct);

        return updateProduct;
    }

    public List<Product> getProductsByCategory(BigInteger id) {

        List<Product> products = productRepository.findProductsByProductCategory_CategoryId(id);

        return products;
    }

    public List<CountryProductsDTO> getCountriesWithTopFiveSellingProduct() {

        List<Country> allCountries = countryRepository.findAll();

        List<Product> products;
        List<CountryProductsDTO> countryProductsDTO = new ArrayList<>();

        for (Country country : allCountries) {

            products = productRepository.topSellingProductsByCountry(country.getCountryId(), PageRequest.of(0, 5));

            countryProductsDTO.add(new CountryProductsDTO(country,products));
        }

        return countryProductsDTO;
    }

    private Product fromInput(ProductInput input) {
        return new Product(input.productId(), input.productName(), input.description(),
                input.standardCost(), input.listPrice());
    }
}
