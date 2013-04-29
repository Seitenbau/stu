package com.seitenbau.testing.util;

import static org.fest.assertions.Assertions.*;

import org.junit.Test;

public class SettableTest {

    @Test
    public void testInitialState() {
        Settable<String> sut = new Settable<String>();
        assertThat(sut.getValue()).isNull();
        assertThat(sut.wasSet()).isFalse();
    }

    @Test
    public void testInitialStateWithConstructor1() {
        Settable<String> sut = new Settable<String>("Rainer");
        assertThat(sut.getValue()).isEqualTo("Rainer");
        assertThat(sut.wasSet()).isTrue();
    }

    @Test
    public void testSetValueState() {
        Settable<String> sut = new Settable<String>();
        sut.setValue("Wein hold");
        assertThat(sut.getValue()).isEqualTo("Wein hold");
        assertThat(sut.wasSet()).isTrue();
    }
}
