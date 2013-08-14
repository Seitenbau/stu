public class StackTrace
{
  static StackTraceElement changeLineNumber(StackTraceElement element, int newLineNumber)
  {
    return new StackTraceElement(element.getClassName(), element.getMethodName(), element.getFileName(), newLineNumber);
  }

  @SuppressWarnings("serial")
  static class MyException extends RuntimeException {
    public MyException(String message, Throwable e)
    {
      super(message, e);
      updateStackTrace();
    }

    public MyException(String message)
    {
      super(message);
      updateStackTrace();
    }

    void updateStackTrace()
    {
      StackTraceElement[] currentStack = getStackTrace();
      for (int i = 0; i < currentStack.length; i++) {
        StackTraceElement element = currentStack[i];
        if (element.getClassName().equals("StackTrace") && element.getMethodName().equals("throwSomething")) {
          currentStack[i] = changeLineNumber(element, element.getLineNumber() - 1);
        }
      }
      setStackTrace(currentStack);
    }
  }

  static void throwSomething()
  {
    System.out.println("Hallo");
    throw new MyException("Huhu");
  }


  public static void main(String[] args) {
    throwSomething();
  }

}
