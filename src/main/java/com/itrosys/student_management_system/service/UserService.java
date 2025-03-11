package com.itrosys.student_management_system.service;

import com.itrosys.student_management_system.entity.Role;
import com.itrosys.student_management_system.entity.Users;
import com.itrosys.student_management_system.exception.BadCredentials;
import com.itrosys.student_management_system.exception.EmailAlreadyExists;
import com.itrosys.student_management_system.exception.UsernameAlreadyExists;
import com.itrosys.student_management_system.exception.UsernameAndEmailAlreadyExists;
import com.itrosys.student_management_system.repository.RoleRepository;
import com.itrosys.student_management_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authManager;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, AuthenticationManager authManager, JWTService jwtService, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    //    public Users register(Users user) {
//
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        return userRepository.save(user);
//    }

    @Transactional
    public void createAdminUserIfNotExist() {
        Role adminRole = roleRepository.findByName("ADMIN");

        if (adminRole != null) {
            Users adminUser = userRepository.findByUsername("ADMIN");

            if (adminUser == null) {
                Users admin = new Users();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin@1234"));
                admin.setEmail("admin@itrosys.com");
                admin.setRole(adminRole);

                userRepository.save(admin);
            }
        } else {
            System.out.println("ADMIN role not found. Please ensure roles are created.");
        }
    }
    public Users register(Users user) {
        List<Users> existingUsers = userRepository.findByUsernameOrEmail(user.getUsername(), user.getEmail());

        if (!existingUsers.isEmpty()) {
            boolean usernameExists = existingUsers.stream().anyMatch(u -> u.getUsername().equals(user.getUsername()));
            boolean emailExists = existingUsers.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()));

            if (usernameExists && emailExists) {
                throw new UsernameAndEmailAlreadyExists("Both username and email already have different you can't Use");
            } else if (usernameExists) {
                throw new UsernameAlreadyExists("Username already have different account");
            } else {
                throw new EmailAlreadyExists("Email already have different account");
            }
        }

        Role defaultRole = user.getEmail().contains("@itrosys.com")
                ? roleRepository.findByName("EMPLOYEE")
                : roleRepository.findByName("USER");

        user.setRole(defaultRole);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }


    public String verify(Users user) {
        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal(); // Get authenticated user details
            return jwtService.generateToken(userDetails); // Pass userDetails to include role
        } catch (Exception e) {
            throw new BadCredentials("Authentication failed: " + e.getMessage());
        }
    }


//    public String verify(Users user) {
//        try {
//            Authentication authentication = authManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
//            );
//            return jwtService.generateToken(user.getUsername());
//        } catch (Exception e) {
//            throw new BadCredentials("Authentication failed: " + e.getMessage());
//
//        }
//    }

}
