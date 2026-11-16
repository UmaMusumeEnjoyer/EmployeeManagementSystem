package com.example.employeemanagement.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UtilityService {

    private final AtomicInteger counter = new AtomicInteger(1000);

    public String formatString(String input) {
        if (input == null) {
            return null;
        }
        return input.trim().replaceAll("\\s+", " ");
    }

    public String generateEmployeeCode() {
        return "EMP-" + counter.incrementAndGet();
    }
}
