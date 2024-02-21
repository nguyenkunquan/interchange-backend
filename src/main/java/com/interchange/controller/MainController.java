package com.interchange.controller;

import com.interchange.config.TwilioConfig;
import com.interchange.entities.Role;
import com.interchange.entities.User;
import com.interchange.repository.UserRepository;
import com.interchange.service.TwilioOTPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import java.security.Principal;

@Controller
public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TwilioConfig twilioConfig;
    @Autowired
    private TwilioOTPService otpService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage() {
        return "home";
    }
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
        model.addAttribute("error", error);
        return "login";
    }

    @RequestMapping(value = "/loginSuccess")
    public String LoginResult(Model model, Principal principal) {
        if(principal == null) {
            return "redirect:/login";
        }
        String username = principal.getName();
        User user = userRepository.findByUserIdOrEmailOrPhoneNumber(username, username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User can't found "));
        String role = String.valueOf(user.getRole());
        System.out.println(role);
        if(role.equals("ADMIN")) {
            return "adminPage";
        } else if(role.equals("CUSTOMER")) {
            model.addAttribute("username", username);
            return "home";
        }
        else
            return "login";
    }
    @RequestMapping("/403")
    public String DeniedAccess(Model model) {
        model.addAttribute("error", "You don't have accessed this page");
        return "accessDeniedPage";
    }

    @RequestMapping(value ="/logoutSuccessfully", method = RequestMethod.GET)
    public String LogoutSuccessfulPage(Model model) {
        model.addAttribute("logoutNotification", "Logout Successfully");
        return "home";
    }

    @RequestMapping("/registerPage")
    public String RegisterPage(Model model) {
        String rePassword = "";
        model.addAttribute("user", new User());
        model.addAttribute("rePassword", rePassword);
        return "register";
    }

    @PostMapping(value = "/register")
    public String checkRegister(@Valid User user, BindingResult bindingResult, @RequestParam("rePassword") String rePassword, HttpSession session) {
        if(userRepository.existsByUserId(user.getUserId())) {
            bindingResult.addError(new FieldError(
                    "user","userId","This User ID already has in system"));
        }
        if(userRepository.existsByEmail(user.getEmail())) {
            bindingResult.addError(new FieldError(
                    "user","email","This Email already has in system"));
        }
        if(userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            bindingResult.addError(new FieldError(
                    "user","phoneNumber","This Phone number already has in system"));
        }
        if(!rePassword.equals(user.getPassword())) {
            bindingResult.addError(new FieldError(
                    "user", "password", "The re-enter password don't match the password"));
        }
        System.out.println(user.getBirthDate());
        System.out.println(user);
        session.setAttribute("user", user);

        if(bindingResult.hasErrors()) {
            return "register";
        }
        return "redirect:send";
    }
    @GetMapping(value = "/send")
    public String sendMessage(HttpSession session) {
        if(twilioConfig == null || twilioConfig.getTrialNumber() == null) {
            return "register";
        }
        User user = (User) session.getAttribute("user");
        if(user == null) {
            return "register";
        }
        System.out.println(user);
        otpService.sendOTPForPasswordReset(user.getPhoneNumber());

        session.setAttribute("user", user);
        return "otp";
    }
    @PostMapping(value = "/checkOTP")
    public String checkOTP(@RequestParam("enteredOTP") String enteredOTP, HttpSession session, Model model) {
        if(otpService.verifyOTP(enteredOTP)) {
            User user = (User) session.getAttribute("user");
            if(user == null) {
                return "otp";
            }
            user.setRole(Role.CUSTOMER);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            session.invalidate();
            return "home";
        } else {
            model.addAttribute("error", "Invalid OTP. Please try again");
            return "otp";
        }
    }
}

