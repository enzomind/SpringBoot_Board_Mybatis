package com.board.board;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BoardMybatisApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SqlSessionFactory sessionFactory;

    @Test
    void contextLoads() {
    }

    @Test
    public void testByApplicationContext(){
        try{
            System.out.println("========================");
            System.out.println(context.getBean("sqlSessionFactory"));
            System.out.println("========================");
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

}
