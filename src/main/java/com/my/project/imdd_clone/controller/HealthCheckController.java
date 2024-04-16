package com.my.project.imdd_clone.controller;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/healthCheck")
public class HealthCheckController {

    @GetMapping
    public String healthCheck() {
        return "app is running...";
    }

    @GetMapping("/permission/admin")
    @RolesAllowed("ADMIN")
    public String adminCheckRoles() {
        return "security admin page";
    }

    @GetMapping("/permission/user")
    @RolesAllowed("USER")
    public String userCheckRoles() {
        return "security user page";
    }
}
