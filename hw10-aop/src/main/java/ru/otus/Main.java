package ru.otus;

import ru.otus.demo.TestLoggingFactory;
import ru.otus.demo.impls.TestLoggingImpl;
import ru.otus.demo.impls.TestLoggingSilentImpl;

public class Main {
    public static void main(String[] args) {

        var first = TestLoggingFactory.create(TestLoggingImpl.class);
        first.calculation(1);
        first.calculation(1, 2);
        first.calculation(1, 2, "foo");

        var second = TestLoggingFactory.create(TestLoggingSilentImpl.class);
        second.calculation(9);
        second.calculation(9, 8);
        second.calculation(9, 8, "bar");
    }

}
