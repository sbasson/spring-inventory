package com.example.demo.persistance.repository;

import com.example.demo.persistance.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, BigInteger>{

    @Query( " select distinct e" +
            " from Employee e" +
            " join fetch e.orders o " +
            " where o.status = 'Pending'")
    List<Employee> salesMansOfPendingOrders();
}
