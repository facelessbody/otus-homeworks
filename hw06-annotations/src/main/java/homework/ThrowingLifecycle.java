package homework;

import homework.annotation.After;
import homework.annotation.Before;
import homework.annotation.Test;

public class ThrowingLifecycle extends Example {

    @Before
    public void brokenBefore() {
        throw new RuntimeException("oops..");
    }

    @Test
    public void willNotRunAnytime() {
        System.out.println("ThrowingLifecycle.willNotRunAnytime");
    }

    @After
    public void brokenAfter() {
        throw new RuntimeException("oops.. Did it again!");
    }
}
