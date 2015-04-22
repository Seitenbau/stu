package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;

public class NachnameGenerator extends ValueGenerator {

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
		
		values = new String[]{ "\"M�ller\"", "\"Schmidt\"",
				"\"Schneider\"", "\"Fischer\"", "\"Weber\"", "\"Meyer\"",
				"\"Wagner\"", "\"Becker\"", "\"Schulz\"", "\"Hoffmann\"",
				"\"Sch�fer\"", "\"Koch\"", "\"Bauer\"", "\"Richter\"", "\"Klein\"",
				"\"Wolf\"", "\"Schr�der\"", "\"Schneider\"", "\"Neumann\"",
				"\"Schwarz\"", "\"Zimmermann\"", "\"Braun\"", "\"Kr�ger\"",
				"\"Hofmann\"", "\"Hartmann\"", "\"Lange\"", "\"Schmitt\"",
				"\"Werner\"", "\"Schmitz\"", "\"Krause\"", "\"Meier\"",
				"\"Lehmann\"", "\"Schmid\"", "\"Schulze\"", "\"Maier\"",
				"\"K�hler\"", "\"Herrmann\"", "\"K�nig\"", "\"Walter\"",
				"\"Mayer\"", "\"Huber\"", "\"Kaiser\"", "\"Fuchs\"", "\"Peters\"",
				"\"Lang\"", "\"Scholz\"", "\"M�ller\"", "\"Wei�\"", "\"Jung\"",
				"\"Hahn\"", "\"Schubert\"", "\"Vogel\"", "\"Friedrich\"",
				"\"Keller\"", "\"G�nther\"", "\"Frank\"", "\"Berger\"",
				"\"Winkler\"", "\"Roth\"", "\"Beck\"", "\"Lorenz\"", "\"Baumann\"",
				"\"Franke\"", "\"Albrecht\"", "\"Schuster\"", "\"Simon\"",
				"\"Ludwig\"", "\"B�hm\"", "\"Winter\"", "\"Kraus\"", "\"Martin\"",
				"\"Schumacher\"", "\"Kr�mer\"", "\"Vogt\"", "\"Stein\"",
				"\"J�ger\"", "\"Otto\"", "\"Sommer\"", "\"Gro�\"", "\"Seidel\"",
				"\"Heinrich\"", "\"Brandt\"", "\"Haas\"", "\"Schreiber\"",
				"\"Graf\"", "\"Schulte\"", "\"Dietrich\"", "\"Ziegler\"",
				"\"Kuhn\"", "\"K�hn\"", "\"Pohl\"", "\"Engel\"", "\"Horn\"",
				"\"Busch\"", "\"Bergmann\"", "\"Thomas\"", "\"Voigt\"",
				"\"Sauer\"", "\"Arnold\"", "\"Wolff\"", "\"Pfeiffer\""};
	}
	
	@Override
	public Result nextValue(){		
		return new Result(values[random.nextInt(values.length)], true);
	}
	
	@Override
	public Result nextValue(EntityBlueprint eb) {
		return new Result(values[random.nextInt(values.length)], true);
	}

	@Override
	public Result nextValue(Integer index, EntityBlueprint eb) {
		return new Result(values[random.nextInt(values.length)], true);
	}
	
	@Override
	public Result nextValue(Integer index) {
		Random rand = new Random(index);		
		return new Result(values[rand.nextInt(values.length)], true);
	}

	public static class Factory implements ValueGeneratorFactory {

		@Override
		public ValueGenerator createGenerator() {
			return new NachnameGenerator();
		}
	}
	

	
	@Override
	public Integer getMaxIndex() {
		return values.length;
	}
}
