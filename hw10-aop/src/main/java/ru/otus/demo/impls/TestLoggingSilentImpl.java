package ru.otus.demo.impls;

import ru.otus.annotations.Log;
import ru.otus.demo.TestLogging;

public class TestLoggingSilentImpl implements TestLogging {
    @Override
    public void calculation(int param1) {

    }

    @Log
    @Override
    public void calculation(int param1, int param2) {

    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {

    }
}
