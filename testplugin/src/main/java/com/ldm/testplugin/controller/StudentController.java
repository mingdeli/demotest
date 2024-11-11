package com.ldm.testplugin.controller;

import com.ldm.testplugin.common.R;
import com.ldm.testplugin.po.entity.Student;
import com.ldm.testplugin.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public R<Student> info(@PathVariable("id") Integer id) {
        Student student = studentService.getById(id);
        return R.ok(student);
    }

    @DeleteMapping("/{id}")
    public R deleteInfo(@PathVariable("id") Integer id) {
        boolean b = studentService.removeById(id);
        if (b) return R.ok();
        else return R.fail();
    }

    @GetMapping("/list")
    public R list() {
        List<Student> list = studentService.list();
        return R.ok(list);
    }

    @GetMapping("/add")
    public R add(@RequestParam("i") int i) throws Exception {
        List<Student> list = studentService.add(i);
        return R.ok(list);
    }
}
