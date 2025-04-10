package com.amazingcode.in.example.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.amazingcode.in.example.entity.Employee;
import com.amazingcode.in.example.entity.QEmployee;
import com.amazingcode.in.example.repository.EmployeeRepository;
import com.amazingcode.in.example.service.EmployeeService;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    
    @PersistenceContext
    private EntityManager entityManager;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    // Query DSL CRUD

    @Override
    public List<Employee> findEmployeesByDepartment(String department) {
        QEmployee qEmployee = QEmployee.employee;
        Predicate predicate = qEmployee.department.eq(department);
        return (List<Employee>) employeeRepository.findAll(predicate);
    }

    @Override
    public List<Employee> findEmployeesByFirstNameContaining(String namePattern) {
        QEmployee qEmployee = QEmployee.employee;
        Predicate predicate = qEmployee.firstName.containsIgnoreCase(namePattern);
        return (List<Employee>) employeeRepository.findAll(predicate);
    }

    @Override
    public List<Employee> findEmployeesWithComplexQuery(String namePattern, String department) {
        QEmployee qEmployee = QEmployee.employee;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        
        return queryFactory.selectFrom(qEmployee)
                .where(qEmployee.firstName.containsIgnoreCase(namePattern)
                        .and(qEmployee.department.eq(department)))
                .orderBy(qEmployee.lastName.asc())
                .fetch();
    }

    @Override
    public void updateEmployeeDepartment(Long id, String newDepartment) {
        QEmployee qEmployee = QEmployee.employee;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        
        queryFactory.update(qEmployee)
                .set(qEmployee.department, newDepartment)
                .where(qEmployee.id.eq(id))
                .execute();
    }

    @Override
    public void deleteEmployeesByDepartment(String department) {
        QEmployee qEmployee = QEmployee.employee;
        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
        
        queryFactory.delete(qEmployee)
                .where(qEmployee.department.eq(department))
                .execute();
    }
}
