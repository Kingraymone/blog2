package top.king.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("top.king")
@MapperScan(basePackages={"top.king.mapper"})
public class Blog2Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Blog2Application.class, args);
    }

}
