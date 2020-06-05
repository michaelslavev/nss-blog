package the.nss.boys.blog.rest;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import the.nss.boys.blog.model.Like;
import the.nss.boys.blog.service.ArticleServicesFacade;

/**
 * Rest controller for Like
 *
 * Creates, Reads, Edits and Deletes data via Http requests
 */
@RestController
@RequestMapping("/api/likes")
public class LikeController{
    private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleServicesFacade articleServicesFacade;
    
    @Autowired
    public LikeController(ArticleServicesFacade articleServicesFacade){
        this.articleServicesFacade = articleServicesFacade;
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Like> getLikes() {
        return articleServicesFacade.findAllLikes();
    }

    /**
     *  Updates like on article if user has the authority
     * @param Id of Like from path url
     * @param like
     */
    //UPDATE LIKE
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.user.username == principal.username) ")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer Id, @RequestBody Like like) {
        if (!Id.equals(like.getId())) {
            LOG.debug("IDs are different.");
            return;
        }
        if (articleServicesFacade.findLikeByID(Id) == null) {
            LOG.debug("Like not found.");
            return;
        }
        articleServicesFacade.updateLike(like);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Like {} updated.", like);
        }
    }

    /**
     * Removes like if user has the authority
     * @param Id of like from path url
     * @param like
     */
    //REMOVE LIKE
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.user.username == principal.username) ")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") Integer Id, @RequestBody Like like) {
        if (!Id.equals(like.getId())) {
            LOG.debug("IDs are different.");
        }
        if (articleServicesFacade.findLikeByID(Id) == null) {
            LOG.debug("Like not found.");
        }
        articleServicesFacade.removeLike(like);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Like {} removed.", like);
        }
    }
}
