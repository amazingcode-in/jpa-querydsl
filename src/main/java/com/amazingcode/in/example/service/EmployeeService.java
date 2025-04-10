package com.amazingcode.in.example.service;

import com.amazingcode.in.example.entity.Employee;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    // Jpa Operations
    
    public Employee saveEmployee(Employee employee);

    public List<Employee> findAllEmployees();
    
    public Optional<Employee> findEmployeeById(Long id);
    
    public void deleteEmployee(Long id);
    
    // QueryDSL Operations
    
    public List<Employee> findEmployeesByDepartment(String department);
    
    public List<Employee> findEmployeesByFirstNameContaining(String namePattern);
    
    @Transactional
    public List<Employee> findEmployeesWithComplexQuery(String namePattern, String department);
    
    @Transactional
    public void updateEmployeeDepartment(Long id, String newDepartment);
    
    @Transactional
    public void deleteEmployeesByDepartment(String department);
}
