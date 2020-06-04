/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.dao;

import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

/**
 * Data access class of Comment
 */
@Repository
public class CommentDao extends BaseDao<Comment>{
    
    public CommentDao(){
        super(Comment.class);
    }

    /**
     * Query for finding all Comments
     * @return List of found Comments
     */
    @Override
    public List<Comment> findAll() {
        return em.createQuery("SELECT c FROM Comment c", Comment.class).getResultList();
    }

    /**
     * Query for finding all Comments on article
     * @return List of found Comments
     */
    public List<Comment> findByArticle(Article article) {
        Objects.requireNonNull(article);
        return em.createQuery("SELECT c FROM Comment c inner join c.article ca WHERE ca= ?1", Comment.class).setParameter(1,article).getResultList();
    }
}
