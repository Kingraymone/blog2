package scan;

import org.springframework.stereotype.Service;

@Service
public class Scan {
    int k = 2;
    public void test(int i){
        System.out.println("Component Scanner"+i);
    }

    public static void kk(){
        System.out.println("static");
    }

    public int join(int i){
        System.out.println("return value"+i);
        return i+1;
    }
}
