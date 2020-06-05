package the.nss.boys.blog.rest;

import java.util.List;
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
import the.nss.boys.blog.model.Article;
import the.nss.boys.blog.model.Topic;
import the.nss.boys.blog.rest.util.RestUtils;
import the.nss.boys.blog.service.ArticleServicesFacade;

/**
 * Rest controller for Topics
 *
 * Creates, Reads, Edits and Deletes data via Http requests
 */
@RestController
@RequestMapping("/api/topics")
public class TopicController{
    
    private static final Logger LOG = LoggerFactory.getLogger(TopicController.class);

    private final ArticleServicesFacade articleServicesFacade;
    
    @Autowired
    public TopicController(ArticleServicesFacade articleServicesFacade) {
        this.articleServicesFacade = articleServicesFacade;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Topic> getTopics() {
        return articleServicesFacade.findAllTopics();
    }

    /**
     * Adds new topic if user has the authority
     * @param topic from HttpRequest_POST
     * @return
     */
    //ADD TOPIC only admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTopic(@RequestBody Topic topic) {
        articleServicesFacade.persistTopic(topic);
        LOG.debug("Created topic {}.", topic);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", topic.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Get topic by id
     * @param id of topic from path url
     * @return Topic object
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Topic getById(@PathVariable("id") Integer id) {
        final Topic category = articleServicesFacade.findTopicByID(id);
        if (category == null) {
            throw NotFoundException.create("Topic", id);
        }
        return category;
    }

    /**
     * Gets all articles of topic ID
     * @param id of topic from path url
     * @return List of Articles
     */
    //FIND ARTICLE BY TOPIC
    @RequestMapping(value = "/{id}/articles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Article> getArticleByTopic(@PathVariable("id") Integer id) {
        return articleServicesFacade.findArticleByTopic(articleServicesFacade.findTopicByID(id));
    }

    /**
     * Adds topic to article if user is logged in
     * @param id of topic from path url
     * @param article
     */
    //ADD ARTICLE A TOPIC
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "/{id}/articles", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addArticleToTopic(@PathVariable("id") Integer id, @RequestBody Article article) {
        final Topic topic = getById(id);
        articleServicesFacade.addTopicToArticle(topic, article);
        LOG.debug("Article {} added into topic {}.", article, topic);
    }

    /**
     * Removes topic from article if user is logged in
     * @param topicId from path url
     * @param articleId from path url
     */
    //REMOVE ARTICLE FROM TOPIC
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "/{topicId}/articles/{articleId}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeArticleFromTopic(@PathVariable("topicId") Integer topicId,
                                          @PathVariable("articleId") Integer articleId) {
        final Topic topic = getById(topicId);
        final Article toRemove = articleServicesFacade.findArticleByID(articleId);
        if (toRemove == null) {
            throw NotFoundException.create("Article", articleId);
        }
        articleServicesFacade.removeTopicFromArticle(topic, toRemove);
        LOG.debug("Article {} removed from topic {}.", toRemove, topic);
    }
}
