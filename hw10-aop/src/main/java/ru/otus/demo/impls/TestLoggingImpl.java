package ru.otus.demo.impls;

import lombok.extern.slf4j.Slf4j;
import ru.otus.annotations.Log;
import ru.otus.demo.TestLogging;

@Slf4j
public class TestLoggingImpl implements TestLogging {
    @Log
    @Override
    public void calculation(int param1) {
        log.info("TestLoggingImpl.calculation(int)");
    }

    @Override
    public void calculation(int param1, int param2) {
        log.info("TestLoggingImpl.calculation(int, int)");
    }

    @Log
    @Override
    public void calculation(int param1, int param2, String param3) {
        log.info("TestLoggingImpl.calculation(int, int, String)");
    }
}
