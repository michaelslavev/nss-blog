/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import the.nss.boys.blog.dao.ArticleDao;
import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Comment;
import the.nss.boys.blog.model.Like;
import the.nss.boys.blog.model.Topic;

/**
 * Service for Articles
 *
 * Adds, Removes, Find and Update articles then persist to database via ArticleDao
 */
@Service
@CacheConfig(cacheNames = "articles")
public class ArticleService {
    
    private final ArticleDao dao;
    
    @Autowired
    public ArticleService(ArticleDao dao){
        this.dao = dao;
    }
    @Transactional(readOnly = true)

    /**
     * Method for finding article by his ID
     */
    public Article find(Integer id) {
        return dao.find(id);
    }

    /**
     * Find all Articles
     */
    @Transactional(readOnly = true)
    @Cacheable()
    public List<Article> findAll() {
        return dao.findAll();
    }

    /**
     * Find article by topic
     * @param topic
     * @return
     */
    @Transactional(readOnly = true)
    public List<Article> findByTopic(Topic topic) {
        return dao.findByTopic(topic);
    }

    /**
     * Find article by date
     * @param date
     * @return
     */
    @Transactional(readOnly = true)
    public List<Article> findByDate(LocalDate date) {
        return dao.findByDate(date);
    }

    /**
     * Find article by title
     * @param title
     * @return
     */
    @Transactional(readOnly = true)
    public List<Article> findByTitle(String title) {
        return dao.findByTitle(title);
    }

    /**
     * Saves  article to database
     * @param article
     */
    @Transactional
    public void persist(Article article) {
        dao.persist(article);
    }

    /**
     * Updates article
     * @param article
     */
    @Transactional
    public void update(Article article) {
        dao.update(article);
    }

    /**
     * Removes article
     * @param article
     */
    @Transactional
    public void remove(Article article) {
        Objects.requireNonNull(article);
        article.setRemoved(true);
        dao.update(article);
    }


    /**
     * Adds comment to article
     * @param article
     * @param comment
     */
    @Transactional
    public void addComment(Article article, Comment comment){
        Objects.requireNonNull(article);
        Objects.requireNonNull(comment);
        article.addComment(comment);
        dao.update(article);
    }

    /**
     * Removes comment from article
     * @param article
     * @param comment
     */
    @Transactional
    public void removeComment(Article article, Comment comment) {
        Objects.requireNonNull(article);
        Objects.requireNonNull(comment);
        article.removeComment(comment);
        dao.update(article);
    }

    /**
     * Add like to article
     * @param article
     * @param like
     */
    @Transactional
    public void addLike(Article article, Like like){
        Objects.requireNonNull(article);
        Objects.requireNonNull(like);
        article.addLike(like);
        dao.update(article);
    }

    /**
     * Removes like from article
     * @param article
     * @param like
     */
    @Transactional
    public void removeLike(Article article, Like like) {
        Objects.requireNonNull(article);
        Objects.requireNonNull(like);
        article.removeLike(like);
        dao.update(article);
    }
}
