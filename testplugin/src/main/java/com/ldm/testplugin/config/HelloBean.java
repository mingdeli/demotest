package com.ldm.testplugin.config;

import com.ldm.hello.service.Helloservice;
import com.ldm.testplugin.po.MyUser;
import com.ldm.testplugin.po.Pet;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

/**
 * @Import 导入bean组件
 * @ImportResource 导入spring的配置文件，使之生效
 */
@Configuration
@Import({MyUser.class, Pet.class})
@ImportResource("classpath:beans.xml")
//@EnableConfigurationProperties(CarProperties.class)
public class HelloBean {

    @Bean
    public Helloservice helloservice(){
        Helloservice helloservice = new Helloservice();
        helloservice.setName("myApplication");
        return helloservice;
    }
}
