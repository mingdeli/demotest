package com.ldm.testplugin.po.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Locale;

@Data
public class BaseEntity {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    @TableField(fill = FieldFill.INSERT)//插入时填充
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)//插入和更新时填充
    private LocalDateTime updateTime;
    @TableLogic
    private Integer deleted;

}
