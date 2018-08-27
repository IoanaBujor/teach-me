package com.assist.internship.service;

import com.assist.internship.model.Category;
import com.assist.internship.model.Role;
import com.assist.internship.model.User;
import com.assist.internship.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;


   @Override
    public Category findByCategoryName(String name) {
    return categoryRepository.findByName(name);
    }

    @Override
    public Category findByCategoryId(int id) {
        return categoryRepository.findById(id);
    }


    @Override
    public void save(Category category){

        categoryRepository.save(category);
    }

//    @Override
//    public void save(Category category) {
//        user.setPassword(user.getPassword());
//        Role userRole = roleRepository.findByName("ADMIN");
//        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
//        userRepository.save(user);
//    }

    @Override
    public List<Category> findAll() {
       return categoryRepository.findAll();
    }
}
