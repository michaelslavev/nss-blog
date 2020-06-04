/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.dao;

import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Topic;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;


/**
 * Data access class of Article
 */
@Repository
public class ArticleDao extends BaseDao<Article>{
    
    public ArticleDao(){
        super(Article.class);
    }

    /**
     * Query for finding all not removed articles
     * @return List of found Articles
     */
    @Override
    public List<Article> findAll() {
        return em.createQuery("SELECT a FROM Article a WHERE NOT a.removed", Article.class).getResultList();
    }

    /**
     * Query for finding articles by topic
     * @param topic
     * @return List of found Articles
     */
    public List<Article> findByTopic(Topic topic) {
        Objects.requireNonNull(topic);
        return em.createQuery("SELECT a FROM Article a WHERE :topic MEMBER OF a.topics AND NOT a.removed", Article.class).getResultList();
    }

    /**
     * Query for finding all articles created on specific date
     * @param date
     * @return List of found Articles
     */
    public List<Article> findByDate(LocalDate date) {
        Objects.requireNonNull(date);
        return em.createQuery("SELECT a FROM Article a WHERE a.created = ?1", Article.class).setParameter(1, date)
                 .getResultList();
    }

    /**
     * Query for finding all articles by title
     * @param title
     * @return List of found Articles
     */
    public List<Article> findByTitle(String title){
        Objects.requireNonNull(title);
        return em.createQuery("SELECT a FROM Article a WHERE a.title = ?1", Article.class).setParameter(1, title)
                 .getResultList();
    }
    
    
}
