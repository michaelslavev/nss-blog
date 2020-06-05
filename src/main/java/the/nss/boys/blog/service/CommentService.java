/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import the.nss.boys.blog.dao.CommentDao;
import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Comment;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for Comments
 *
 * Adds, Removes, Find and Update comments then persist to database via CommentDao
 */
@Service
@CacheConfig(cacheNames = "comments")
public class CommentService {
    
    private final CommentDao dao;

    public CommentService (CommentDao dao){
        this.dao = dao;
    }

    /**
     * Find comments on article
     * @param article
     * @return
     */
    @Transactional(readOnly = true)
    public List<Comment> findByArticle(Article article) {
        return dao.findByArticle(article);
    }

    /**
     * Find comment by his id
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Comment find(Integer id) {
        return dao.find(id);
    }

    /**
     * Find all comments
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable()
    public List<Comment> findAll() {
        return dao.findAll();
    }

    /**
     * Saves comment to database
     * @param comment
     */
    @Transactional
    public void persist(Comment comment) {
        Objects.requireNonNull(comment);
        dao.persist(comment);
    }

    /**
     * Updates comment
     * @param comment
     */
    @Transactional
    public void update(Comment comment) {
        dao.update(comment);
    }

    /**
     * Removes comment
     * @param comment
     */
    @Transactional
    public void remove(Comment comment) {
        Objects.requireNonNull(comment);
        //comment.setRemoved(true);
        dao.update(comment);
    }
    /*
    @Transactional(readOnly = true)
    public List<Comment> findAll(Article article) {
        return dao.findAll(article);
    }
    */
}
