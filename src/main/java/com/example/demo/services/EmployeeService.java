package com.example.demo.services;

import com.example.demo.persistance.entity.Employee;
import com.example.demo.persistance.entity.Order;
import com.example.demo.persistance.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<Employee> getSalesmanWithPendingOrders() {

        /*option one - filter in the app, more code, less readable, more runtime

        List<Employee> all = employeeRepository.findAll();

        Map<Employee, List<Order>> salesToOrderMap = all.stream()
                .flatMap(employee -> employee.getOrders().stream())
                .filter(order -> order.getStatus().equals("Pending"))
                .collect(Collectors.groupingBy(Order::getSalesMan));

        Set<Employee> pendingSalesMan = salesToOrderMap.keySet();

        pendingSalesMan.forEach(employee -> employee.setOrders(salesToOrderMap.get(employee)));

        return pendingSalesMan;*/

        //option two - filter in the jpa via query, less code, more readable, less runtime
        List<Employee> pendingSalesMan = employeeRepository.salesMansOfPendingOrders();

        return pendingSalesMan;
    }
}

