package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.values.valuetypes.StringValue;

public class NachnameGenerator extends ValueGenerator {

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
		
		values = new String[]{ "\"Müller\"", "\"Schmidt\"",
				"\"Schneider\"", "\"Fischer\"", "\"Weber\"", "\"Meyer\"",
				"\"Wagner\"", "\"Becker\"", "\"Schulz\"", "\"Hoffmann\"",
				"\"Schäfer\"", "\"Koch\"", "\"Bauer\"", "\"Richter\"", "\"Klein\"",
				"\"Wolf\"", "\"Schröder\"", "\"Schneider\"", "\"Neumann\"",
				"\"Schwarz\"", "\"Zimmermann\"", "\"Braun\"", "\"Krüger\"",
				"\"Hofmann\"", "\"Hartmann\"", "\"Lange\"", "\"Schmitt\"",
				"\"Werner\"", "\"Schmitz\"", "\"Krause\"", "\"Meier\"",
				"\"Lehmann\"", "\"Schmid\"", "\"Schulze\"", "\"Maier\"",
				"\"Köhler\"", "\"Herrmann\"", "\"König\"", "\"Walter\"",
				"\"Mayer\"", "\"Huber\"", "\"Kaiser\"", "\"Fuchs\"", "\"Peters\"",
				"\"Lang\"", "\"Scholz\"", "\"Möller\"", "\"Weiß\"", "\"Jung\"",
				"\"Hahn\"", "\"Schubert\"", "\"Vogel\"", "\"Friedrich\"",
				"\"Keller\"", "\"Günther\"", "\"Frank\"", "\"Berger\"",
				"\"Winkler\"", "\"Roth\"", "\"Beck\"", "\"Lorenz\"", "\"Baumann\"",
				"\"Franke\"", "\"Albrecht\"", "\"Schuster\"", "\"Simon\"",
				"\"Ludwig\"", "\"Böhm\"", "\"Winter\"", "\"Kraus\"", "\"Martin\"",
				"\"Schumacher\"", "\"Krämer\"", "\"Vogt\"", "\"Stein\"",
				"\"Jäger\"", "\"Otto\"", "\"Sommer\"", "\"Groß\"", "\"Seidel\"",
				"\"Heinrich\"", "\"Brandt\"", "\"Haas\"", "\"Schreiber\"",
				"\"Graf\"", "\"Schulte\"", "\"Dietrich\"", "\"Ziegler\"",
				"\"Kuhn\"", "\"Kühn\"", "\"Pohl\"", "\"Engel\"", "\"Horn\"",
				"\"Busch\"", "\"Bergmann\"", "\"Thomas\"", "\"Voigt\"",
				"\"Sauer\"", "\"Arnold\"", "\"Wolff\"", "\"Pfeiffer\""};
	}
	
	@Override
	public Result nextValue(){		
		return new Result(new StringValue(values[random.nextInt(values.length)]), true, true);
	}
	
	@Override
	public Result nextValue(Integer index) {
		Random rand = new Random(index);		
		return new Result(new StringValue(values[rand.nextInt(values.length)]), true, true);
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
