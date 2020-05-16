/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import the.nss.boys.blog.model.Comment;
import the.nss.boys.blog.service.CommentService;


@RestController
@RequestMapping("/api/comments")
public class CommentController{
    
    private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

    private final CommentService commentService;
    
    @Autowired
    public CommentController(CommentService commentService){
        this.commentService = commentService;
    }

    //UPDATE COMMENT
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.user.username == principal.username) ")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer Id, @RequestBody Comment comment) {
        if (!Id.equals(comment.getId())) {
            System.out.println("IDs are different.");
        }
        if (commentService.find(Id) == null) {
            System.out.println("Comment not found.");
        }
        commentService.update(comment);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Comment {} updated.", comment);
        }
    }
    
    //REMOVE COMMENT
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.user.username == principal.username) ")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer Id, @RequestBody Comment comment) {
        if (!Id.equals(comment.getId())) {
            System.out.println("IDs are different.");
        }
        if (commentService.find(Id) == null) {
            System.out.println("Comment not found.");
        }
        commentService.remove(comment);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Comment {} deleted.", comment);
        }
    }
}
