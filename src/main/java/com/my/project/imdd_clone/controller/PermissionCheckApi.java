package com.my.project.imdd_clone.controller;

import com.my.project.imdd_clone.controller.response.APIResponse;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/permission")
public class PermissionCheckApi {

    @GetMapping("/admin")
    @RolesAllowed("ADMIN")
    public APIResponse<String> adminCheckRoles() {
        return APIResponse.<String>builder()
                .data("security admin page")
                .message("get access to admin page")
                .build();
    }

    @GetMapping("/user")
    @RolesAllowed("USER")
    public APIResponse<String> userCheckRoles() {
        return APIResponse.<String>builder()
                .data("security user page")
                .message("get access to user page")
                .build();
    }
}
