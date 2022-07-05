package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigInteger;

public interface EmployeeRepository extends JpaRepository<Employee, BigInteger>{


}
