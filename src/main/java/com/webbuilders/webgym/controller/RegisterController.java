package com.webbuilders.webgym.controller;

import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import com.webbuilders.webgym.domain.User;
import com.webbuilders.webgym.services.UserService;
import com.webbuilders.webgym.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserService userService;
    private Validator validator;

    @Autowired
    public RegisterController(BCryptPasswordEncoder bCryptPasswordEncoder,
                              UserService userService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.validator = new Validator();
    }

    @RequestMapping(value="/register", method = RequestMethod.GET)
    public ModelAndView showRegistrationPage(ModelAndView modelAndView, User user){

        modelAndView.addObject("user", user);
        modelAndView.setViewName("register");

        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView processRegistrationForm(ModelAndView modelAndView,
                                                @Valid User user,
                                                BindingResult bindingResult) {

        try {
            userService.findByEmail(user.getEmail());
            modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a user registered with the email provided.");
            modelAndView.setViewName("register");
            bindingResult.reject("email");
        } catch (RuntimeException re) {
        }

        if (!validator.isValidEmail(user.getEmail())) {
            modelAndView.addObject("invalidEmail", "The given email is invalid!");
            modelAndView.setViewName("register");
            bindingResult.reject("email");
        }

        if (!validator.isValidFirstName(user.getFirstName())) {
            modelAndView.addObject("invalidFirstName", "The given First name is invalid!");
            modelAndView.setViewName("register");
            bindingResult.reject("firstName");
        }

        if (!validator.isValidLastName(user.getLastName())) {
            modelAndView.addObject("invalidLastName", "The given Last name is invalid!");
            modelAndView.setViewName("register");
            bindingResult.reject("lastName");
        }

        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("register");
        } else {

            user.setEnabled(false);
            user.setConfirmationToken(UUID.randomUUID().toString());
            userService.saveUser(user);

            modelAndView.addObject("token", user.getConfirmationToken());
            modelAndView.setViewName("redirect:/confirm");
        }

        return modelAndView;
    }

    @RequestMapping(value="/confirm", method = RequestMethod.GET)
    public ModelAndView confirmRegistration(ModelAndView modelAndView, @RequestParam("token") String token) {

        User user = userService.findByConfirmationToken(token);

        if (user == null) {
            modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
        } else {
            modelAndView.addObject("confirmationToken", user.getConfirmationToken());
        }

        modelAndView.setViewName("confirm");
        return modelAndView;
    }

    @RequestMapping(value="/confirm", method = RequestMethod.POST)
    public ModelAndView confirmRegistration(ModelAndView modelAndView,
                                            BindingResult bindingResult,
                                            @RequestParam Map<String, String> requestParams,
                                            RedirectAttributes redir) {

        modelAndView.setViewName("confirm");

        if (!validator.isValidPassword(requestParams.get("password"))) {
            bindingResult.reject("password");

            redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.\nThe password must contains one uppercase letter, one lowercase letter and one number\nThe password must have at least 8 characters.");

            modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
            return modelAndView;
        }



        if (!requestParams.get("password").equals(requestParams.get("passwordConfirm"))) {
            bindingResult.reject("passwordConfirm");

            redir.addFlashAttribute("errorMessage", "The passwords not identical.");

            modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
            return modelAndView;
        }

        User user = userService.findByConfirmationToken(requestParams.get("token"));
        user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
        user.setEnabled(true);

        userService.saveUser(user);

        modelAndView.addObject("successMessage", "Your password has been set!");
        modelAndView.setViewName("confirm");

        return modelAndView;
    }

}
