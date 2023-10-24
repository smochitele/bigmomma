package com.healinghaven.bigmomma.controller;

import com.healinghaven.bigmomma.entity.AccessKey;
import com.healinghaven.bigmomma.entity.User;
import com.healinghaven.bigmomma.enums.UserSearchCriteria;
import com.healinghaven.bigmomma.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UsersService service;

    @PostMapping("/api/userregistration")
    public User addUser(@RequestBody User user) {
        return service.addUser(user);
    }

    @GetMapping("/api/getuser")
    public User getUser(@RequestBody User user) {
        return service.getUser(user);
    }

    @GetMapping("/api/getusers")
    public List<User> getUsers() {
        return service.getUsers();
    }

    @GetMapping("/api/searchuser/{searchCriteria}/{criteriaValue}")
    public User getUserByCategory(@PathVariable int searchCriteria, @PathVariable String criteriaValue) {
        return service.getUserByCategory(UserSearchCriteria.searchCriteria(searchCriteria), criteriaValue);
    }

    @GetMapping("/api/searchusers/{searchCriteria}/{criteriaValue}")
    public List<User> getUsersByCategory(@PathVariable int searchCriteria, @PathVariable String criteriaValue) {
        return service.getUsersByCategory(UserSearchCriteria.searchCriteria(searchCriteria), criteriaValue);
    }

    @PutMapping("/api/updateuser")
    public User updateUser(@RequestBody User user) {
        return service.updateUser(user);
    }

    @PutMapping("/api/changepassword")
    public AccessKey changePassword(@RequestBody AccessKey accessKey) {
        return service.changePassword(accessKey);
    }

    @GetMapping("/api/deleteuser/{emailAddress}")
    public String deleteUser(@PathVariable String emailAddress) {
        service.deleteUser(emailAddress);
        return HttpStatus.OK.getReasonPhrase();
    }
}
