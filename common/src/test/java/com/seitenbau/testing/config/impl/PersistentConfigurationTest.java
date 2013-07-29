package com.seitenbau.testing.config.impl;

import static org.fest.assertions.Assertions.assertThat;

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
    assertThat(sut.getString("value1")).isEqualTo("aValue");
    assertThat(sut.getString("value2.values")).isEqualTo("twoValues");
    assertThat(sut.getString("value3[test]")).isEqualTo("testValue");
  }

  @Test
  public void loadDslValues() {
    sut.initValuesFor(new String[]{"test-02"},noProcessor);
    assertThat(sut.getValueMap()).hasSize(BASE_PROPERTIES + 3);
    assertThat(sut.getString("value1")).isEqualTo("aValuevalue2");
    assertThat(sut.getString("value2")).isEqualTo("x2aValuevalue2x3value2");
    assertThat(sut.getString("value3")).isEqualTo("value2");
  }

  @Test
  public void mapDslValues() {
    sut.initValuesFor(new String[]{"test-03"},noProcessor);
    assertThat(sut.getValueMap()).hasSize(BASE_PROPERTIES + 5);
    assertThat(sut.getMapEntry("value", "key1",null)).isSameAs(StoredProperty.NOT_SET_VALUE);
    assertThat(sut.getMapEntry("values", "key",null)).isNull();
    assertThat(sut.getMapEntry("values", "key",StoredProperty.NOT_SET_VALUE)).isSameAs(StoredProperty.NOT_SET_VALUE);
    assertThat(sut.getMapEntry("values", "key1",null)).isEqualTo("value1");
    assertThat(sut.getMapEntry("values", "key2",null)).isEqualTo("value2");
    assertThat(sut.getMapEntry("values", "key3",null)).isEqualTo("value3rainer_");
    assertThat(sut.getString("value2")).isEqualTo("rainer");
    assertThat(sut.getString("value3")).isEqualTo("val3value1");
  }

}
