package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import ru.otus.annotations.Log;

import static ru.otus.utils.ReflectionUtils.getAnnotatedMethods;

public class TestLoggingFactory {

    public static Set<Method> annotatedMethods = getAnnotatedMethods(TestLogging.class, Log.class);

    public static <T extends TestLogging> TestLogging create(Supplier<T> supplier) {
        var invocationHandler = new TestLoggingInvocationHandler(supplier.get(), annotatedMethods);
        return (TestLogging) Proxy.newProxyInstance(TestLogging.class.getClassLoader(),
                new Class[]{TestLogging.class}, invocationHandler);
    }

    @RequiredArgsConstructor
    private static class TestLoggingInvocationHandler implements InvocationHandler {
        private final TestLogging instance;
        private final Set<Method> annotatedMethods;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (annotatedMethods.contains(method)) {
                System.out.println("method = " + method + ", args = " + Arrays.deepToString(args));
            }
            return method.invoke(instance, args);
        }
    }

}
