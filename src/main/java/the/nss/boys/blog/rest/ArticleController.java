package the.nss.boys.blog.rest;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import the.nss.boys.blog.builder.ArticleBuilder;
import the.nss.boys.blog.exception.NotFoundException;
import the.nss.boys.blog.exception.ValidationException;
import the.nss.boys.blog.interceptor.ArticleLoggerInterceptor;
import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Comment;
import the.nss.boys.blog.model.Like;
import the.nss.boys.blog.rest.util.RestUtils;
import the.nss.boys.blog.security.SecurityUtils;
import the.nss.boys.blog.service.ArticleServicesFacade;

import javax.interceptor.Interceptors;

/**
 * Rest controller for Article
 *
 * Creates, Reads, Edits and Deletes data via Http requests
 */
@RestController
@RequestMapping("/api/articles")
@Interceptors(ArticleLoggerInterceptor.class)
public class ArticleController{
    private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleServicesFacade articleServicesFacade;

    @Autowired
    public ArticleController(ArticleServicesFacade articleServicesFacade){
        this.articleServicesFacade = articleServicesFacade;
    }

    /**
     * Get all articles
     * @return List of articles
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Article> getAllArticles(){
       final List<Article> articles;
       articles = articleServicesFacade.findAllArticles();
       return articles;
    }


    /**
     * Get article by ID
     * @param id from HttpRequest_GET
     * @return article
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Article find(@PathVariable("id") Integer id) {
        final Article result = articleServicesFacade.findArticleByID(id);
        return result;
    }

    /**
     * Get articles by title
     * @param title from HttpRequest_GET
     * @return List of articles
     */
    //FIND ARTICLES BY TITLE
    @RequestMapping(value = "/title={title}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Article> findByTitle(@PathVariable("title") String title) {
        final List<Article> result = articleServicesFacade.findArticleByTitle(title);
        return result;
    }
    
    //FIND ARTICLE BY DATE

    /**
     * Get articles created on specific time
     * @param created from HttpRequest_GET
     * @return List of articles
     */
    @RequestMapping(value = "/date={date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Article> findByDate(@PathVariable("date") LocalDate created) {
        final List<Article> result = articleServicesFacade.findArticleByDate(created);
        return result;
    }

    /**
     * Creates new article if user has the authority
     * @param article from HttpRequest_POST
     * @return HttpStatus status code
     */
    //CREATE ARTICLE
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or (filterObject.author.username == principal.username)")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createArticle(@RequestBody Article article) {
        ArticleBuilder.getInstance().startBuilding();
        ArticleBuilder.getInstance().addContent(article.getContent());
        ArticleBuilder.getInstance().addTitle(article.getTitle());
        ArticleBuilder.getInstance().addDate();
        ArticleBuilder.getInstance().addTopic(article.getTopics().get(0));
        ArticleBuilder.getInstance().addUser(SecurityUtils.getCurrentUser());
        articleServicesFacade.persistArticle(ArticleBuilder.getInstance().getBuiltArticle());
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", article.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Updates article if user has the authority
     * @param id from path url
     * @param article from HttpRequest_PUT
     */
    //UPDATE ARTICLE
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.author.username == principal.username) ")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArticle(@PathVariable("id") Integer id, @RequestBody Article article) {
        final Article original = find(id);
        if (!original.getId().equals(article.getId())) {
            throw new ValidationException("Article identifier in the data does not match the one in the request URL.");
        }
        articleServicesFacade.updateArticle(article);
    }

    /**
     * Removes article if user has the authority
     * @param id from path url
     */
    //REMOVE ARTICLE    
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.author.username == principal.username) ")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
     public void removeArticle(@PathVariable("id") Integer id) {
        final Article toRemove =articleServicesFacade.findArticleByID(id);
        if (toRemove == null) {
            return;
        }
        articleServicesFacade.removeArticle(toRemove);
        if(LOG.isDebugEnabled())
            LOG.debug("Removed article {}.", toRemove);
    }


    /**
     * Add new comment to article if user is logged in
     * @param id from path url
     * @param comment from HttpRequest_POST
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addCommentToArticle(@PathVariable("id") Integer id, @RequestBody Comment comment) {
        final Article article = find(id);
        articleServicesFacade.addCommentToArticle(article,comment);
        if(LOG.isDebugEnabled())
            LOG.debug("Comment {} added into article {}.", article, comment);
    }


    /**
     * Removes comment from article if user has the authority
     * @param commentId from path url
     * @param articleId from path url
     */
    //REMOVE COMMENT FROM ARTICLE
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or (filterObject.author.username == principal.username)")
    @RequestMapping(value = "/{articleId}/comments/{commentId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeCommentFromArticle(@PathVariable("commentId") Integer commentId,
                                          @PathVariable("articleId") Integer articleId) {
        final Article article = find(articleId);
        final Comment toRemove =articleServicesFacade.findCommentByID(commentId);
        if (toRemove == null) {
            throw NotFoundException.create("Comment", commentId);
        }
        articleServicesFacade.removeCommentFromArticle(article,toRemove);
        if(LOG.isDebugEnabled())
            LOG.debug("Comment {} removed from article {}.", toRemove, article);
    }

    /**
     * Get all comments of article
     * @param id of Article from path url
     * @return List of Comments
     */
    //VIEW ARTICLES COMMENTS
    @RequestMapping(value = "/{id}/comments", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
     public List<Comment> getCommentsOfArticle(@PathVariable("id") Integer id) {
        return articleServicesFacade.findCommentByArticle(articleServicesFacade.findArticleByID(id));
    }


    /**
     * Get all likes of article
     * @param id of Article from path url
     * @return List of Likes
     */
    //VIEW ARTICLES LIKES
    @RequestMapping(value = "/{id}/likes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<String> getArticleLikes(@PathVariable("id") Integer id){
        List<Like> likes =articleServicesFacade.findLikesByArticle(articleServicesFacade.findArticleByID(id));
        return likes.stream().map((l) -> l.getUser().getUsername()).collect(Collectors.toList());
    }

    /**
     * Add like to article if user is logged in
     * @param id of Article from path url
     * @param like
     */
    //ADD LIKES TO ARTICLE
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "/{id}/likes", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addLikeToArticle(@PathVariable("id") Integer id, @RequestBody Like like) {
        final Article article = find(id);
        articleServicesFacade.addLikeToArticle(article,like);
        if(LOG.isDebugEnabled())
            LOG.debug("Like {} added into article {}.", article, like);
    }
    

}
