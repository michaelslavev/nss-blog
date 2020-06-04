/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.dao;

import the.nss.boys.blog.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;


@Repository
public class UserDao extends BaseDao<User> {

    public UserDao() {
        super(User.class);
    }

    /**
     * Query for finding user by his username
     * @param username
     * @return user
     */
    public User findByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM User u WHERE u.username LIKE ?1", User.class).setParameter(1, username)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
