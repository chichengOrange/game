package com.cc;

import com.cc.common.Utils.YmlUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement //如果mybatis中service实现类中需要加入事务注解，需要此处添加该注解
@MapperScan(value = "com.cc.mapper")

@Import({YmlUtil.class})//,DynamicDataSourceRegister.class


public class Application extends SpringBootServletInitializer {

   //TODO
    /**
     * 打war包专用  war包打开  启动类继承 extends SpringBootServletInitializer
     **/
   @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
       return application.sources(Application.class);
   }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
