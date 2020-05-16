/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.service;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import the.nss.boys.blog.dao.UserDao;
import the.nss.boys.blog.model.Role;
import the.nss.boys.blog.model.User;

@Service
public class UserService {

    private final UserDao dao;
    
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDao dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void persist(User user) {
        Objects.requireNonNull(user);
        user.encodePassword(passwordEncoder);
        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }
        dao.persist(user);
    }
    
    @Transactional
    public void update(User user) {
        dao.update(user);
    }

   
    @Transactional(readOnly = true)
    public User findByUsername(String username) {
        return dao.findByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean exists(String username) {
        return dao.findByUsername(username) != null;
    }
    
 
    
}

