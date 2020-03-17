package by.shurikpreproject.springboottask5.controller;


import by.shurikpreproject.springboottask5.dao.UserDao;
import by.shurikpreproject.springboottask5.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class UserController {
    private UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping({"/welcome", "/"})
    public String getWelcome(@RequestParam(name = "message", required = false, defaultValue = "Aloha") String message, Model model) {
        model.addAttribute("message", message);
        model.addAttribute("greeting", "Hola, ");
        model.addAttribute("they", "Amigos");
        model.addAttribute("users", userDao.findAll());
        return "welcome";
    }

    @PostMapping ("filter")
    public String filter (@RequestParam String filter, Map <String, Object> model){//задаем Имя по кот. будем фильтровать
                Iterable<User> messages;//куда вставлять
        if (filter != null && !filter.isEmpty()) {
            messages = userDao.findByUserName(filter);
        } else {
            messages = userDao.findAll();
        }
        model.put("messages", messages);
        return "welcome";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLogin(Model model) {
        return "login";
    }

    @GetMapping({"/403"})
    public String getBadRequest() {
        return "403";
    }

    @GetMapping("/signUp")//регимся
    public String showSignUpForm(User user) {
        return "addUser";//добавляемся
    }

    @PostMapping("/addUser")//перехватываем, проверяем
    public String addUser(@Valid User user, BindingResult result, Model model) {
        if (result.hasErrors()) {// если не прошел Valid - заново
            return "addUser";
        }
        userDao.save(user);
        model.addAttribute("users", userDao.findAll());
        return "welcome";
    }

//    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
//    public String addUser(@ModelAttribute("user") User user) {
//        userDao.save(user);
//
//        return "redirect:/";
//    }


    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        return "updateUser";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid User user,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setId(id);
            return "updateUser";
        }

        userDao.save(user);
        model.addAttribute("users", userDao.findAll());
        return "welcome";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        User user = userDao.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userDao.delete(user);
        model.addAttribute("users", userDao.findAll());
        return "welcome";
    }

}
