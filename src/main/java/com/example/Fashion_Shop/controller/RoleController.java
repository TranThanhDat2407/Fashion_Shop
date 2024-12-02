package com.example.Fashion_Shop.controller;

import com.example.Fashion_Shop.model.Role;
import com.example.Fashion_Shop.repository.RoleRepository;
import com.example.Fashion_Shop.response.role.RoleResponse;
import com.example.Fashion_Shop.service.role.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/roles")
@AllArgsConstructor
public class RoleController {
    private final RoleService roleService;
    @GetMapping
    public ResponseEntity<List<RoleResponse>> getAllRoles() {

        List<Role> roles = roleService.getAllRoles();

        // Chuyển đổi danh sách role sang danh sách RoleResponse
        List<RoleResponse> roleResponses = roles.stream()
                .map(role -> new RoleResponse().fromRole(role))
                .collect(Collectors.toList());

        // Trả về ResponseEntity với danh sách RoleResponse
        return ResponseEntity.ok(roleResponses);
    }
}
