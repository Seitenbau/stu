package com.seitenbau.stu.asserts;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class SampleAssertionComponent
{

    public void verify(String msg)
    {
        assertThat(msg, is("Hello World"));
    }

}
