/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.dao;

import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Like;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;


@Repository
public class LikeDao extends BaseDao<Like> {
    
    public LikeDao(){
        super(Like.class);
    }

    /**
     * Query for finding all Likes
     * @return List of found Likes
     */
    public List<Like> findAll() {
        return em.createQuery("SELECT l FROM Like l", Like.class).getResultList();
    }

    /**
     * Query for finding all likes on Article
     * @return List of found Likes
     */
    public List<Like> findByArticle(Article article) {
        Objects.requireNonNull(article);
        return em.createQuery("SELECT l FROM Like l inner join l.article la WHERE la=?1", Like.class).setParameter(1, article)
                 .getResultList();
    }
}
