package com.example.pagination;

import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SnapshotCacheService {

    // In-memory cache: snapshotId -> List of Employees
    private final Map<String, List<Employee>> cache = new ConcurrentHashMap<>();

    // Store data and return unique snapshot ID
    public String createSnapshot(List<Employee> employees) {
        String snapshotId = UUID.randomUUID().toString();
        cache.put(snapshotId, employees);
        System.out.println("✅ Created snapshot: " + snapshotId);
        return snapshotId;
    }

    // Retrieve data by snapshot ID
    public List<Employee> getSnapshot(String snapshotId) {
        System.out.println("📦 Reading from cache (NO DB CALL): " + snapshotId);
        return cache.get(snapshotId);
    }

    // Clear snapshot
    public void clearSnapshot(String snapshotId) {
        cache.remove(snapshotId);
        System.out.println("🗑️ Cleared snapshot: " + snapshotId);
    }
}