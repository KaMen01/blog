package com.duellinkes.blog;

import com.duellinkes.blog.config.AuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.duellinkes.blog.mapper" )
@EnableConfigurationProperties(AuthProperties.class)
public class BlogApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }
}
