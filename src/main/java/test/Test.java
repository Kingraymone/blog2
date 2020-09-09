package test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.DebuggingClassWriter;
import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.context.annotation.ImportAware;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;
import scan.InjectTest;
import scan.Scan;

@Component
public class Test implements ImportAware {
    @Autowired
    InjectTest injectTest;

    public AnnotationMetadata aware;

    public void kk() {
        System.out.println("test");
        injectTest.test();
    }

    @Override
    public void setImportMetadata(AnnotationMetadata importMetadata) {
        this.aware = importMetadata;
    }

    public static void main(String[] args) {
        System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "E:\\git\\cglib");
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(Scan.class);
        enhancer.setCallbacks(new Callback[]{new MethodInterceptorTest(), (MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("代理类222222222前");
            Object o1 = methodProxy.invokeSuper(o, objects);
            System.out.println("代理类22222222后");
            return o1;
        }});
        Scan o = (Scan) enhancer.create();
        o.test(5);
    }
}
