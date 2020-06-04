/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.dao;

import the.nss.boys.blog.model.Topic;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;


@Repository
public class TopicDao extends BaseDao<Topic>{
    
    public TopicDao(){
        super(Topic.class);
    }

    /**
     * Query for finding all Topics
     * @return List of found Topics
     */
    @Override
    public List<Topic> findAll() {
        return em.createQuery("SELECT t FROM Topic t", Topic.class).getResultList();
    }

    /**
     * Query for finding all topics by name
     * @param name
     * @return List of found Topics
     */
     public List<Topic> findByName(String name){
        Objects.requireNonNull(name);
        return em.createQuery("SELECT t FROM Topic t WHERE t.name LIKE ?1", Topic.class).setParameter(1, name)
                 .getResultList();
    }


    
    
}
