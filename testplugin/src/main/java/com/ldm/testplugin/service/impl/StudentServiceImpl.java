package com.ldm.testplugin.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ldm.testplugin.mapper.StudentMapper;
import com.ldm.testplugin.po.entity.Student;
import com.ldm.testplugin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    @Transactional
    public List<Student> add( Integer i ) throws Exception {
        return add1(i);
    }

    public List<Student> add1( Integer i ) throws Exception {
        Student student = new Student();
        student.setName("卫青"+i);
        student.setClassId(32);
        student.setBirthday(DateUtil.today());
        student.setCourses(List.of(1,4,5,8));
        student.setEmail("555667709@qq.com");
        this.save(student);
        if (i == 10){
            int p=1/0;
        }
        System.out.println("=====================1==========");
        Long id = redisTemplate.opsForValue().increment("conut",1);
        if(id ==null){
            throw new Exception("id is null,为空了");
        }

        System.out.println("=====================2==========");
        Student student1 = new Student();
        student1.setName("霍去病"+i);
        student1.setClassId(id.intValue());
        student1.setBirthday(DateUtil.today());
        this.save(student1);
        if(i == 20){
            int p=1/0;
        }
        return List.of(student, student1);
    }


}
