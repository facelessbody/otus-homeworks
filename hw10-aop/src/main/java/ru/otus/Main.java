package ru.otus;

public class Main {
    public static void main(String[] args) {

        var first = TestLoggingFactory.create(TestLoggingImpl::new);
        first.calculation(1);
        first.calculation(1, 2);
        first.calculation(1, 2, "foo");

        var second = TestLoggingFactory.create(TestLoggingSilentImpl::new);
        second.calculation(9);
        second.calculation(9, 8);
        second.calculation(9, 8, "bar");
    }

}
