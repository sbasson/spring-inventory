package com.example.demo.services;

import com.example.demo.persistance.entity.Country;
import com.example.demo.persistance.entity.Product;
import com.example.demo.persistance.entity.ProductCategory;
import com.example.demo.persistance.repository.CountryRepository;
import com.example.demo.persistance.repository.ProductCategoryRepository;
import com.example.demo.persistance.repository.ProductRepository;
import com.example.demo.utility.CountryProducts;
import com.example.demo.utility.ProductInput;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);


    private final ProductRepository productRepository;
    private final ProductCategoryRepository productCategoryRepository;
    private final CountryRepository countryRepository;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product deleteProduct(BigInteger id) {
        Product deleteProduct = new Product();

        Optional<Product> findProduct = productRepository.findById(id);

        if (findProduct.isPresent()) {
            deleteProduct = findProduct.get();
            productRepository.deleteById(id);
        }

        crudLog(deleteProduct,"deleted");

        return deleteProduct;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product createProduct(ProductInput input) {

        Product newProduct = buildFromInput(input);

        Optional<ProductCategory> productCategory = productCategoryRepository.findById(input.productCategoryId());

        if (productCategory.isEmpty()) {
            return newProduct;
        }

        newProduct.setProductCategory(productCategory.get());

        newProduct = productRepository.save(newProduct);

        crudLog(newProduct,"created");

        return newProduct;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Product updateProduct(ProductInput input) {

        Product updateProduct = buildFromInput(input);

        Optional<ProductCategory> productCategory;

        if (input.productCategoryId()!=null) {
            productCategory = productCategoryRepository.findById(input.productCategoryId());

            if (productCategory.isEmpty())
                return updateProduct;
            else
                updateProduct.setProductCategory(productCategory.get());
        }

        updateProduct = productRepository.save(updateProduct);

        crudLog(updateProduct,"updated");

        return updateProduct;
    }

    public List<Product> getProductsByCategory(BigInteger id) {

        List<Product> products = productRepository.findProductsByProductCategory_CategoryId(id);

        return products;
    }

    public List<CountryProducts> getCountriesWithTopFiveSellingProduct() {

        List<Country> allCountries = countryRepository.findAll();

        List<Product> products;
        List<CountryProducts> countryProducts = new ArrayList<>(allCountries.size());

        for (Country country : allCountries) {

            products = productRepository.topSellingProductsByCountry(country.getCountryId(), PageRequest.of(0, 5));

            countryProducts.add(new CountryProducts(country,products));
        }

        return countryProducts;
    }

    private Product buildFromInput(ProductInput input) {
        return new Product(input.productId(), input.productName(), input.description(),
                input.standardCost(), input.listPrice());
    }

    public Product buildProduct(BigInteger productId) {
        return new Product(productId);
    }

    private void crudLog(Product product, String operation) {

        log.info("Product " + operation + " '{" +product.toString() + "}'");
    }
}
