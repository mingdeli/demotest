package com.ldm.teststarter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class TeststarterApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TeststarterApplication.class, args);
        Object user01 = run.getBean("user01");
        System.out.println(user01);

        Object bean = run.getBean("com.ldm.teststarter.po.MyUser");
        System.out.println(bean);

    }

}
