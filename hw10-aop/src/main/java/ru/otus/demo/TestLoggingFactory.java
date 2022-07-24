package ru.otus.demo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.otus.annotations.Log;
import ru.otus.utils.ReflectionUtils;

import static ru.otus.utils.ReflectionUtils.getAnnotatedMethods;

public class TestLoggingFactory {

    private static final Map<Class<?>, Set<Method>> annotatedMethodsByClasses = new HashMap<>();
    private static final Map<Class<?>, Supplier<?>> suppliersByClasses = new HashMap<>();

    public static <T extends TestLogging> TestLogging create(Class<T> tClass) {
        var supplier = suppliersByClasses.computeIfAbsent(tClass, ReflectionUtils::newInstanceSupplier);
        var newInstance = tClass.cast(supplier.get());

        var annotatedMethods =
                annotatedMethodsByClasses.computeIfAbsent(tClass, cls -> getAnnotatedMethods(cls, Log.class));

        var invocationHandler = new TestLoggingInvocationHandler(newInstance, annotatedMethods);
        return (TestLogging) Proxy.newProxyInstance(TestLogging.class.getClassLoader(),
                new Class[]{TestLogging.class}, invocationHandler);
    }

    @Slf4j
    @RequiredArgsConstructor
    private static class TestLoggingInvocationHandler implements InvocationHandler {
        private final TestLogging instance;
        private final Set<Method> annotatedMethods;

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (annotatedMethods.stream()
                    .anyMatch(m -> ReflectionUtils.isSameSignature(m, method))) {
                log.info("@Log handled\nmethod = {}, args = {}", method, Arrays.deepToString(args));
            }
            return method.invoke(instance, args);
        }
    }

}
