package com.webbuilders.webgym.controller;

import com.webbuilders.webgym.domain.User;
import com.webbuilders.webgym.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Slf4j
@Controller
public class LoginController {

    private UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginController(UserService userService,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage(ModelAndView modelAndView) {

        //modelAndView.addObject("user", new User());
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value="/login", method = RequestMethod.POST)
    public ModelAndView processLogin(ModelAndView modelAndView,
                                     BindingResult bindingResult,
                                     @RequestParam Map<String, String> requestParams,
                                     RedirectAttributes redir,
                                     Model model) {

        modelAndView.setViewName("login");
        User user;

        try {
            user = userService.findByEmail(requestParams.get("email"));
        } catch (RuntimeException re) {
            modelAndView.setViewName("login");
            return modelAndView;
        }

        if (!bCryptPasswordEncoder.matches(requestParams.get("password"), user.getPassword())) {
            bindingResult.reject("email");
            bindingResult.reject("password");
            redir.addFlashAttribute("errorMessage", "Not registered email or invalid password!");
            return modelAndView;
        }

        //modelAndView.addObject(user);
        modelAndView.setViewName("redirect:index");

        return modelAndView;
    }

}
