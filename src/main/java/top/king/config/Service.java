package top.king.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
@ConfigurationProperties("test")
@Data
public class Service {
    public int i = 10;
    public String king = "";
    public String password = "";
    @EventListener
    public void test(ContextRefreshedEvent event) {
        System.out.println("IOC容器刷新完成通知！" + i++);
        if (Optional.ofNullable(event).isPresent()) {

        }
        if (Objects.nonNull(event)) {

        }
        // StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        // Arrays.stream(stackTrace).forEach(System.out::println);
    }
}
