package com.seitenbau.stu.progress;

public class LongRunAction
{

  private LongRunGui _currentGui;

  private int _next;

  public LongRunAction(LongRunGui gui)
  {
    _currentGui = gui;
  }

  public void next(String text)
  {
    if (_currentGui != null)
    {
      _currentGui.setProgress(text, ++_next);
    }
  }

  public void begin(String text, int max)
  {
    if (_currentGui != null)
    {
      _currentGui.beginSection(text,max);
    }
  }

  public void endAction()
  {
    if (_currentGui != null)
    {
      _currentGui.endSection("");
    }
  }

}
