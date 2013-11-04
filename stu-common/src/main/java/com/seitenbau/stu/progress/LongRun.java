package com.seitenbau.stu.progress;

public class LongRun
{
  private static LongRun _instance;

  public static LongRun getInstance()
  {
    if (_instance == null)
    {
      _instance = new LongRun();
    }
    return _instance;
  }

  private static LongRunGui _currentGui;

  public static LongRunAction beginAction(String name, int max)
  {
    LongRunAction action = new LongRunAction(_currentGui);
    action.begin(name,max);
    return action;
  }

  public void registerGui(LongRunGui gui)
  {
    _currentGui = gui;
  }

}
