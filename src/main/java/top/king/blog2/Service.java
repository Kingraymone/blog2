package top.king.blog2;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.NestedExceptionUtils;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@ConfigurationProperties("test")
@Data
public class Service {
    public int i = 10;
    public String king = "";
    public String password = "";
    @EventListener
    public void test(ContextRefreshedEvent event) {
        System.out.println("事件完成通知！" + i++);
        if (Optional.ofNullable(event).isPresent()) {

        }
        if (Objects.nonNull(event)) {

        }
        // StackTraceElement[] stackTrace = new RuntimeException().getStackTrace();
        // Arrays.stream(stackTrace).forEach(System.out::println);
    }
}
