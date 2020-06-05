package the.nss.boys.blog.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Comment;
import the.nss.boys.blog.model.Like;
import the.nss.boys.blog.model.Topic;

import java.time.LocalDate;
import java.util.List;

/**
 * Facade for article related services.
 * Inkoves methods on other services.
 * Layer between REST controllers and services.
 */
@Service
public class ArticleServicesFacade {

    private final ArticleService articleService;
    private final CommentService commentService;
    private final LikeService likeService;
    private final TopicService topicService;

    @Autowired
    public ArticleServicesFacade(ArticleService articleService, CommentService commentService, LikeService likeService, TopicService topicService){
        this.articleService = articleService;
        this.commentService = commentService;
        this.likeService = likeService;
        this.topicService = topicService;
    }

    public List<Comment> findCommentByArticle(Article article) {
        return commentService.findByArticle(article);
    }

    public Comment findCommentByID(Integer id) {
        return commentService.find(id);
    }

    public List<Comment> findAllComments() {
        return commentService.findAll();
    }

    public void persistComment(Comment comment) {
        commentService.persist(comment);
    }

    public void updateComment(Comment comment) {
        commentService.update(comment);
    }

    public void removeComment(Comment comment) {
        commentService.update(comment);
    }

    public Article findArticleByID(Integer id) {
        return articleService.find(id);
    }

    public List<Article> findAllArticles() {
        return articleService.findAll();
    }

    public List<Article> findArticleByTopic(Topic topic) {
        return articleService.findByTopic(topic);
    }

    public List<Article> findArticleByDate(LocalDate date) {
        return articleService.findByDate(date);
    }

    public List<Article> findArticleByTitle(String title) {
        return articleService.findByTitle(title);
    }

    public void persistArticle(Article article) {
        articleService.persist(article);
    }

    public void updateArticle(Article article) {
        articleService.update(article);
    }

    public void removeArticle(Article article) {
        articleService.update(article);
    }

    public void addCommentToArticle(Article article, Comment comment){
        articleService.update(article);
    }

    public void removeCommentFromArticle(Article article, Comment comment) {
        articleService.update(article);
    }

    public void addLikeToArticle(Article article, Like like){
        articleService.update(article);
    }

    public void removeLikeFromArticle(Article article, Like like) {
        articleService.update(article);
    }

    public List<Like> findLikesByArticle(Article article) {
        return likeService.findByArticle(article);
    }

    public Like findLikeByID(Integer id) {
        return likeService.find(id);
    }

    public List<Like> findAllLikes() {
        return likeService.findAll();
    }

    public void persistLike(Like like) {
        likeService.persist(like);
    }

    public void updateLike(Like like) {
        likeService.update(like);
    }

    public void removeLike(Like like) {
        likeService.update(like);
    }

    public List<Topic> findAllTopics() {
        return topicService.findAll();
    }

    public Topic findTopicByID(Integer id) {
        return topicService.find(id);
    }

    public void persistTopic(Topic topic) {
        topicService.persist(topic);
    }

    public void addTopicToArticle(Topic topic, Article article) {
        topicService.addTopic(topic,article);
    }

    public void removeTopicFromArticle(Topic topic, Article article) {
        topicService.removeTopic(topic, article);
    }
}
