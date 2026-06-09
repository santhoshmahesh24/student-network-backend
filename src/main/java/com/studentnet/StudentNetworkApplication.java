package com.studentnet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudentNetworkApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentNetworkApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("  Student Network Platform is RUNNING");
        System.out.println("  API Base: http://localhost:8080/api");
        System.out.println("  H2 Console: http://localhost:8080/h2-console");
        System.out.println("========================================\n");
    }
}
