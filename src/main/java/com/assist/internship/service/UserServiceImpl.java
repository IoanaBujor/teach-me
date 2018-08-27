package com.assist.internship.service;

import com.assist.internship.model.Role;
import com.assist.internship.model.User;
import com.assist.internship.repository.RoleRepository;
import com.assist.internship.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class UserServiceImpl implements UserService {


    private RestTemplate restTemplate;

//    public UserServiceImpl (RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public void saveUser(User user) {
        //user.setPassword(user.getPassword());
       Role userRole = roleRepository.findByName("CLIENT");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);
    }

    @Override
    public void saveAdmin(User user) {
        //user.setPassword(user.getPassword());
        Role userRole = roleRepository.findByName("ADMIN");
        user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
        userRepository.save(user);
    }

//    @Override
//    public Role findById(int role_id){return roleRepository.findById(role_id);}

    @Override
    public User findUserById(long id)
    {
        return userRepository.findById(id);
    }
    @Override
    public List<User> findAll()
    {

        return userRepository.findAll();
    }
    @Override
    public User findUserByResetToken(String token)
    {

        return userRepository.findByResetToken(token);
    }

//    @Override
//    public User deleteUserByEmail(String email){
//        return userRepository.deleteByEmail(email);
//    }
    @Override
    public void deleteUserByEmail(String email){
        for (Iterator<User> iterator = userRepository.findAll().iterator(); iterator.hasNext(); ) {
            User user = iterator.next();
            if (user.getEmail() == email) {
                iterator.remove();
            }
        }
    }

    public boolean tokenIsValid(String token) {
        if(token==null){
            return false;
        }else{
            User user = findUserByResetToken(token);
            if (user == null)
                return false;}
        return true;
    }

    public boolean isAdmin1(String token){
        User user=findUserByResetToken(token);
        boolean control=false;
        if(user==null){
            return false;
        }
        for (Role role:user.getRoles()){
            control=Objects.equals(role.getName(),"ADMIN");
        }
        return control;
    }

//    @Override
//    public List getAuthority() {
//        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//    }
//

}
