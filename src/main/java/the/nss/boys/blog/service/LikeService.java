/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.service;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import the.nss.boys.blog.dao.LikeDao;
import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Like;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service for Likes
 *
 * Adds, Removes, Find and Update likes then persist to database via LikeDao
 */
@Service
@CacheConfig(cacheNames = "likes")
public class LikeService {
    
    private final LikeDao dao;
    
    public LikeService (LikeDao dao){
        this.dao = dao;
    }

    /**
     * Find likes on article
     * @param article
     * @return
     */
    @Transactional(readOnly = true)
    public List<Like> findByArticle(Article article) {
        return dao.findByArticle(article);
    }

    /**
     * Find like by id
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Like find(Integer id) {
        return dao.find(id);
    }

    /**
     * Find all likes
     * @return
     */
    @Transactional(readOnly = true)
    @Cacheable()
    public List<Like> findAll() {
        return dao.findAll();
    }

    /**
     * Saves like to database
     * @param like
     */
    @Transactional
    public void persist(Like like) {
        Objects.requireNonNull(like);
        dao.persist(like);
    }

    /**
     * Update like
     * @param like
     */
    @Transactional
    public void update(Like like) {
        dao.update(like);
    }

    /**
     * Removes like
     * @param like
     */
    @Transactional
    public void remove(Like like) {
        Objects.requireNonNull(like);
        //comment.setRemoved(true);
        dao.update(like);
    }
}
