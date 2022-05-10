package homework;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

public class TestRunner {

    public static void main(String[] args) throws ClassNotFoundException {
//        var results = runTestSuite(Class.forName("homework.Example"));
//        var results = runTestSuite(Class.forName("homework.Inherited"));
        var results = runTestSuite(Class.forName("homework.ThrowingLifecycle"));
        System.out.printf("""
                There are %d tests found
                  PASSED: %d
                  FAILED: %d
                """, results.total, results.passed, results.failed);
    }

    private record Results(int total, int passed, int failed) {

    }

    private static Results runTestSuite(Class<?> testSuiteClass) {

        int total = 0;
        int passed = 0;
        int failed = 0;

        var afterMethods = findAnnotatedMethods(testSuiteClass, After.class);
        var beforeMethods = findAnnotatedMethods(testSuiteClass, Before.class);

        var tests = findAnnotatedMethods(testSuiteClass, Test.class);
        for (Method test : tests) {
            ++total;
            try {
                var newInstance = testSuiteClass.getConstructor().newInstance();
                runTest(newInstance, beforeMethods, afterMethods, test);
                System.out.println("PASSED");
                ++passed;
            } catch (Throwable t) {
                var msg = Arrays.stream(t.getSuppressed())
                        .map(Throwable::toString)
                        .collect(Collectors.joining("\n\t"));
                System.out.println("FAILED\n\t" + msg);
                ++failed;
            } finally {
                System.out.println(
                        Stream.generate(() -> "-").limit(80).collect(Collectors.joining()));
            }
        }
        return new Results(total, passed, failed);
    }

    private static void runTest(Object $this, Collection<Method> beforeMethods, Collection<Method> afterMethods,
                                Method test) {
        var exceptions = new ArrayList<Throwable>();
        try {
            for (Method m : beforeMethods) {
                invoke($this, m);
            }
            invoke($this, test);
        } catch (Throwable e) {
            exceptions.add(e);
        } finally {
            for (Method m : afterMethods) {
                try {
                    invoke($this, m);
                } catch (Throwable e) {
                    exceptions.add(e);
                }
            }
        }

        if (exceptions.size() > 0) {
            var t = new RuntimeException();
            exceptions.forEach(t::addSuppressed);
            throw t;
        }
    }

    public static void invoke(Object $this, Method m) throws Throwable {
        try {
            m.setAccessible(true);
            m.invoke($this);
        } catch (InvocationTargetException e) {
            throw e.getCause();
        }
    }

    private static List<Method> findAnnotatedMethods(Class<?> aClass, Class<? extends Annotation> annotationClass) {
        if (aClass == null) {
            return new ArrayList<>();
        }

        var methods = findAnnotatedMethods(aClass.getSuperclass(), annotationClass);
        for (var m : aClass.getDeclaredMethods()) {
            if ((m.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }
            findOverridden(methods, m)
                    .ifPresent(methods::remove);
            if (m.isAnnotationPresent(annotationClass)) {
                methods.add(m);
            }
        }
        return methods;
    }

    private static Optional<Method> findOverridden(List<Method> methods, Method m) {
        return methods.stream()
                .filter(x -> x.getName().contentEquals(m.getName()))
                .filter(x -> x.getParameterCount() == m.getParameterCount())
                .filter(x -> {
                    var xParameterTypes = x.getParameterTypes();
                    var mParameterTypes = m.getParameterTypes();
                    for (int i = 0; i < xParameterTypes.length && i < mParameterTypes.length; i++) {
                        if (!xParameterTypes[i].equals(mParameterTypes[i])) {
                            return false;
                        }
                    }
                    return true;
                })
                .findAny();
    }
}
