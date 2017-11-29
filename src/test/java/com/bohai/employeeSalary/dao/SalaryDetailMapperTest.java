package com.bohai.employeeSalary.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations = "classpath:applicationContextTest.xml")
public class SalaryDetailMapperTest extends AbstractJUnit4SpringContextTests{
    
    public static SqlSessionFactory sqlSessionFactory;

    @Before
    public void init(){
        String resource = "mybatis/mybatis-config.xml";
        if(sqlSessionFactory ==null){
            try {
                InputStream inputStream = Resources.getResourceAsStream(resource);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
                
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    
    
    @Test
    public void callInterestReportTest(){
        SqlSession session = sqlSessionFactory.openSession();
        
        SalaryDetailMapper mapper = session.getMapper(SalaryDetailMapper.class);
         
        Map<String, String> map = new HashMap<String, String>();
        map.put("IN_STAFF_NUMBER", "01160611");
        map.put("IN_PAY_MONTH", "2017-05");
        
        mapper.calcSalary(map);
        System.out.println(map.get("OUT_SALARY"));
        
        session.close();
    }
    
    

}
