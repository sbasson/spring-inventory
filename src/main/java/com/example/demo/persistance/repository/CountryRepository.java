package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country,String> {
}
