package com.fly.blog.controller.admin;

import com.fly.blog.po.User;
import com.fly.blog.service.UserService;
import com.fly.blog.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.security.provider.MD5;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String log() {
        return "/admin/login";
    }

    @PostMapping("/login")
    public String login (@RequestParam String username,
                         @RequestParam String password,
                         HttpSession session,
                         RedirectAttributes attributes) {
        User user = userService.checkUser(username, MD5Utils.code(password));
        if (user != null) {
            session.setAttribute("user",user);
            return "/admin/index";
        } else {
            log.warn("用户名或者密码错误:{}:{}",username,password);
            //添加重定向属性
            attributes.addFlashAttribute("message","用户名或密码错误");
            return "redirect:/admin";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
