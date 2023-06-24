package com.ldm.testplugin.po.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "student", autoResultMap = true)
public class Student extends BaseEntity {

    @TableField("name")
    private String name;

    @TableField("class_id")
    private Integer classId;

    @TableField("birthday")
    private String birthday;

    @TableField("email")
    private String email;

    @TableField(value = "courses",typeHandler = JacksonTypeHandler.class)
    private List<String> courses;

}
