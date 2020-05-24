/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.service;

import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import the.nss.boys.blog.dao.ArticleDao;
import the.nss.boys.blog.dao.TopicDao;
import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Topic;

@Service
public class TopicService {
    
    private final TopicDao dao;
    private final ArticleDao articleDao;
    
    @Autowired
    public TopicService(TopicDao dao, ArticleDao articleDao) {
        this.dao = dao;
        this.articleDao = articleDao;
    }
    
    @Transactional(readOnly = true)
    @Cacheable("findAllTopics")
    public List<Topic> findAll() {
        return dao.findAll();
    }

    @Transactional(readOnly = true)
    public Topic find(Integer id) {
        return dao.find(id);
    }

    @Transactional
    public void persist(Topic topic) {
        Objects.requireNonNull(topic);
        dao.persist(topic);
    }
    
    /**
     * Adds the specified topic to the article.
     *
     * @param topic Target topic
     * @param article  Article to add
     */
    
    @Transactional
    public void addTopic(Topic topic, Article article) {
        Objects.requireNonNull(topic);
        Objects.requireNonNull(article);
        article.addTopic(topic);
        articleDao.update(article);
    }

    
    @Transactional
    public void removeTopic(Topic topic, Article article) {
        Objects.requireNonNull(topic);
        Objects.requireNonNull(article);
        article.removeTopic(topic);
        articleDao.update(article);
    }
}
