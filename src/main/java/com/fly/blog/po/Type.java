package com.fly.blog.po;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tb_type")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Integer typeId;

    @NotEmpty(message = "分类名称不能为空!")
    private String typeName;

    @OneToMany(mappedBy = "type")
    private List<Blog> blogs = new ArrayList<>();

}
