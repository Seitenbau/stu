package model;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.seitenbau.stu.database.generator.data.DataGenerator;
import com.seitenbau.stu.database.generator.data.Entities;
import com.seitenbau.stu.database.generator.data.STUTableOutput;

public class TestLinkGenerator {

	public static void main(String[] args) throws Exception {
		final TestLinkModel model = new TestLinkModel();

		final DataGenerator generator = new DataGenerator(model);

		model.generate();
		final Entities entities = generator.generate();

		STUTableOutput output = new STUTableOutput();
		final String generatedDSL = output.create(entities);
		System.out.println(generatedDSL);

		// Write TestData Groovy DSL file
		String dsl = new STUTableOutput().create(entities);
		String outFile = "src/test/generated-java/" + model.getPackageName().replace(".", "/") + "/" + model.getName()
				+ "DataSetDefault.groovy";
		System.out.println(outFile);
		FileUtils.write(new File(outFile), dsl);

		String refs = new STUTableOutput().createRefs(entities);
		String outFileRefs = "src/test/generated-java/" + model.getPackageName().replace(".", "/") + "/" + model.getName()
				+ "DataSetRefs.java";
		System.out.println(outFileRefs);
		FileUtils.write(new File(outFileRefs), refs);

		System.out.println("-----------------------------");
		entities.printStats();
		System.out.println();

		System.out.println("Verification Loop Iterations: " + entities.getLoopCount() + "\t");
	}

}