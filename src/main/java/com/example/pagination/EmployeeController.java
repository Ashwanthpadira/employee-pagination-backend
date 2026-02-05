package com.example.pagination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@CrossOrigin(origins = "http://localhost:4200") // For Angular
public class EmployeeController {

    @Autowired
    private EmployeeDataService dataService;

    @Autowired
    private SnapshotCacheService cacheService;

    @GetMapping("/page")
    public PageResponse<Employee> getEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String snapshotId) {

        List<Employee> allEmployees;

        // If no snapshotId, fetch from "DB" and create snapshot
        if (snapshotId == null || snapshotId.isEmpty()) {
            System.out.println("🔴 NEW REQUEST - Fetching from DB");
            allEmployees = dataService.getAllEmployees(); // DB call happens ONCE
            snapshotId = cacheService.createSnapshot(allEmployees);
        } else {
            // Use cached data (NO DB CALL)
            System.out.println("🟢 PAGE CHANGE - Using cached data");
            allEmployees = cacheService.getSnapshot(snapshotId);
            
            if (allEmployees == null) {
                return new PageResponse<>(List.of(), 0, size, 0, 0, "");
            }
        }

        // Calculate pagination
        int totalElements = allEmployees.size();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        int fromIndex = Math.min(page * size, totalElements);
        int toIndex = Math.min(fromIndex + size, totalElements);

        List<Employee> pageContent = allEmployees.subList(fromIndex, toIndex);

        return new PageResponse<>(pageContent, page, size, totalElements, totalPages, snapshotId);
    }
}