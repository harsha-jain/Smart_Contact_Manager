package com.scm.sscm20.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.sscm20.entity.User;
import com.scm.sscm20.forms.UserForm;
import com.scm.sscm20.helpers.Message;
import com.scm.sscm20.helpers.MessageType;
import com.scm.sscm20.services.UserService;

import jakarta.servlet.http.HttpSession;


@Controller
public class PageController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index() {
        return "redirect:/home";
    }

  
    @RequestMapping("/home")
    public String home(org.springframework.ui.Model model) {
        System.out.println("home page handler");
        model.addAttribute("name", "Substirng technology");
        model.addAttribute("Harsha_Jain", "Smart contact manager");
        model.addAttribute("Github_Reposiory", "https://github.com/harsha-jain");
        return "home";
    }

    @RequestMapping("/about")
    public String about(Model model) {
        model.addAttribute("isLogin", model);
        System.out.println("About page loading");
        return "about";
    }
    @RequestMapping("/services")
    public String servicesPage() {
        System.out.println("Services Page loading");
        return "services";
    }

    @GetMapping("/contact")
    public String contact() {
        return new String("contact");
    }

    @GetMapping("/login")
    public String login() {
        return new String("login");
    }
    
    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm = new UserForm();
        // default data bhi daal sakte hai
        // userForm.setName("Durgesh");
        // userForm.setAbout("This is about : Write something about yourself");
        model.addAttribute("userForm", userForm);

        return "register";
    }
    
     @RequestMapping(value = "/do-register", method = RequestMethod.POST)
    public String processRegister(@ModelAttribute UserForm userForm,BindingResult bindingResult, HttpSession httpSession) {
        System.out.println("Processing registration");
        // fetch form data
        // UserForm
        System.out.println(userForm);

        // validate form data
        // TODO::Validate userForm[Next Video]

        if (bindingResult.hasErrors()) {
            return "register";
        }
        // save to database

        // userservice

        // UserForm--> User
       /*  User user = User.builder()
              .name(userForm.getName())
              .email(userForm.getEmail())
              .password(userForm.getPassword())
              .about(userForm.getAbout())
              .phoneNumber(userForm.getPhoneNumber())
              .profilePic(
                      "https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75")
              .build();*/
                
       User user = new User();
       user.setName(userForm.getName());
       user.setEmail(userForm.getEmail());
       user.setPassword(userForm.getPassword());
       user.setAbout(userForm.getAbout());
       user.setPhoneNumber(userForm.getPhoneNumber());
       user.setEnabled(false);
       user.setProfilePic(
        "https://www.learncodewithdurgesh.com/_next/image?url=%2F_next%2Fstatic%2Fmedia%2Fdurgesh_sir.35c6cb78.webp&w=1920&q=75"
       ); 




        User savedUser = userService.saveUser(user);

        System.out.println("user saved :");

        // message = "Registration Successful"

        // add the message:

        Message message = Message.builder().content("Registration Successful").type(MessageType.green).build();

        httpSession.setAttribute("message", message);

        // redirectto login page
        return "redirect:/register";
    }
    
}

