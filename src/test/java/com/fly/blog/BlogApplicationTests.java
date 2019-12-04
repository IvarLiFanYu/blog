package com.fly.blog;

import com.fly.blog.po.Comment;
import com.fly.blog.service.CommentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BlogApplicationTests {

    @Autowired
    private CommentService commentService;

    @Test
    public void contextLoads() {
        List<Comment> comment = commentService.findCommentByBlogId(14);
        System.out.println(comment);
    }

}
