package ru.otus;

public class TestLoggingImpl implements TestLogging {
    @Override
    public void calculation(int param1) {
        System.out.println("TestLoggingImpl.calculation(int)");
    }

    @Override
    public void calculation(int param1, int param2) {
        System.out.println("TestLoggingImpl.calculation(int, int)");
    }

    @Override
    public void calculation(int param1, int param2, String param3) {
        System.out.println("TestLoggingImpl.calculation(int, int, String)");
    }
}
