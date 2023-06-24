package com.ldm.testplugin;

import com.ldm.hello.service.OwsTemplate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@RestController
@SpringBootTest
public class OssTest {
    @Autowired
    private OwsTemplate ossTemplate;

    @Test
    void contextLoads() {
        ossTemplate.createBucket("oss02");
        System.out.println("hello");
    }
}
