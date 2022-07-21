package com.webkop.coffeshopweb.services.users;

import com.webkop.coffeshopweb.models.Role;
import com.webkop.coffeshopweb.models.User;

import java.util.List;

public interface UserService {
    User saveUser(User user);
    Role saveRole(Role role);
    User addRoleToUser(String username, String rolename);
    User getUserByUsername(String username);
    User getUserById(Long id);
    List<User> getUsers();
}
