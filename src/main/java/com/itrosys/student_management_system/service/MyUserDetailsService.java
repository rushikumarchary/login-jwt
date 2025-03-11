package com.itrosys.student_management_system.service;

import com.itrosys.student_management_system.entity.UserPrincipal;
import com.itrosys.student_management_system.entity.Users;
import com.itrosys.student_management_system.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository){
        this.userRepository=userRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user =userRepository.findByUsername(username);
        if (user == null){
            System.out.println("user Not Found");
            throw new UsernameNotFoundException("user Not found");
        }
        return new UserPrincipal(user);
    }
}
