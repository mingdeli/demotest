package com.ldm.teststarter.config;

import com.ldm.teststarter.po.Pet;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 绑定配置文件类
 */
@Component
@ConfigurationProperties("mycar.df") //只有在容器中，才能生效； 或者使用@EnableConfigurationProperties(CarProperties.class)开启
@Data
public class CarProperties {

    private String url;
    private int port;
    private String name;
    private List list;

}
