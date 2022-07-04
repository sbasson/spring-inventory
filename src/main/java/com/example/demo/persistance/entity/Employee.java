package com.example.demo.persistance.entity;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYEES", schema = "OT", catalog = "")
public class Employee {
    private BigInteger employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date hireDate;
    private String jobTitle;
    private Employee manager;
    private Collection<Employee> employeesBelow;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "EMPLOYEE_ID")
    public BigInteger getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(BigInteger employeeId) {
        this.employeeId = employeeId;
    }

    @Basic
    @Column(name = "FIRST_NAME")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Basic
    @Column(name = "LAST_NAME")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "EMAIL")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "PHONE")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "HIRE_DATE")
    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    @Basic
    @Column(name = "JOB_TITLE")
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(employeeId, employee.employeeId) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(email, employee.email) && Objects.equals(phone, employee.phone) && Objects.equals(hireDate, employee.hireDate) && Objects.equals(jobTitle, employee.jobTitle);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, email, phone, hireDate, jobTitle);
    }

    @ManyToOne
    @JoinColumn(name = "MANAGER_ID", referencedColumnName = "EMPLOYEE_ID")
    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    @OneToMany(mappedBy = "manager")
    public Collection<Employee> getEmployeesBelow() {
        return employeesBelow;
    }

    public void setEmployeesBelow(Collection<Employee> employeesBelow) {
        this.employeesBelow = employeesBelow;
    }
}
