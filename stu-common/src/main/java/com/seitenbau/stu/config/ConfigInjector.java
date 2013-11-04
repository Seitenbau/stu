package com.seitenbau.stu.config;


public interface ConfigInjector {

  void injectValuesInto(ValueProvider values, Object target);

}
