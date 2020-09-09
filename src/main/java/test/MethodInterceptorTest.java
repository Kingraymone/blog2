package test;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import scan.Scan;

import java.lang.reflect.Method;

public class MethodInterceptorTest implements MethodInterceptor {
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("代理前！");
        Object o1 = methodProxy.invokeSuper(o, objects);
        System.out.println("代理后！");
        return o1;
    }
}
