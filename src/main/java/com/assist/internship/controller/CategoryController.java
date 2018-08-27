package com.assist.internship.controller;

import com.assist.internship.helpers.InternshipResponse;
import com.assist.internship.helpers.ResponseObject;
import com.assist.internship.helpers.RoleType;
import com.assist.internship.model.Category;
import com.assist.internship.model.Role;
import com.assist.internship.model.User;
import com.assist.internship.repository.RoleRepository;
import com.assist.internship.service.CategoryService;
import com.assist.internship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    //GET   /categories - get all the course categories:OK

    @RequestMapping(value = "/category", method = RequestMethod.PUT)
    public ResponseEntity update(@RequestBody Category category, @RequestHeader("reset_token") final String token)
    {

        if( userService.tokenIsValid(token))
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied!", null));
        }
        else
        {
            Category newCategory = categoryService.findByCategoryId(category.getId());
            if(newCategory != null)
            {
                newCategory.setName(category.getName());
                categoryService.save(newCategory);
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(newCategory)));
            }
            else
            {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "The provided id doesn't belong to any existing category.", null));
            }
        }
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ResponseEntity getCategories(@RequestHeader("reset_token") final String token)
    {
        if( userService.tokenIsValid(token))
        {
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied", null));
        }
        else
        {
            List<Category> categories = categoryService.findAll();
            List<ResponseObject> lista =  new ArrayList<ResponseObject>();
            lista.addAll(categories);
            return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Found!", lista));
        }
    }

        //  POST  /create/category - create a new course category - data sent in the request body
        @RequestMapping(value="create/category", method=RequestMethod.POST)
        public ResponseEntity createCategory(@RequestBody Category category, @RequestHeader("reset_token") final String token)
        {
            if (userService.isAdmin1(token) && userService.tokenIsValid(token)) {
                Category dbCategory = categoryService.findByCategoryName(category.getName());
                if (dbCategory == null) {
                    categoryService.save(category);
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(true, "Success", Arrays.asList(category)));
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Category  exist please create another category", null));
                }
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new InternshipResponse(false, "Access Denied", null));
            }

        }
}
