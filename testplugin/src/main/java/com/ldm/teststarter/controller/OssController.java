package com.ldm.teststarter.controller;

import com.ldm.hello.service.OssTemplate1;
import com.ldm.hello.service.OssTemplaterm;
import com.netease.ncc.unus.changelog.core.annotation.MarkChangeLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("oss")
public class OssController {

//        @Autowired
//        private OssTemplaterm ossTemplate;
    @Autowired
    private OssTemplate1 ossTemplate;

//    @Autowired
//    private OssTemplaterm osstemp;

    @PostMapping("/upload")
    @MarkChangeLog
    public String upload(MultipartFile file) throws Exception {
        ossTemplate.putObject("oss02", file.getOriginalFilename(), file.getInputStream(), file.getContentType());
        return "ok()";
    }

    @GetMapping("/createBucket")
    void contextLoads() {
        ossTemplate.createBucket("oss02");
        System.out.println("hello");
    }

}
