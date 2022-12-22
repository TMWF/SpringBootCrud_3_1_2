package com.example.springbootcrud.controller;

import com.example.springbootcrud.model.User;
import com.example.springbootcrud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/")
public class UsersController {
    private final UserService userService;


    public UsersController(UserService userService) {
        this.userService = userService;
        addUsers();
    }

    private void addUsers() {
        userService.saveUser(new User("Leo", "Bonhart","lb@mail.ru"));
        userService.saveUser(new User("Иван", "Сидоров","isid@mail.ru"));
        userService.saveUser(new User("Henry", "Cavill","hcav@mail.ru"));
        userService.saveUser(new User("Maша", "Иванова","mi@mail.ru"));
        userService.saveUser(new User("Tom", "Cat","t@mail.ru"));
    }
    @GetMapping
    public String show(Model model) {
        return getUsersView(model);
    }

    @PostMapping
    public String show2(Model model) {
        return getUsersView(model);
    }

    private String getUsersView(Model model) {
        model.addAttribute("message", "Список пользователей");
        model.addAttribute("url", "users");
        return "index";
    }



    @GetMapping("/users/new")
    public String createForm(@ModelAttribute("user") User user) {
        return "new";
    }

    @PostMapping ("users")
    public String create(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:users";
    }

    @GetMapping("/users")
    public String index(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all";
    }

    @GetMapping("users/{id}")
    public String read(Model model, @PathVariable(name = "id") long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "show";
    }

    @GetMapping("users/{id}/edit")
    public String editForm(Model model, @PathVariable() long id) {
        model.addAttribute("user", userService.getUserById(id));
        return "edit";
    }

    @PostMapping("users/{id}")
    public String edit(@ModelAttribute("user") User user, @PathVariable("id") long id) {
        userService.saveUser(user);
        return "redirect:/users";
    }


    @DeleteMapping("users/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }
}
