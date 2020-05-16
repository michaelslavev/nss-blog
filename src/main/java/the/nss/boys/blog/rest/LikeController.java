/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import the.nss.boys.blog.service.LikeService;


@RestController
@RequestMapping("/api/likes")
public class LikeController{
    private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

    private final LikeService likeService;
    
    @Autowired
    public LikeController(LikeService likeService){
        this.likeService = likeService;
    }
    
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Like> getLikes() {
        return likeService.findAll();
    }

    //UPDATE LIKE
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.user.username == principal.username) ")
    @RequestMapping(value="/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable("id") Integer Id, @RequestBody Like like) {
        if (!Id.equals(like.getId())) {
            System.out.println("IDs are different.");
        }
        if (likeService.find(Id) == null) {
            System.out.println("Like not found.");
        }
        likeService.update(like);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Like {} updated.", like);
        }
    }
    
    //REMOVE LIKE
    @PreAuthorize("hasRole('ROLE_ADMIN') or (filterObject.user.username == principal.username) ")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("id") Integer Id, @RequestBody Like like) {
        if (!Id.equals(like.getId())) {
            System.out.println("IDs are different.");
        }
        if (likeService.find(Id) == null) {
            System.out.println("Like not found.");
        }
        likeService.remove(like);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Like {} removed.", like);
        }
    }
}
