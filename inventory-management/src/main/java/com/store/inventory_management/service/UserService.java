package com.store.inventory_management.service;

import com.store.inventory_management.dto.UserDTO;
import com.store.inventory_management.exception.ResourceNotFoundException;
import com.store.inventory_management.exception.UnauthorizedException;
import com.store.inventory_management.model.User;
import com.store.inventory_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO registerCashier(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail()) || userRepository.existsByCashierUsername(userDTO.getCashierUsername())) {
            throw new IllegalArgumentException("Email or username already exists");
        }
        User user = mapToEntity(userDTO);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole("ROLE_CASHIER");
        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    public List<UserDTO> getAllCashiers() {
        checkAdminAuthorization();
        return userRepository.findByRole("ROLE_CASHIER")
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public UserDTO updateCashier(String cashierCode, UserDTO userDTO) {
        checkAdminAuthorization();
        User user = userRepository.findByCashierCode(cashierCode)
                .orElseThrow(() -> new ResourceNotFoundException("User", "cashierCode", cashierCode));
        user.setCashierUsername(userDTO.getCashierUsername());
        user.setContact(userDTO.getContact());
        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    public void deleteCashier(String cashierCode) {
        checkAdminAuthorization();
        User user = userRepository.findByCashierCode(cashierCode)
                .orElseThrow(() -> new ResourceNotFoundException("User", "cashierCode", cashierCode));
        userRepository.delete(user);
    }

    private void checkAdminAuthorization() {
        String currentUserRole = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();
        if (!currentUserRole.contains("ROLE_ADMIN")) {
            throw new UnauthorizedException("Accessing user management", "ROLE_ADMIN");
        }
    }

    private UserDTO mapToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        dto.setContact(user.getContact());
        dto.setCashierUsername(user.getCashierUsername());
        dto.setRole(user.getRole());
        dto.setCashierCode(user.getCashierCode());
        return dto;
    }

    private User mapToEntity(UserDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setContact(dto.getContact());
        user.setCashierUsername(dto.getCashierUsername());
        user.setPassword(dto.getPassword());
        user.setRole(dto.getRole());
        user.setCashierCode(dto.getCashierCode());
        return user;
    }
}