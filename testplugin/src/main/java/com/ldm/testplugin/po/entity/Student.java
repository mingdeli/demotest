package com.ldm.testplugin.po.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.ldm.testplugin.annotation.EncryptField;
import com.ldm.testplugin.annotation.EncryptTable;
import lombok.Data;
import org.joda.time.DateTime;

import java.util.List;

@Data
@EncryptTable
@TableName(value = "student", autoResultMap = true)
public class Student extends BaseEntity {

    @TableField("name")
    private String name;

    @TableField("class_id")
    private Integer classId;

    @TableField("birthday")
    private String birthday;

    @TableField("email")
    @EncryptField
    private String email;

    @TableField(value = "courses",typeHandler = JacksonTypeHandler.class)
    private List<Integer> courses;

}
