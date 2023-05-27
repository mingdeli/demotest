package com.ldm.demoes.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;


@Configuration
public class ElasticSearchClientConfig {

//    @Bean
//    // 硬编码的值可以设置到配置文件，通过@Value读取
//    public RestHighLevelClient restHighLevelClient() {
//        RestHighLevelClient client = new RestHighLevelClient(
//                RestClient.builder(
//                        new HttpHost("192.168.202.133", 9200, "http")
//                )
//        );
//        return client;
//    }


    // client的方式
    private static final int PORT = 9200;
    private static final String IP1 = "192.168.202.133";
    private static final String IP2 = "192.168.202.134";
    private static final String IP3 = "192.168.202.135";
    private static final String SecurityUser = "";
    private static final String SecurityPassword = "";
    private static final String clusterName = "es-cluster";


    private static CredentialsProvider init() {
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials(SecurityUser, SecurityPassword));
        return credentialsProvider;
    }

    @Bean
    public RestClientBuilder restClientBuilder() throws UnknownHostException {
        //配置权限验证（输入用户密码）
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost(IP1, PORT, "http"),
                new HttpHost(IP2, PORT, "http"),
                new HttpHost(IP3, PORT, "http"))
                .setHttpClientConfigCallback(httpClientBuilder ->
                        httpClientBuilder.setDefaultCredentialsProvider(init()));

        return restClientBuilder;
    }


    @Bean(name = "restHighLevelClient")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {
        return new RestHighLevelClient(restClientBuilder);
    }


}
