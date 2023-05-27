package com.ldm.demoes.controller;

import com.alibaba.fastjson.JSON;
import com.ldm.demoes.Po.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("es")
public class EsDemo {

    // 面向对象来操作
    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient highLevelClient;

    /**
     * 生成单元测试
     */
    @GetMapping("/info1")
    public Map esInfo1() throws IOException {

        int total;
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

// 分页参数
        searchSourceBuilder.from(1);
        searchSourceBuilder.size(10);
        searchSourceBuilder.sort("_id", SortOrder.DESC);

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery() // 精确匹配

                .must(QueryBuilders.termQuery("field1", "value1")) // 关键字模糊+分词匹配

                .must(QueryBuilders.multiMatchQuery("keyword", "field1", "field2", "field3", "field4", "field5"));
        searchSourceBuilder.query(boolQueryBuilder);

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = highLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        if (Objects.nonNull(searchResponse)) {
            TotalHits totalHits = searchResponse.getHits().getTotalHits();

            SearchHits searchHits = searchResponse.getHits();
            for (int i = 0; i < searchHits.getHits().length; i++) {
                String str = searchHits.getHits()[i].getSourceAsString();
            }
        }
        return null;
    }

    @GetMapping("/info")
    public Map esInfo() throws IOException {
        System.out.println("这是 /info");
        Map<String, Object> map = new HashMap<>();
        // 1、创建索引请求
        CreateIndexRequest request1 = new CreateIndexRequest("kuang_index");
        // 2、客户端执行请求 IndicesClient,请求后获得响应
        CreateIndexResponse createIndexResponse = highLevelClient.indices().create(request1, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
        // 创建对象
        User user = new User("狂神说", 3);
        // 创建请求
        IndexRequest request = new IndexRequest("kuang_index");
        // 规则 put /kuang_index/_doc/1
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        // 将我们的数据放入请求 json
        request.source(JSON.toJSONString(user), XContentType.JSON);
        // 客户端发送请求 , 获取响应的结果
        IndexResponse indexResponse = highLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString()); //

        map.put("es", "abc");
        map.put("es1", indexResponse.toString());
        return map;
    }
}
