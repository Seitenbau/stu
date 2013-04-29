package com.seitenbau.testing.config.impl;

import static org.fest.assertions.Assertions.*;

import java.net.InetAddress;
import java.util.Properties;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;

import com.seitenbau.testing.config.StoredProperty;
import com.seitenbau.testing.config.ValueProcessor;
import com.seitenbau.testing.mockito.MockitoRule;

public class PersistentConfigurationTest {
  PersistentConfiguration sut = new PersistentConfiguration(new VariableDslProcessor()) {
    @Override
    String toPath(String part) {
      return part; // located inside this testclass package
    }
  };
  
  @Rule
  public MockitoRule mockito = new MockitoRule();

  @Mock
  ValueProcessor noProcessor;

  int BASE_PROPERTIES = 0; // ip,hostename,username,dnsname

  @Test
  public void loadKeyValues() {
    sut.initValuesFor(new String[]{"test-01"},noProcessor);
    assertThat(sut.getValueMap()).hasSize(BASE_PROPERTIES + 3);
    assertThat(sut.getString("wert1")).isEqualTo("einWert");
    assertThat(sut.getString("wert2.werte")).isEqualTo("zweiWerte");
    assertThat(sut.getString("wert3[test]")).isEqualTo("testWert");
  }

  @Test
  public void loadDslValues() {
    sut.initValuesFor(new String[]{"test-02"},noProcessor);
    assertThat(sut.getValueMap()).hasSize(BASE_PROPERTIES + 3);
    assertThat(sut.getString("wert1")).isEqualTo("einWertwert2");
    assertThat(sut.getString("wert2")).isEqualTo("x2einWertwert2x3wert2");
    assertThat(sut.getString("wert3")).isEqualTo("wert2");
  }

  @Test
  public void mapDslValues() {
    sut.initValuesFor(new String[]{"test-03"},noProcessor);
    assertThat(sut.getValueMap()).hasSize(BASE_PROPERTIES + 5);
    assertThat(sut.getMapEntry("wert", "key1",null)).isSameAs(StoredProperty.NOT_SET_VALUE);
    assertThat(sut.getMapEntry("werte", "key",null)).isNull();
    assertThat(sut.getMapEntry("werte", "key",StoredProperty.NOT_SET_VALUE)).isSameAs(StoredProperty.NOT_SET_VALUE);
    assertThat(sut.getMapEntry("werte", "key1",null)).isEqualTo("wert1");
    assertThat(sut.getMapEntry("werte", "key2",null)).isEqualTo("wert2");
    assertThat(sut.getMapEntry("werte", "key3",null)).isEqualTo("wert3rainer_");
    assertThat(sut.getString("wert2")).isEqualTo("rainer");
    assertThat(sut.getString("wert3")).isEqualTo("val3wert1");
  }

}
