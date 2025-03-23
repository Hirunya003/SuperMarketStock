package com.store.inventory_management.controller;

import com.store.inventory_management.dto.UserDTO;
import com.store.inventory_management.service.AuthService;
import com.store.inventory_management.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    // Cashier Registration (public endpoint)
    @PostMapping("/users/register")
    public ResponseEntity<UserDTO> registerCashier(@RequestBody UserDTO userDTO) {
        UserDTO registeredUser = userService.registerCashier(userDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    // User Login (public endpoint)
    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody UserDTO userDTO) {
        String token = authService.login(userDTO.getEmail(), userDTO.getPassword());
        return ResponseEntity.ok(Map.of("token", token));
    }

    // Get All Cashiers (admin only)
    @GetMapping("/users/cashiers")
    public ResponseEntity<List<UserDTO>> getAllCashiers() {
        List<UserDTO> cashiers = userService.getAllCashiers();
        return ResponseEntity.ok(cashiers);
    }

    // Update Cashier (admin only)
    @PutMapping("/users/update/{cashierCode}")
    public ResponseEntity<UserDTO> updateCashier(@PathVariable String cashierCode, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateCashier(cashierCode, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete Cashier (admin only)
    @DeleteMapping("/users/delete/{cashierCode}")
    public ResponseEntity<Void> deleteCashier(@PathVariable String cashierCode) {
        userService.deleteCashier(cashierCode);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
