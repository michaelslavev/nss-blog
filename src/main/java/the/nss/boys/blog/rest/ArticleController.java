/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.rest;

import java.security.Principal;
import java.security.Security;
import java.time.LocalDateTime;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import the.nss.boys.blog.exception.NotFoundException;
import the.nss.boys.blog.exception.ValidationException;
import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Comment;
import the.nss.boys.blog.model.Like;
import the.nss.boys.blog.rest.util.RestUtils;
import the.nss.boys.blog.security.SecurityUtils;
import the.nss.boys.blog.service.ArticleService;
import the.nss.boys.blog.service.CommentService;
import the.nss.boys.blog.service.LikeService;


@RestController
@RequestMapping("/api/articles")
public class ArticleController{
    private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleService articleService;
    private final CommentService commentService;
    private final LikeService likeService;

    @Autowired
    public ArticleController(ArticleService articleService, CommentService commentService, LikeService likeService){
        this.articleService = articleService;
        this.commentService = commentService;
        this.likeService = likeService;
    }
    
    //GET ALL ARTICLES
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Article> getAllArticles(){
       final List<Article> articles;
       articles = articleService.findAll();
       return articles;
    }
    
    
    //FIND ARTICLE BY ID
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Article find(@PathVariable("id") Integer id) {
        final Article result = articleService.find(id);
        if (result == null) {
            System.out.println("No article with this id.");
        }
        return result;
    }
    
    //FIND ARTICLES BY TITLE
    @RequestMapping(value = "/title={title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Article> findByTitle(@PathVariable("title") String title) {
        final List<Article> result = articleService.findByTitle(title);
        if (result == null) {
            System.out.println("No article with this title.");
        }
        return result;
    }
    
    //FIND ARTICLE BY DATE
    @RequestMapping(value = "/date={date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Article> findByDate(@PathVariable("date") LocalDateTime created) {
        final List<Article> result = articleService.findByDate(created);
        if (result == null) {
            System.out.println("No article with this date.");
        }
        return result;
    }
    
    
    //CREATE ARTICLE
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or (filterObject.author.username == principal.username)")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createArticle(@RequestBody Article article) {
        article.setDate(LocalDateTime.now());
        article.setUser(SecurityUtils.getCurrentUser());
        articleService.persist(article);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Article {} persisted successfully.", article);
        }
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", article.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    //UPDATE ARTICLE
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.author.username == principal.username) ")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArticle(@PathVariable("id") Integer id, @RequestBody Article article) {
        final Article original = find(id);
        if (!original.getId().equals(article.getId())) {
            throw new ValidationException("Article identifier in the data does not match the one in the request URL.");
        }
        articleService.update(article);
        LOG.debug("Updated article {}.", article);
    }
    
    //REMOVE ARTICLE    
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.author.username == principal.username) ")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
     public void removeArticle(@PathVariable("id") Integer id) {
        final Article toRemove = articleService.find(id);
        if (toRemove == null) {
            return;
        }
        articleService.remove(toRemove);
        LOG.debug("Removed article {}.", toRemove);
    }
    
   
    
    //ADD COMMENT TO ARTICLE
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addCommentToArticle(@PathVariable("id") Integer id, @RequestBody Comment comment) {
        final Article article = find(id);
        articleService.addComment(article, comment);
        LOG.debug("Comment {} added into article {}.", article, comment);
    }
    
    
    //REMOVE COMMENT FROM ARTICLE
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or (filterObject.author.username == principal.username)")
    @RequestMapping(value = "/{articleId}/comments/{commentId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCommentFromArticle(@PathVariable("commentId") Integer commentId,
                                          @PathVariable("articleId") Integer articleId) {
        final Article article = find(articleId);
        final Comment toRemove = commentService.find(commentId);
        if (toRemove == null) {
            throw NotFoundException.create("Comment", commentId);
        }
        articleService.removeComment(article, toRemove);
        LOG.debug("Comment {} removed from article {}.", toRemove, article);
    }
    
    
    //VIEW ARTICLES COMMENTS
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
     public List<Comment> getCommentsOfArticle(@PathVariable("id") Integer id) {
        return commentService.findByArticle(articleService.find(id));
    }
    
    
     
    //VIEW ARTICLES LIKES
    @RequestMapping(value = "/{id}/likes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getArticleLikes(@PathVariable("id") Integer id){
        List<Like> likes = likeService.findByArticle(articleService.find(id));
        List<String> usernames = new ArrayList<>();
        likes.forEach((like) -> {
            usernames.add(like.getUser().getUsername());
        });
       return usernames;
    }
     
    
    //ADD LIKES TO ARTICLE
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "/{id}/likes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addLikeToArticle(@PathVariable("id") Integer id, @RequestBody Like like) {
        final Article article = find(id);
        articleService.addLike(article, like);
        LOG.debug("Like {} added into article {}.", article, like);
    }
    

}
