package com.lkc.www.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class MybatisConfig {
    @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();

        Properties properties = new Properties();

        //设置true，会将RowBounds第一个参数offset当成pageNum页码使用
        properties.setProperty("offsetAsPageNum","true");

        //设置true，使用RowBounds分布会进行count查询
        properties.setProperty("rowBoundsWithCount","true");

        properties.setProperty("reasonable","true");

        pageHelper.setProperties(properties);

        return pageHelper;
    }
}
