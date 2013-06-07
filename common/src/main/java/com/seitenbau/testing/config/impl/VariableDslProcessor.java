package com.seitenbau.testing.config.impl;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.seitenbau.testing.config.StoredProperty;

public class VariableDslProcessor {

	public String getValue(Map<String, String> values, String key,
			String defaultvalue) {
		return parse(values, key, defaultvalue);
	}

	static final String VAR_PATTERN = "(\\\\?\\$\\{([^\\$]*?)\\})";

	Pattern varPattern = Pattern.compile(VAR_PATTERN);

	String parse(Map<String, String> prop, String key, String defaultvalue) {
		Object value = prop.get(key);
		if (value != null) {
			return processDsl(prop, (String) value, (String) value);
		}
		if (key.contains("$")) {
			return processDsl(prop, key, null);
		}
		return defaultvalue;
	}

	public String processDsl(Map<String, String> prop, String key,
			String defaultvalue) {
		int depth = 15;
		String replaced = key;
		while ((replaced = replaceVars(prop, replaced, defaultvalue))
				.contains("${") && depth-- > 0)
			;
		return replaced.replace("\\${", "${");
	}

	String replaceVars(Map<String, String> prop, String key, String defaultvalue) {
		Matcher matcher = varPattern.matcher(key);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
		  String all = matcher.group(1);
		  if(!all.startsWith("\\")) 
		  {
  			String var = matcher.group(2);
  			String replacement = get(prop, var, defaultvalue);
  			if (replacement != null) 
  			{
  			  if(replacement.contains("${")) 
  			  {
  			    throw new RuntimeException("Was not able to resolve config property : " + var);
  			  }
  				matcher.appendReplacement(sb, replacement);
  			}
		  }
		}
		matcher.appendTail(sb);
		return sb.toString();
	}

	private String get(Map<String, String> prop, String key, String defaultValue) {
		Object value = prop.get(key);
		if (value == null) {
			if (defaultValue == StoredProperty.NOT_SET_VALUE) {
				return null;
			}
			return defaultValue;
		}
		String neu = replaceVars(prop, (String) value, defaultValue);
		return neu;
	}

}
