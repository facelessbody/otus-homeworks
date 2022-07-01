package homework;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

public class Example {

    @Before
    public static void staticBefore() {
        System.out.println("Example.staticBefore");
    }

    @After
    public static void staticAfter() {
        System.out.println("Example.staticAfter");
    }

    @Before
    public void firstBefore() {
        System.out.println("Example.firstBefore");
    }

    @After
    public void firstAfter() {
        System.out.println("Example.firstAfter");
    }

    @Test
    public void firstTest() {
        System.out.println("Example.firstTest");
    }

    @Test
    private void secondTest() {
        System.out.println("Example.secondTest");
    }

    public void nonAnnotatedTest() {
        System.out.println("Example.nonAnnotatedTest");
    }

    @Test
    public void bothAnnotatedTest() {
        System.out.println("Example.bothAnnotatedTest");
    }
}
