package com.itrosys.student_management_system.service;

import com.itrosys.student_management_system.entity.Role;
import com.itrosys.student_management_system.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleService {

    private final RoleRepository roleRepo;

    public RoleService(RoleRepository roleRepo) {
        this.roleRepo = roleRepo;
    }

    @Transactional
    public void createRoleIfNotExist(String roleName) {
        if (roleRepo.findByName(roleName) == null) {
            roleRepo.save(new Role(null, roleName)); // ID is auto-generated
        }
    }
}
