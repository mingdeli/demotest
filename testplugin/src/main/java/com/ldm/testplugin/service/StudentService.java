package com.ldm.testplugin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ldm.testplugin.po.entity.Student;

import java.util.List;

public interface StudentService extends IService<Student> {
    List<Student> add(Integer i) throws Exception;
}
