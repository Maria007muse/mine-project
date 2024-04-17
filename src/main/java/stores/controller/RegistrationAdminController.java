package stores.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import stores.dto.AdminRegistrationDto;
import stores.service.UserService;

@Controller
@RequestMapping("/registration/admin")
public class RegistrationAdminController {

    private UserService userService;

    public RegistrationAdminController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("admin")
    public AdminRegistrationDto adminRegistrationDto() {
        return new AdminRegistrationDto();
    }

    @GetMapping
    public String showAdminRegistrationForm() {
        return "registration_admin";
    }

    @PostMapping
    public String saveAdmin(@ModelAttribute("admin") @Valid AdminRegistrationDto adminDto, BindingResult result) {
        if (result.hasErrors()) {
            return "registration";
        }
        try {
            userService.saveAdmin(adminDto);
        } catch (Exception e) {
            System.out.println(e);
            return "redirect:/registration/admin?error";
        }
        return "redirect:/login";
    }
}


