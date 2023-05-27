package com.ldm.teststarter;

import com.ldm.hello.service.OssTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@SpringBootTest
public class OssTest {
    @Autowired
    private OssTemplate ossTemplate;

    @Test
    void contextLoads() {
        ossTemplate.createBucket("oss02");
        System.out.println("hello");
    }
}
