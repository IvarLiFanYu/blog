package com.fly.blog.controller.admin;

import com.fly.blog.enums.ResultEnums;
import com.fly.blog.exception.ExceptionCast;
import com.fly.blog.po.Blog;
import com.fly.blog.po.Tag;
import com.fly.blog.po.Type;
import com.fly.blog.po.User;
import com.fly.blog.request.QueryRequest;
import com.fly.blog.service.BlogService;
import com.fly.blog.service.TagService;
import com.fly.blog.service.TypeService;
import com.fly.blog.util.StringArrayConvertor;
import com.fly.blog.vo.BlogVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Controller
@Slf4j
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagService tagService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 5,sort = {"updateTime"},direction= Sort.Direction.DESC) Pageable pageable,
                        QueryRequest blog, Model model) {

        Page<Blog> page = blogService.listBlog(pageable, blog);
        //查询标签列表
        List<Tag> tagList = tagService.findAll();
        //查询分类信息
        List<Type> typeList = typeService.findAll();
        model.addAttribute("page",page);
        model.addAttribute("tagList",tagList);
        model.addAttribute("typeList",typeList);
        return "/admin/blogs";
    }

    /**
     * 根据条件查询局部刷新
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 5,sort = {"updateTime"},direction= Sort.Direction.DESC) Pageable pageable,
                         QueryRequest blog, Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @RequestMapping("/blogs/input")
    public String saveInput(Model model) {
        Blog blog = new Blog();
        List<Type> types = typeService.findAll();
        model.addAttribute("typeList",types);
        List<Tag> tags = tagService.findAll();
        model.addAttribute("tagList",tags);
        model.addAttribute("blog",blog);
        return "/admin/blogs-input";
    }

    @RequestMapping("/blogs/input/{id}")
    public String editInput(@PathVariable("id") Integer blogId, Model model) {
        Blog blog = blogService.findOne(blogId);
        List<Tag> tags = blog.getTags();
        List<Integer> tagIdList = new ArrayList<>();
        tags.forEach(tag->tagIdList.add(tag.getTagId()));
        String ids = StringArrayConvertor.list2string(tagIdList);
        List<Type> types = typeService.findAll();
        List<Tag> tagList = tagService.findAll();
        model.addAttribute("typeList",types);
        model.addAttribute("tagList",tagList);
        model.addAttribute("ids",ids);
        model.addAttribute("blog",blog);
        return "/admin/blogs-input";
    }

    @PostMapping("/blogs")
    public String save(@Valid BlogVO blogVO, BindingResult bindingResult, HttpSession session, RedirectAttributes attributes) {

        if (bindingResult.hasErrors()) {
            log.error("非自定义异常,message={}",bindingResult.getAllErrors());
            return "/admin/blogs-input";
        }

        if (session.getAttribute("user") == null) {
            ExceptionCast.cast(ResultEnums.USER_NOT_LOGIN);
            return "/admin/blogs-input";
        }

        Blog blog = new Blog();

        BeanUtils.copyProperties(blogVO,blog);

        if ("true".equals(blogVO.getPublished())) {
            blog.setPublished(true);
        } else if ("false".equals(blogVO.getPublished())) {
            blog.setPublished(false);
        }

        List<String> listTagId = StringArrayConvertor.string2list(blogVO.getTagIds());
        List<Tag> tagList = new ArrayList<>();
        if (listTagId != null) {
            listTagId.forEach(item->tagList.add(tagService.findById(Integer.parseInt(item))));
        }
        blog.setTags(tagList);
        blog.setType(typeService.getById(blogVO.getTypeId()));
        blog.setUser((User) session.getAttribute("user"));

        Blog save = blogService.save(blog);
        if (save == null) {
            attributes.addFlashAttribute("message","保存失败!");
        } else {
            attributes.addFlashAttribute("message","保存成功!");
        }
        return "redirect:/admin/blogs";
    }

    @RequestMapping("/blogs/delete/{id}")
    public String delete(@PathVariable("id")Integer blogId) {
        blogService.delete(blogId);
        return "redirect:/admin/blogs";
    }

}
