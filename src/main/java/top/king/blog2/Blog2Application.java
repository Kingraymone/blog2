package top.king.blog2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan("top.king")
public class Blog2Application {

    @Bean
    public String test2() {
        return "king";
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Blog2Application.class, args);
        Service service = run.getBean("service", Service.class);
        System.out.println(service.i + " " + service.king + " " + service.password);
    }

}
