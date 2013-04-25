package com.seitenbau.testing.config;


public interface ConfigInjector {

	void injectValuesInto(ValueProvider values, Object target);

}
