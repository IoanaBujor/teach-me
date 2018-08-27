package com.assist.internship.service;

import com.assist.internship.model.Role;
import com.assist.internship.model.User;

import java.util.List;

public interface UserService {

    public User findUserByEmail(String email);
    public User findUserById(long id);
    public List<User> findAll();
    public void saveUser(User user);
    public void saveAdmin(User user);
    public User findUserByResetToken(String token);
    public void deleteUserByEmail(String email);
   // public Role findById(int role_id);
    boolean isAdmin1(String token);
    boolean tokenIsValid(String token);
   // public List getAuthority();
}