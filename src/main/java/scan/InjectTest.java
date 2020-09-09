package scan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class InjectTest {
    int k = 20;
    public void test(){
        System.out.println("Autowired Value Inject");
    }
}
