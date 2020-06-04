/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.rest;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import the.nss.boys.blog.interceptor.UserLoggerInterceptor;
import the.nss.boys.blog.model.User;
import the.nss.boys.blog.rest.util.RestUtils;
import the.nss.boys.blog.security.DefaultAuthenticationProvider;
import the.nss.boys.blog.security.model.AuthenticationToken;
import the.nss.boys.blog.security.model.UserDetails;
import the.nss.boys.blog.service.UserService;

import javax.interceptor.Interceptors;

/**
 * Rest controller for User
 *
 * Creates, Reads, Edits and Deletes data via Http requests
 */
@RestController
@RequestMapping("/api/users")
@Interceptors(UserLoggerInterceptor.class)
public class UserController{

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final DefaultAuthenticationProvider provider;

    @Autowired
    public UserController(UserService userService, DefaultAuthenticationProvider provider) {
        this.userService = userService;
        this.provider = provider;
    }

    /**
     * Register new user
     * @param user from HttpRequest_POST
     * @return
     */
    @RequestMapping(value="/register",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> register(@RequestBody User user) {
        userService.persist(user);
        LOG.debug("User {} successfully registered.", user);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/current");
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    /**
     * Gets current user if anyone is logged
     * @param principal
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @RequestMapping(value = "/current", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public User getCurrent(Principal principal) {
        final AuthenticationToken auth = (AuthenticationToken) principal;
        return auth.getPrincipal().getUser();
    }

    /**
     * Login user if undergoes authentication
     * @param user
     * @return
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> login(@RequestBody User user) {
        final Authentication t = new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
        provider.authenticate(t);

        LOG.debug("User {} successfully logged in.", user.getUsername());
        final UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
        return new ResponseEntity<>(details.getUser(), HttpStatus.ACCEPTED);
    }

    /*@RequestMapping("/**")
    public RedirectView redirect(){
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("", (Object) null);
        String loc = headers.get(HttpHeaders.LOCATION).get(0);
        return new RedirectView("redirect:/" + loc);
    }*/

    /*@RequestMapping("/**")
    public ResponseEntity redirect(){
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("", (Object) null);
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }*/



}

