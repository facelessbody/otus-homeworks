package homework;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

public class Inherited extends Example {

    @Before
    public void secondBefore() {
        System.out.println("Inherited.secondBefore");
    }

    @After
    public void secondAfter() {
        System.out.println("Inherited.secondAfter");
    }

    @Test
    public void thirdTest() {
        System.out.println("Inherited.thirdTest");
    }

    @Test
    public void throwExceptionTest() {
        throw new RuntimeException("something goes wrong");
    }

    @Test
    public void bothAnnotatedTest() {
        System.out.println("Inherited.bothAnnotated");
    }

    @Test
    public static void staticTest() {
        System.out.println("Inherited.staticTest");
    }
}
