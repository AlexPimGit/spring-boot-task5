package by.shurikpreproject.springboottask5.controller;


import by.shurikpreproject.springboottask5.model.User;
import by.shurikpreproject.springboottask5.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin/welcome")
    public String getWelcome(@RequestParam(name = "message", required = false, defaultValue = "Aloha") String message, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("greeting", "Hola, ");
        model.addAttribute("they", "Amigos");
        model.addAttribute("users", userService.listUser());
        return "welcome";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {//задаем Имя по кот. будем фильтровать
        List messages;//куда вставлять
        if (filter != null && !filter.isEmpty()) {
            messages = userService.findListByUsername(filter);
        } else {
            messages = userService.listUser();
        }
        model.put("messages", messages);
        return "welcome";
    }

    @GetMapping("/admin/addUser")
    public String showAddForm(User user) {
        return "addUser";
    }

    @PostMapping("/admin/addUser")//перехватываем, проверяем
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {// если не прошел Valid - заново
            return "addUser";
        }
        userService.addUser(user);
        model.addAttribute("users", userService.listUser());
        return "welcome";
    }

    @GetMapping("/admin/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "updateUser";
    }

    @PostMapping("/admin/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "updateUser";
        }

        userService.addUser(user);
        model.addAttribute("users", userService.listUser());
        return "welcome";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        userService.removeUser(id);
        model.addAttribute("users", userService.listUser());
        return "welcome";
    }

}
