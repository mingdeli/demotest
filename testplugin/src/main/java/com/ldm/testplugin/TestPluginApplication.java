package com.ldm.testplugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class TestPluginApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(TestPluginApplication.class, args);

        Object bean = run.getBean("com.ldm.testplugin.po.MyUser");
        System.out.println(bean);

    }

}
