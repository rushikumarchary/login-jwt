package com.itrosys.student_management_system.repository;

import com.itrosys.student_management_system.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer> {

    Users findByUsername(String username);

    boolean findByEmail(String email);

    @Query("SELECT u FROM Users u WHERE u.username = :username OR u.email = :email")
    List<Users> findByUsernameOrEmail(@Param("username") String username, @Param("email") String email);

}
