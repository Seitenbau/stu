package com.seitenbau.stu.database;

import com.seitenbau.stu.config.StoredProperty;
import com.seitenbau.stu.util.Holder;

public interface TestConfigFuzzy {

  String DB_FUZZY_OFFSET_KEY = "db.fuzzy.offset";
  
  @StoredProperty(key = DB_FUZZY_OFFSET_KEY, defaultValue="15")
  Holder<String> DB_FUZZY_OFFSET = new Holder<String>();
  
}
