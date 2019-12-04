package com.fly.blog.controller;

import com.fly.blog.service.BlogService;
import com.fly.blog.service.TagService;
import com.fly.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;

    @GetMapping({"/","/blogs"})
    public String index(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("blogList",blogService.listBlog(pageable));
        model.addAttribute("tagList",tagService.findTop(6));
        model.addAttribute("typeList",typeService.findTop(6));
        model.addAttribute("recommendBlogList",blogService.listRecommendBlog(6));
        return "/index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 2, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         Model model, String query) {
        Page page = blogService.listBlog(pageable, query);
        model.addAttribute("blogList",page);
        model.addAttribute("query",query);
        return "search::searchBlogList";
    }

    @RequestMapping("/about")
    public String about() {
        return "about";
    }

}
