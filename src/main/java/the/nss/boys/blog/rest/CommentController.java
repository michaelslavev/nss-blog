package the.nss.boys.blog.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import the.nss.boys.blog.model.Comment;
import the.nss.boys.blog.service.ArticleServicesFacade;

/**
 * Rest controller for Comment
 *
 * Creates, Reads, Edits and Deletes data via Http requests
 */
@RestController
@RequestMapping("/api/comments")
public class CommentController{
    
    private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

    private final ArticleServicesFacade articleServicesFacade;
    
    @Autowired
    public CommentController(ArticleServicesFacade articleServicesFacade){
        this.articleServicesFacade = articleServicesFacade;
    }

    /**
     * Update comment if user has the authority
     * @param Id of comment from path url
     * @param comment from HttpRequest_PUT
     */
    //UPDATE COMMENT
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.user.username == principal.username) ")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer Id, @RequestBody Comment comment) {
        if (!Id.equals(comment.getId())) {
            if (LOG.isDebugEnabled())
                LOG.debug("IDs are different.");
        }
        if (articleServicesFacade.findCommentByID(Id) == null) {
            if (LOG.isDebugEnabled())
                LOG.debug("Comment not found.");
        }
        articleServicesFacade.updateComment(comment);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Comment {} updated.", comment);
        }
    }

    /**
     * Deletes comment if user has the authority
     * @param Id of comment from path url
     * @param comment
     */
    //REMOVE COMMENT
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.user.username == principal.username) ")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer Id, @RequestBody Comment comment) {
        if (!Id.equals(comment.getId())) {
            if (LOG.isDebugEnabled())
                LOG.debug("IDs are different.");
        }
        if (articleServicesFacade.findCommentByID(Id) == null) {
            if (LOG.isDebugEnabled())
                LOG.debug("Comment not found.");
        }
        articleServicesFacade.removeComment(comment);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Comment {} deleted.", comment);
        }
    }
}
