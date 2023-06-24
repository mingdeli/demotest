package com.ldm.testplugin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ldm.testplugin.po.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<Student> {
}
