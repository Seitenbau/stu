package com.seitenbau.testing.dbunit;

import com.seitenbau.testing.config.StoredProperty;
import com.seitenbau.testing.util.Holder;

/**
 * Default constants 4 Database access.
 * 
 * Attention: <br>
 * <b>The DatabaseTester classes are using the key
 * but not actually the Fields from this interface.</b>
 * <pre>
 *   db.driver=org.gjt.mm.mysql.Driver
 *   db.url=jdbc:mysql://192.168.0.x:3306/dbName
 *   db.username=admin
 *   db.password=geheim
 *   # optinal :
 *   db.schema=theSchema
 * </pre>
 */
public interface TestConfigDatabase
{
  @StoredProperty(key = "db.driver")
  Holder<String> DB_DRIVER = new Holder<String>();
  
  @StoredProperty(key = "db.url")
  Holder<String> DB_URL = new Holder<String>();
  
  @StoredProperty(key = "db.username")
  Holder<String> DB_USER = new Holder<String>();
  
  @StoredProperty(key = "db.password")
  Holder<String> DB_PWD = new Holder<String>();
  
  @StoredProperty(key = "db.schema",defaultValue="")
  Holder<String> DB_SHEMA= new Holder<String>();
}
