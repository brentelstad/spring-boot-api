package com.api.Gaming.api.api_v1;

import com.api.Gaming.api.entities.User;
import com.api.Gaming.api.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final UserService userService;

    private static final Logger logger = LogManager.getLogger();

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * @param user_id
     * @return
     */
    @RequestMapping(value = "user/{user_id}", method = RequestMethod.GET)
    public User getUser(@PathVariable final Long user_id) {
        User user = userService.getUserById(user_id);
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
        User user = userService.getUserById(user_id);
        if (user == null) {
            logger.warn("Attempted to update user that did not exist in the database.");
            return false;
        }
        if (updateUserJson != null && !updateUserJson.isEmpty()) {
           return userService.saveUserFromJSON(updateUserJson);
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
            return userService.saveUserFromJSON(newUserJson);
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
        if (userService.userExists(user_id)) {
            userService.deleteUser(user_id);
            return true;
        }
        logger.warn("Attempted to delete user that did not exist in the database.");
        return false;
    }
}
