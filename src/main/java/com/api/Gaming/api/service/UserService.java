package com.api.Gaming.api.service;

import com.api.Gaming.api.entities.User;
import com.api.Gaming.api.repository.UserRepository;
import com.fasterxml.jackson.core.JsonParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     *
     * @param userId
     * @return
     */
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    /**
     *
     */
    public boolean userExists(Long userId) {
        return userRepository.existsById(userId);
    }

    /**
     *
     * @param userId
     */
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     *
     * @param userAsJSON
     * @return
     */
    public boolean saveUserFromJSON(String userAsJSON) {
        boolean success = true;
        try {
            userRepository.save(User.createUserFromJson(userAsJSON));
        } catch (JsonParseException e) {
            logger.error("Failed to parse User object from JSON and persist it.", e);
            success = false;
        } catch (Exception e) {
            logger.error("Failed to parse User object, unkown error.", e);
            success = false;
        }
        return success;
    }

}
