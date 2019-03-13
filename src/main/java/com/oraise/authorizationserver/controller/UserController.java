package com.oraise.authorizationserver.controller;

import com.oraise.authorizationserver.User;
import com.oraise.authorizationserver.UserData;
import com.oraise.authorizationserver.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * The type User controller.
 */
@RestController
@RequestMapping
public class UserController {

    /**
     * The User repository.
     */
    final UserRepository userRepository;

    /**
     * Instantiates a new User controller.
     *
     * @param userRepository the user repository
     */
    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Get one user user.
     *
     * @param id the id
     * @return the user
     */
    @GetMapping("/user/{id}")
    public User getOneUser(@PathVariable Long id){
        return userRepository.findById(id).get();
    }


    /**
     * Update user user.
     *
     * @param userData the user data
     * @param id       the id
     * @return the user
     * @throws Exception the exception
     */
    @PutMapping("/user/{id}")
    public User updateUser(@Valid @RequestBody UserData userData, @PathVariable Long id) throws Exception {
        return userRepository.findById(id).map(us -> {
            BeanUtils.copyProperties(userData , us);
            us.setId(id);
            return userRepository.save(us);
        }).get();
    }


    /**
     * Delete user.
     *
     * @param id the id
     */
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
    }



}
