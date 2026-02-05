package com.example.pagination;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeDataService {

    private static final List<Employee> EMPLOYEES = new ArrayList<>();

    // Static block to generate 100 employees once
    static {
        String[] departments = {"IT", "HR", "Finance", "Marketing", "Sales"};
        
        for (int i = 1; i <= 100; i++) {
            EMPLOYEES.add(new Employee(
                (long) i,
                "Employee " + i,
                departments[i % 5],
                "employee" + i + "@company.com",
                30000.0 + (i * 500)
            ));
        }
    }

    // Get all employees (this simulates DB call - called only ONCE)
    public List<Employee> getAllEmployees() {
        System.out.println("📊 Fetching all 100 employees from 'database'...");
        return new ArrayList<>(EMPLOYEES);
    }
}