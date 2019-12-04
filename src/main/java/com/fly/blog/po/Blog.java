package com.fly.blog.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客类
 */
@Entity
@Table(name="tb_blog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    /** 博客id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer blogId;
    /** 标题. */
    @NotEmpty(message = "标题不能为空!")
    private String title;
    @NotEmpty(message = "描述不能为空!")
    private String description;
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String content;
    /** 博客首图 */
    private String headImage;
    /** 原创, 转载 或者 翻译. */
    @NotNull(message = "该选项不能为空!")
    private String flag;
    /** 浏览量. */
    private Integer views;
    /** 是否可赞赏. */
    private boolean appreciation;
    /** 是否发布. */
    private boolean published;
    /** 是否可评论. */
    private boolean commentable;
    /** 是否可转载. */
    private boolean shareStatement;
    /** 是否推荐. */
    private boolean recommend;
    /** 创建时间. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    /** 更新时间. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToOne
    private User user;
    /** 博客标签列表. */
    @ManyToMany(targetEntity = Tag.class,cascade = CascadeType.PERSIST)
    private List<Tag> tags = new ArrayList<>();
    /** 博客类型. */
    @ManyToOne
    private Type type;
    /** 博客评论. */
    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

}
