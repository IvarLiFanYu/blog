package com.fly.blog.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

/**
 * 标签类
 */
@Entity
@Table(name="tb_tag")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer tagId;
    @NotEmpty(message = "标签名字不能为空!")
    private String tagName;

    @ManyToMany(mappedBy = "tags")
    private List<Blog> blogs = new ArrayList<>();


}
