package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintPair;

public class NachnameGenerator extends ValueGenerator
{
  private Random random;

  private final String[] values = {"\"M�ller\"", "\"Schmidt\"", "\"Schneider\"", "\"Fischer\"", "\"Weber\"",
      "\"Meyer\"", "\"Wagner\"", "\"Becker\"", "\"Schulz\"", "\"Hoffmann\"", "\"Sch�fer\"", "\"Koch\"", "\"Bauer\"",
      "\"Richter\"", "\"Klein\"", "\"Wolf\"", "\"Schr�der\"", "\"Schneider\"", "\"Neumann\"", "\"Schwarz\"",
      "\"Zimmermann\"", "\"Braun\"", "\"Kr�ger\"", "\"Hofmann\"", "\"Hartmann\"", "\"Lange\"", "\"Schmitt\"",
      "\"Werner\"", "\"Schmitz\"", "\"Krause\"", "\"Meier\"", "\"Lehmann\"", "\"Schmid\"", "\"Schulze\"", "\"Maier\"",
      "\"K�hler\"", "\"Herrmann\"", "\"K�nig\"", "\"Walter\"", "\"Mayer\"", "\"Huber\"", "\"Kaiser\"", "\"Fuchs\"",
      "\"Peters\"", "\"Lang\"", "\"Scholz\"", "\"M�ller\"", "\"Wei�\"", "\"Jung\"", "\"Hahn\"", "\"Schubert\"",
      "\"Vogel\"", "\"Friedrich\"", "\"Keller\"", "\"G�nther\"", "\"Frank\"", "\"Berger\"", "\"Winkler\"", "\"Roth\"",
      "\"Beck\"", "\"Lorenz\"", "\"Baumann\"", "\"Franke\"", "\"Albrecht\"", "\"Schuster\"", "\"Simon\"", "\"Ludwig\"",
      "\"B�hm\"", "\"Winter\"", "\"Kraus\"", "\"Martin\"", "\"Schumacher\"", "\"Kr�mer\"", "\"Vogt\"", "\"Stein\"",
      "\"J�ger\"", "\"Otto\"", "\"Sommer\"", "\"Gro�\"", "\"Seidel\"", "\"Heinrich\"", "\"Brandt\"", "\"Haas\"",
      "\"Schreiber\"", "\"Graf\"", "\"Schulte\"", "\"Dietrich\"", "\"Ziegler\"", "\"Kuhn\"", "\"K�hn\"", "\"Pohl\"",
      "\"Engel\"", "\"Horn\"", "\"Busch\"", "\"Bergmann\"", "\"Thomas\"", "\"Voigt\"", "\"Sauer\"", "\"Arnold\"",
      "\"Wolff\"", "\"Pfeiffer\""};

  @Override
  public void initialize(long seed)
  {
    random = new Random(seed);
  }

  @Override
  public Result nextValue(EntityBlueprint eb)
  {
	  return new Result(values[random.nextInt(values.length)], true);
	  }

  public static class Factory implements ValueGeneratorFactory
  {

    @Override
    public ValueGenerator createGenerator()
    {
      return new NachnameGenerator();
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
