package com.seitenbau.stu.database.generator.values;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;

public class TimestampGenerator extends ValueGenerator
{

  private Random random;

  public TimestampGenerator()
  {
  }

  @Override
  public void initialize(long seed)
  {
    random = new Random(seed);
  }

  @Override
  public Result nextValue(EntityBlueprint eb)
  {
	 Calendar calendar = Calendar.getInstance();
	java.util.Date now = calendar.getTime();
	java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());
	//return new Result("\"" + currentTimestamp.toString().substring(0, 19) + "\"", true);
	return new Result("asDate(\"" + currentTimestamp.toString().substring(0, 19) + "\")", true);
  }

  public int randBetween(int start, int end) {
    return start + random.nextInt(1 + end - start);
  }

  public String getString(int i)
  {
    if (i < 10) {
      return "0" + String.valueOf(i);
    }
    return String.valueOf(i);
  }

  public static class Factory implements ValueGeneratorFactory {

    @Override
    public ValueGenerator createGenerator()
    {
      return new TimestampGenerator();
    }

  }

@Override
public void addConstraint(ConstraintPair constraintPair) {
	// TODO Auto-generated method stub
	
}

@Override
public void clearConstraints() {
	// TODO Auto-generated method stub
	
}
}
