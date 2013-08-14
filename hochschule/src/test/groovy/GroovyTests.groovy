

class Test
{
  public static Object or(Integer self, Integer arg)
  {
    println self + " | " + arg;
    return 0;
  }

  public static Object or(String self, String arg)
  {
    if (arg == "3") {
      //throw new RuntimeException("3 is nich gut");
    }
    println self + " | " + arg;

    return "0";
  }
}

use(Test) {
  "1" | "2" | "3"
}


def a(int i, int j)
{
  println i + j;
}

a 1, 2
