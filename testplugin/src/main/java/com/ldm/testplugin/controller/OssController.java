package com.ldm.testplugin.controller;

import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.ldm.hello.service.OwsTemplate;
/* import com.netease.ncc.unus.changelog.core.annotation.MarkChangeLog; */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("oss")
public class OssController {

    @Autowired
    private OwsTemplate ossTemplate;

    @PostMapping("/upload")
//    @MarkChangeLog
    public String upload(MultipartFile file) throws Exception {
        ossTemplate.putObject("oss02", file.getOriginalFilename(), file.getInputStream(), file.getContentType());
        return String.format("upload ok，fileName："+ file.getOriginalFilename());
    }

    @GetMapping("/createBucket")
    public String createBucket() {
        ossTemplate.createBucket("oss02");
        return "ok";
    }

    @GetMapping("/getFileUrl")
    public String getFileUrl(@RequestParam(defaultValue = "342.png") String fileName) {
        String fileUrl = ossTemplate.getObjectURL("oss02", fileName, 7);
        return fileUrl;
    }

}
