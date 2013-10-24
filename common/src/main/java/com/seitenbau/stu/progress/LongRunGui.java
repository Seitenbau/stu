package com.seitenbau.stu.progress;

public interface LongRunGui
{
  void setProgress(String text, int value);

  void beginSection(String text, int max);
  
  void endSection(String text);
}
