package com.webkop.coffeshopweb.repositories;

import com.webkop.coffeshopweb.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Role findByName(String name);
}
