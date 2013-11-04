package com.seitenbau.stu.rules;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Test;

import com.seitenbau.stu.rules.AssertionLogger;

public class AssertionLoggerTest {

    class Logged {
        public boolean _logged;

        @Override
        public String toString() {
            _logged = true;
            return super.toString();
        }
    }

    Logged l1 = new Logged();
    Logged l2 = new Logged();
    Logged l3 = new Logged();
    AssertionLogger sut = new AssertionLogger();

    @Test
    public void testLazyLogging() {
        assertThat(l1._logged).isFalse();
        assertThat(l2._logged).isFalse();
        assertThat(l3._logged).isFalse();
        
        sut.log(l1,l2);
        assertThat(l1._logged).isTrue();
        assertThat(l2._logged).isTrue();
        assertThat(l3._logged).isFalse();
        
        sut.logLater(l3);
        assertThat(l1._logged).isTrue();
        assertThat(l2._logged).isTrue();
        assertThat(l3._logged).isFalse();
        
        sut.doLog();
        assertThat(l1._logged).isTrue();
        assertThat(l2._logged).isTrue();
        assertThat(l3._logged).isTrue();
    }
}
