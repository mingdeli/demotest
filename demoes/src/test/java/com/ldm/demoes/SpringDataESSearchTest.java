package com.ldm.demoes;

import com.ldm.demoes.Po.Product;
import com.ldm.demoes.dao.ProductDao;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDataESSearchTest {
    @Autowired
    private ProductDao productDao;
//
//    /**
//     * term查询
//     * search(termQueryBuilder) 调用搜索方法，参数查询构建器对象
//     */
//    @Test
//    public void termQuery(){
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "小米");
//        Iterable<Product> products = productDao.searchSimilar(new Product(),null,null);
//        for (Product product : products) {
//            System.out.println(product);
//        }
//    }
//
//    /**
//     * term查询加分页
//     */
//    @Test
//    public void termQueryByPage(){
//        int currentPage= 0 ;
//        int pageSize = 5;
//        //设置查询分页
//        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", "小米");
//        Product product1 = new Product();
//        product1.setId(1L);
//        Iterable<Product> products = productDao.searchSimilar(product1,["title"], pageRequest);
//        for (Product product : products) {
//            System.out.println(product);
//        }
//    }

}