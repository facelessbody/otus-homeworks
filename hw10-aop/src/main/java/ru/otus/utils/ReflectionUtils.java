package ru.otus.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ReflectionUtils {
    public static Set<Method> getAnnotatedMethods(Class<?> interfaceClass,
                                                  Class<? extends Annotation> annotationClass) {
        System.out.println("ReflectionUtils.getAnnotatedMethods");
        return Arrays.stream(interfaceClass.getMethods())
                .filter(m -> m.isAnnotationPresent(annotationClass))
                .collect(Collectors.toSet());
    }
}
