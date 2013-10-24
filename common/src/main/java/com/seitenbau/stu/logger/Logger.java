package com.seitenbau.stu.logger;

public abstract class Logger 
{
  public abstract void trace(Object ...messages); 
  public abstract void debug(Object ...messages); 
  public abstract void info(Object ...messages); 
  public abstract void warn(Object ...messages); 
  public abstract void error(Object ...messages); 
  public abstract void fatal(Object ...messages);

}
