package com.fly.blog.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客类
 */
@Entity
@Table(name="tb_blog")
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer blogId;
    private String title;
    private String headImage;
    private String flag;
    private Integer views;
    private boolean appreciation;
    private boolean published;
    private boolean commentable;
    private boolean shareStatement;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private List<Tag> tags = new ArrayList<>();

    @ManyToOne
    private Type type;

    @OneToMany(mappedBy = "blog")
    private List<Comment> comments = new ArrayList<>();

}
