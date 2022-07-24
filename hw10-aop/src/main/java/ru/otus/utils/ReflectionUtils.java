package ru.otus.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ReflectionUtils {
    public static Set<Method> getAnnotatedMethods(Class<?> tClass,
                                                  Class<? extends Annotation> annotationClass) {
        System.out.println("ReflectionUtils.getAnnotatedMethods");
        return Arrays.stream(tClass.getMethods())
                .filter(m -> m.isAnnotationPresent(annotationClass))
                .collect(Collectors.toSet());
    }

    public static <T> T newInstance(Class<T> tClass) {
        try {
            return tClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> Supplier<T> newInstanceSupplier(Class<T> tClass) {
        return () -> newInstance(tClass);
    }

    public static boolean isSameSignature(Method m1, Method m2) {
        return m1.getName().contentEquals(m2.getName())
                && Arrays.deepEquals(m1.getParameterTypes(), m2.getParameterTypes());
    }
}
