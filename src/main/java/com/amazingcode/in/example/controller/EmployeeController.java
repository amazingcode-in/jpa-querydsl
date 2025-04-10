package com.amazingcode.in.example.controller;

import com.amazingcode.in.example.entity.Employee;
import com.amazingcode.in.example.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    // Standard CRUD operations
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }
    
    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.findAllEmployees();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return employeeService.findEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
        return employeeService.findEmployeeById(id)
                .map(existingEmployee -> {
                    employee.setId(id);
                    return ResponseEntity.ok(employeeService.saveEmployee(employee));
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeService.findEmployeeById(id).isPresent()) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // QueryDSL specific operations
    
    @GetMapping("/department/{department}")
    public List<Employee> getEmployeesByDepartment(@PathVariable String department) {
        return employeeService.findEmployeesByDepartment(department);
    }
    
    @GetMapping("/search")
    public List<Employee> searchEmployeesByName(@RequestParam String name) {
        return employeeService.findEmployeesByFirstNameContaining(name);
    }
    
    @GetMapping("/search/complex")
    public List<Employee> searchEmployeesWithComplexCriteria(
            @RequestParam String name, 
            @RequestParam String department) {
        return employeeService.findEmployeesWithComplexQuery(name, department);
    }
    
    @PatchMapping("/{id}/department")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmployeeDepartment(@PathVariable Long id, @RequestParam String department) {
        employeeService.updateEmployeeDepartment(id, department);
    }
    
    @DeleteMapping("/department/{department}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployeesByDepartment(@PathVariable String department) {
        employeeService.deleteEmployeesByDepartment(department);
    }
}