package com.healinghaven.bigmomma.service;

import com.healinghaven.bigmomma.entity.AccessKey;
import com.healinghaven.bigmomma.entity.User;
import com.healinghaven.bigmomma.enums.UserSearchCriteria;
import com.healinghaven.bigmomma.enums.UserType;
import com.healinghaven.bigmomma.repository.UsersRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private static final Logger LOG = LoggerFactory.getLogger(UsersService.class);
    @Autowired
    private UsersRepository repository;

    //GET METHODS
    public User getUser(User user) {
        try {
            LOG.info("Attempting to find user[" + user + "] from DB");
            return repository.getUser(user);
        } catch (Exception e) {
            LOG.error("Failed to get user[" + user + "]", e);
            return null;
        }
    }

    public List<User> getUsers() {
        try {
            LOG.info("Getting all users from the DB");
            return repository.getUsers();
        } catch (Exception e) {
            LOG.error("Failed to retrieve users", e);
            return null;
        }
    }

    public User getUserByCategory(UserSearchCriteria searchCriteria, String criteriaValue) {
        try {
            LOG.info("Searching user by category[" + searchCriteria + "] with value[" + criteriaValue + "]");
            return repository.getUserByCategory(searchCriteria, criteriaValue);
        } catch (Exception e) {
            LOG.error("Failed to get user by category[" + searchCriteria + "]", e);
            return null;
        }
    }

    public List<User> getUsersByCategory(UserSearchCriteria searchCriteria, String criteriaValue) {
        try {
            LOG.info("Searching users by category[" + searchCriteria + "] with value[" + criteriaValue + "]");
            return repository.getUsersByCategory(searchCriteria, criteriaValue);
        } catch (Exception e) {
            LOG.error("Failed to get users by category[" + searchCriteria + "]", e);
            return null;
        }
    }

    //POST METHODS
    public User addUser(User user) {
        try {
            LOG.info("Adding user[" + user + "]");
            return repository.addUser(user);
        } catch (Exception e) {
            LOG.error("Failed to add user[" + user + "]", e);
            return null;
        }
    }

    //PUT METHODS
    public User updateUser(User user) {
        try {
            LOG.info("Updating user[" + user + "]");
            return repository.updateUser(user);
        } catch (Exception e) {
            LOG.error("Failed to update user[" + user + "]", e);
            return null;
        }
    }

    public void updateUserType(String emailAddress, UserType userType) {
        try {
            repository.updateUserType(emailAddress, userType);
            LOG.info("Attempting to update user[" + emailAddress + "] to user type[" + userType + "]");
        } catch (Exception e) {
            LOG.error("Failed to update user[" + emailAddress + "] to user type[" + userType + "]");
        }
    }

    public AccessKey changePassword(AccessKey accessKey) {
        try {
            LOG.info("Updating user password");
            return repository.changePassword(accessKey);
        } catch (Exception e) {
            LOG.error("Failed to update user password", e);
            return null;
        }
    }

    //DELETE METHODS
    public void deleteUser(String emailAddress) {
        try {
            LOG.info("Deleting user with email[" + emailAddress + "]");
            repository.deleteUser(emailAddress);
        } catch (Exception e) {
            LOG.error("Failed to delete user [" + emailAddress + "]", e);
        }
    }
}
