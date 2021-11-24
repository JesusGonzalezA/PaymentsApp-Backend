package com.paymentsapp.paymentsapp.controller;

import com.paymentsapp.paymentsapp.controller.helpers.IAuthenticationFacade;
import com.paymentsapp.paymentsapp.exception.AlreadyExistsException;
import com.paymentsapp.paymentsapp.exception.BadCredentialsException;
import com.paymentsapp.paymentsapp.model.User;
import com.paymentsapp.paymentsapp.security.PasswordHelper;
import com.paymentsapp.paymentsapp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService userService;
    @Autowired
    private IAuthenticationFacade authenticationFacade;
    @Autowired
    private PasswordHelper passwordHelper;

    @PostMapping("")
    public ResponseEntity<User> add(@RequestBody User user) {
        try {
            userService.save(user);
            return new ResponseEntity<User>(user, HttpStatus.CREATED);
        } catch (AlreadyExistsException exception) {
                return new ResponseEntity(exception.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        try {
            userService.login(username,password);
            return new ResponseEntity<String>("Logged in", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        } catch (BadCredentialsException e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> get() {
        UserDetails userDetails = (UserDetails)authenticationFacade.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        try {
            User user = userService.get(username);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("")
    public ResponseEntity<User> update(@RequestBody User user){
        UserDetails userDetails = (UserDetails)authenticationFacade.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        try{
            User existingUser = userService.get(username);
            
            user.setUsername(username);
            user.setId(existingUser.getId());
            
            userService.update(user);
            return new ResponseEntity<User>(HttpStatus.OK);
        } catch (NoSuchElementException e){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("")
    public ResponseEntity<User> delete(){
        UserDetails userDetails = (UserDetails)authenticationFacade.getAuthentication().getPrincipal();
        String username = userDetails.getUsername();

        try {
            User user = userService.get(username);
            userService.delete(user.getId());
            return new ResponseEntity("Deleted user " + username, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }
}

