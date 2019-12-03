package com.api.Gaming.api.api_v1;

import com.api.Gaming.api.entities.User;
import com.api.Gaming.api.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



@RestController
public class UserController {

    @Autowired private UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger();

    /**
     *
     * @param user_id
     * @return
     */
    @RequestMapping(value = "user/{user_id}", method = RequestMethod.GET)
    public User getUser(@PathVariable final Long user_id) {
        User user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            logger.warn("Attempted to get a user that did not exist in the database.");
            return null;
        }
        return user;
    }

    /**
     *
     * @param user_id
     * @param updateUserJson
     * @return
     */
    @RequestMapping(value = "user/{user_id}", method = RequestMethod.PUT, consumes = "application/json")
    public boolean updateUser(@PathVariable(name = "user_id") final Long user_id, @RequestBody String updateUserJson) {
        User user = userRepository.findById(user_id).orElse(null);
        if (user == null) {
            logger.warn("Attempted to update user that did not exist in the database.");
            return false;
        }
        if (updateUserJson != null && !updateUserJson.isEmpty()) {
            try {
                userRepository.save(User.createUserFromJson(updateUserJson));
            } catch (Exception e) {
                logger.error("Failed to parse User object from JSON and persist it.", e);
                return false;
            }
            return true;
        }
        logger.warn("Attempted to update user with bad/missing data.");
        return false;
    }

    /**
     *
     * @param newUserJson
     * @return
     */
    @RequestMapping(value = "user", method = RequestMethod.POST, consumes = "application/json")
    public boolean creatUser(@RequestBody String newUserJson) {
        if (newUserJson != null && !newUserJson.isEmpty()) {
            try {
                userRepository.save(User.createUserFromJson(newUserJson));
            } catch (Exception e) {
                logger.error("Failed to parse User object from JSON and persist it.", e);
                return false;
            }
            return true;
        }
        logger.warn("Attempted to create a user with bad/missing data.");
        return false;
    }

    /**
     *
     * @param user_id
     * @return
     */
    @RequestMapping(value = "user/{user_id}", method = RequestMethod.DELETE)
    public boolean deleteUser(@PathVariable final Long user_id) {
        if (userRepository.existsById(user_id)) {
            userRepository.deleteById(user_id);
            return true;
        }
        logger.warn("Attempted to delete user that did not exist in the database.");
        return false;
    }
}
