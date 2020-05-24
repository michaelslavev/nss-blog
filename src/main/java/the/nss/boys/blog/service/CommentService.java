/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.service;

import org.springframework.cache.annotation.Cacheable;
import the.nss.boys.blog.dao.CommentDao;
import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Comment;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    
    private final CommentDao dao;
    
    public CommentService (CommentDao dao){
        this.dao = dao;
    }
    
    @Transactional(readOnly = true)
    public List<Comment> findByArticle(Article article) {
        return dao.findByArticle(article);
    }
    
    @Transactional(readOnly = true)
    public Comment find(Integer id) {
        return dao.find(id);
    }
    
    @Transactional(readOnly = true)
    @Cacheable("findAllComments")
    public List<Comment> findAll() {
        return dao.findAll();
    }
    
    @Transactional
    public void persist(Comment comment) {
        Objects.requireNonNull(comment);
        dao.persist(comment);
    }
    
    @Transactional
    public void update(Comment comment) {
        dao.update(comment);
    }
    
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
