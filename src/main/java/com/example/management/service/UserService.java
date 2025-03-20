package com.example.management.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.management.dto.UserDTO;
import com.example.management.entity.Employee;
import com.example.management.entity.Users;
import com.example.management.exception.UserAlreadyExistsException;
import com.example.management.mapper.UserMapper;
import com.example.management.repository.EmployeeRepository;
import com.example.management.repository.UserRepository;
import com.example.management.util.Role;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final UserMapper USER_MAPPER = UserMapper.INSTANCE;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;

    public List<Users> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UserDTO addUser(@Valid UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() + " already exists");
        }
        Users user = new Users();
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setUsername(userDTO.getUserName());
        user.setRole(Role.USER);
        userRepository.save(user);
        Employee employee = new Employee();
        employee.setName(userDTO.getUserName());
        employee.setEmail(userDTO.getEmail());
        employee.setHireDate(LocalDate.now());
        employee.setUser(user);
        employeeRepository.save(employee);
        return USER_MAPPER.toDto(user);
    }

    public Users getUserDetailAfterLogin(Authentication authentication) {
        List<Users> users = userRepository.findByEmail(authentication.getName());
        if (users.size() == 0) {
            throw new UsernameNotFoundException("User details not found" + authentication.getName());
        }
        return users.get(0);
    }

    // @Override
    // public UserDetails loadUserByUsername(String username) throws
    // UsernameNotFoundException {
    // String email, password = null;
    // List<GrantedAuthority> authorities = null;
    // List<Users> user = userRepository.findByEmail(username);
    // if (user.size() == 0) {
    // throw new UsernameNotFoundException("User details not found" + username);
    // } else {
    // email = user.get(0).getEmail();
    // password = user.get(0).getPassword();
    // authorities = new ArrayList<>();
    // authorities.add(new SimpleGrantedAuthority(user.get(0).getRole().name()));

    // }
    // return new User(username, password, authorities);
    // }

}
