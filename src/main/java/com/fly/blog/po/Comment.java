package com.fly.blog.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 评论类
 */
@Entity
@Table(name="tb_comment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer commentId;
    private String nickName;
    private String email;
    private String content;
    private String avatar;
    private boolean adminComment;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne
    private Blog blog;

    @ManyToOne
    private Comment commentParent;
    @OneToMany(mappedBy = "commentParent")
    private List<Comment> commentChild = new ArrayList<>();

}
