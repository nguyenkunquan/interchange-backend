//package com.interchange.controller;
//import com.interchange.config.TwilioConfig;
//import com.interchange.dto.AuthDTO.ChangePasswordDTO;
//import com.interchange.entities.Role;
//import com.interchange.entities.User;
//import com.interchange.repository.UserRepository;
//import com.interchange.service.TwilioOTPService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.FieldError;
//import org.springframework.web.bind.annotation.*;
//import jakarta.servlet.http.HttpSession;
//import jakarta.validation.Valid;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import java.security.Principal;
//
//@Controller
//public class MainController {
//    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private TwilioConfig twilioConfig;
//    @Autowired
//    private TwilioOTPService otpService;
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @GetMapping(value = "/")
//    public String homePage() {
//        return "home";
//    }
//    @GetMapping(value = "/login")
//    public String loginPage(@RequestParam(name = "error", required = false) String error, Model model) {
//        model.addAttribute("error", error);
//        return "login";
//    }
//
//    @RequestMapping(value = "/loginSuccess")
//    public String loginResult(Model model, Principal principal) {
//        if(principal == null) {
//            return "redirect:/login";
//        }
//        String username = principal.getName();
//        User user = userRepository.findByUserIdOrEmailOrPhoneNumber(username, username, username)
//                .orElseThrow(() -> new UsernameNotFoundException("User can't found "));
//        String role = String.valueOf(user.getRole());
//        logger.info(role);
//        if(role.equals("ADMIN")) {
//            return "adminPage";
//        } else if(role.equals("CUSTOMER")) {
//            model.addAttribute("username", username);
//            return "home";
//        }
//        else
//            return "login";
//    }
//    @RequestMapping("/403")
//    public String deniedAccess(Model model) {
//        model.addAttribute("error", "You don't have accessed this page");
//        return "accessDeniedPage";
//    }
//
//    @GetMapping(value ="/logoutSuccessfully")
//    public String logoutSuccessfulPage(Model model) {
//        model.addAttribute("logoutNotification", "Logout Successfully");
//        return "home";
//    }
//
//    @RequestMapping("/registerPage")
//    public String registerPage(Model model) {
//        String rePassword = "";
//        model.addAttribute("user", new User());
//        model.addAttribute("rePassword", rePassword);
//        return "register";
//    }
//
//    @PostMapping(value = "/register")
//    public String checkRegister(@Valid User user, BindingResult bindingResult, @RequestParam("rePassword") String rePassword, HttpSession session) {
//        if(userRepository.existsByUserId(user.getUserId())) {
//            bindingResult.addError(new FieldError(
//                    "user","userId","This User ID already has in system"));
//        }
//        if(userRepository.existsByEmail(user.getEmail())) {
//            bindingResult.addError(new FieldError(
//                    "user","email","This Email already has in system"));
//        }
//        if(userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
//            bindingResult.addError(new FieldError(
//                    "user","phoneNumber","This Phone number already has in system"));
//        }
//        if(!rePassword.equals(user.getPassword())) {
//            bindingResult.addError(new FieldError(
//                    "user", "password", "The re-enter password don't match the password"));
//        }
//        if(!user.isOver18()) {
//            bindingResult.addError(new FieldError(
//                    "user","birthDate", "You must more than 18 years old"));
//        }
//        logger.info(user.getBirthDate());
//        session.setAttribute("user", user);
//
//        if(bindingResult.hasErrors()) {
//            return "register";
//        }
//        return "redirect:send";
//    }
//    @GetMapping(value = "/send")
//    public String sendMessage(HttpSession session) {
//        if(twilioConfig == null || twilioConfig.getTrialNumber() == null) {
//            return "redirect:/registerPage";
//        }
//        User user = (User) session.getAttribute("user");
//        if(user == null) {
//            return "redirect:/registerPage";
//        }
//        otpService.sendOTPForPasswordReset(user.getPhoneNumber());
//
//        session.setAttribute("user", user);
//        return "otpForRegisterPage";
//    }
//    @PostMapping(value = "/checkOTPForRegister")
//    public String checkOTPForRegister(@RequestParam("enteredOTP") String enteredOTP, HttpSession session, Model model) {
//        if(otpService.verifyOTP(enteredOTP)) {
//            User user = (User) session.getAttribute("user");
//            if(user == null) {
//                return "otpForRegisterPage";
//            }
//            user.setRole(Role.CUSTOMER);
//            user.setPassword(passwordEncoder.encode(user.getPassword()));
//            userRepository.save(user);
//            session.invalidate();
//            return "home";
//        } else {
//            model.addAttribute("error", "Invalid OTP. Please try again");
//            return "otpForRegisterPage";
//        }
//    }
//    @GetMapping("/showProfile")
//    public String showProfile(@RequestParam("username") String username, Model model) {
//        if(username == null || username.isEmpty()) {
//            model.addAttribute("error", "Some error when load the User");
//            return "home";
//        }
//        else {
//            User user = userRepository.findByUserIdOrEmailOrPhoneNumber(username, username, username)
//                    .orElseThrow(() -> new UsernameNotFoundException("User can't found "));
//            model.addAttribute("user", user);
//            return "manageProfile";
//        }
//    }
//    @PostMapping("/saveInfo")
//    public String modProfile(User user, RedirectAttributes redirectAttributes, BindingResult bindingResult, Model model) {
//        if(!user.isOver18()) {
//            bindingResult.addError(new FieldError(
//                    "user","birthDate", "You must more than 18 years old"));
//        }
//        if(bindingResult.hasErrors()) {
//            return "manageProfile";
//        }
//        userRepository.setUserInfoById(user.getFirstName(), user.getLastName(),
//                user.getBirthDate(), user.getProvince(), user.getDistrict(), user.getWard(), user.getStreetAddress(), user.getUserId());
//        model.addAttribute("successMessage", "Cập nhập hồ sơ thành công");
//        return "manageProfile";
//    }
//    @PostMapping("/changePasswordPage")
//    public String showChangePasswordPage(User user, Model model) {
//        ChangePasswordDTO dto = new ChangePasswordDTO();
//        dto.setUserId(user.getUserId());
//        model.addAttribute("dto", dto);
//        return "changePassPage";
//    }
//
//    @PostMapping("/changePassword")
//    public String verifyChangePassword(@ModelAttribute("dto") ChangePasswordDTO dto, HttpSession session, BindingResult bindingResults) {
//        User user = userRepository.findByUserId(dto.getUserId())
//                .orElseThrow(() -> new UsernameNotFoundException("User can't found "));
//
//
//            if(!passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
//                bindingResults.addError(new FieldError(
//                        "dto", "oldPassword", "The old password not true"));
//            }
//            if(!dto.getNewPassword().equals(dto.getReNewPassword())) {
//                bindingResults.addError(new FieldError("dto", "reNewPassword",
//                        "The re-password do not match"));
//            }
//            if(bindingResults.hasErrors()) {
//                return "changePassPage";
//            }
//            session.setAttribute("dto", dto);
//            session.setAttribute("user", user);
//            return "redirect:/sendOTPToChangePassword";
//    }
//    @GetMapping("/sendOTPToChangePassword")
//    public String sendOTPChangePassword(HttpSession session) {
//        if(twilioConfig.getTrialNumber() == null || twilioConfig.getAccountSid() == null || twilioConfig.getAccountSid() == null ) {
//            return "changePassPage";
//        }
//        else {
//            User user = (User) session.getAttribute("user");
//            ChangePasswordDTO dto = (ChangePasswordDTO) session.getAttribute("dto");
//            if(user != null) {
//                otpService.sendOTPForPasswordReset(user.getPhoneNumber());
//            }
//            session.setAttribute("user", user);
//            session.setAttribute("dto", dto);
//        }
//        return "otpForPasswordResetPage";
//    }
//    @PostMapping("/checkOTPForPasswordReset")
//    public String checkOTPForPasswordRest(@RequestParam("enteredOTP") String enteredOTP, HttpSession session, Model model) {
//        if(otpService.verifyOTP(enteredOTP)) {
//            User user = (User) session.getAttribute("user");
//            ChangePasswordDTO dto = (ChangePasswordDTO) session.getAttribute("dto");
//            if(user == null) {
//                return "otpForPasswordResetPage";
//            }
//            user.setPassword(passwordEncoder.encode(dto.getNewPassword()));
//            userRepository.setPasswordById(user.getPassword(), user.getUserId());
//            session.invalidate();
//            return "home";
//        } else {
//            model.addAttribute("error", "Invalid OTP. Please try again");
//            return "otpForPasswordResetPage";
//        }
//    }
//}
//
