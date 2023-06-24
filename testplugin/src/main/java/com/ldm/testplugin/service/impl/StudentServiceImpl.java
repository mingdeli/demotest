package com.ldm.testplugin.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldm.testplugin.mapper.StudentMapper;
import com.ldm.testplugin.po.entity.Student;
import com.ldm.testplugin.service.StudentService;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
