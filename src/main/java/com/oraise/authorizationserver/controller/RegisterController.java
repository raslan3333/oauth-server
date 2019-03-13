package com.oraise.authorizationserver.controller;

import com.oraise.authorizationserver.RoleRepository;
import com.oraise.authorizationserver.User;
import com.oraise.authorizationserver.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Register controller.
 */
@Controller
@RequestMapping

public class RegisterController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    /**
     * Index string.
     *
     * @param model the model
     * @return the string
     */
    @GetMapping("home")
    public String home(Model model) {
        String currentUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByUsername(currentUserName);
        model.addAttribute("currentUser", currentUser);
        return "home";
    }


    /**
     * Gets one user.
     *
     * @param model the model
     * @param user  the user
     * @return the one user
     */
    @GetMapping("signup")
    public String getOneUser(Model model, User user) {
        model.addAttribute("user", user);
        return "signup";
    }


    /**
     * Sets one user.
     *
     * @param model the model
     * @param user  the user
     * @return the one user
     */
    @PostMapping("signup")
    public String setOneUser(Model model, User user) {
        user.setEnabled(true);
        user.setRole(roleRepository.findByName("ROLE_USER"));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        model.addAttribute("currentUser", user);
        return "home";
    }


    /**
     * Gets users.
     *
     * @param model the model
     * @return the users
     */
    @GetMapping("users")
    public String getUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "users";
    }

    /**
     * Users string.
     *
     * @param user the user
     * @return the string
     */
    @PostMapping("users")
    public String users(User user) {
        return "users";
    }

    /**
     * Index string.
     *
     * @param res the res
     * @return the string
     */
    @GetMapping
    public String index(HttpServletResponse res) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean hasUserRole = auth.getAuthorities().stream().anyMatch(role -> role.getAuthority().contains("ROLE_ADMIN"));
        String[] hasAnyRole = auth.getAuthorities().toString().split("\\W+");
        List<String> arrayList = new ArrayList<>(Arrays.asList(hasAnyRole));

        if (arrayList.contains("ROLE_USER") || arrayList.contains("ROLE_ADMIN")) {
            try {
                res.sendRedirect("/home");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "index";
    }

    /**
     * Login string.
     *
     * @param res the res
     * @return the string
     */
    @GetMapping("/login")
    public String login(HttpServletResponse res) {
        return index(res);
    }


}
