package com.assist.internship.repository;

import com.assist.internship.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{

    Role findByName(String role);
   // Role findById(long role_id);
}