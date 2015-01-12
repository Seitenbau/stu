package model;

import com.seitenbau.stu.database.generator.DataType;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.TableBuilder;
import com.seitenbau.stu.database.generator.values.BuchNameGenerator;
import com.seitenbau.stu.database.generator.values.DataGenerator;
import com.seitenbau.stu.database.generator.values.IntegerGenerator;
import com.seitenbau.stu.database.generator.values.NachnameGenerator;
import com.seitenbau.stu.database.generator.values.StringGenerator;
import com.seitenbau.stu.database.generator.values.VerlagNameGenerator;
import com.seitenbau.stu.database.generator.values.constraints.CompareValueConstraint;
import com.seitenbau.stu.database.generator.values.constraints.CompareValueConstraint.CompareType;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintsData;
import com.seitenbau.stu.database.generator.values.constraints.DataConstraint;
import com.seitenbau.stu.database.generator.values.constraints.UniqueConstraint;

public class BookDatabaseModel extends DatabaseModel {
	public BookDatabaseModel() {
		database("BookDatabase");
		packageName("com.seitenbau.stu.bookdatabase.model");
		enableTableModelClassesGeneration();
		// disbaleTableDSLGeneration();
		infinite(4);
		dataSource(new ConstraintsData());

		TableBuilder buch = table("buch");
		TableBuilder verlag = table("verlag");
		TableBuilder autor = table("autor");

		buch //
		.column("id", DataType.BIGINT)
				//
				.defaultIdentifier()
				//
				.autoInvokeNext()
				//
				.column("name", DataType.VARCHAR)
				//
				.generator(new BuchNameGenerator())
				.column("verlag", DataType.BIGINT) //
		.reference.local.foreign(verlag.ref("id")).multiplicity("0..*").build();

		verlag //
		.column("id", DataType.BIGINT) //
				.defaultIdentifier() //
				.autoInvokeNext() //
				.column("name", DataType.VARCHAR) //
				.generator(new VerlagNameGenerator()).build();

		autor //		
		
		.constraint(new DataConstraint("vorname"), "vorname", new DataConstraint("geschlecht"), "geschlecht")
		.constraint(new DataConstraint("vorname"), "vorname", new DataConstraint("sprache"), "sprache")
		.constraint(new CompareValueConstraint(CompareType.GREATER_EQUAL), "geburtsjahr", "mitglied_seit")
		.constraint(new UniqueConstraint(), "vorname")
		
		.column("id", DataType.BIGINT)
				//
				.defaultIdentifier()
				//
				.autoInvokeNext()
				//				
				.column("vorname", DataType.VARCHAR)			
				.generator(new DataGenerator("vorname"))
				//
				.column("nachname", DataType.VARCHAR)				
				.generator(new NachnameGenerator())
				//
				.column("geschlecht", DataType.VARCHAR)
				.generator(new DataGenerator("geschlecht"))
				//
				.column("sprache", DataType.VARCHAR)							
				.generator(new DataGenerator("sprache"))
				//
				.column("geburtsjahr", DataType.INTEGER)
				.generator(new IntegerGenerator(1990, 2014))
				//
				.column("mitglied_seit", DataType.INTEGER)
				.generator(new IntegerGenerator(1900, 2014))				
				//
				.build();

		associativeTable("buch_autor").column("buch_id", DataType.BIGINT).reference
				.foreign(buch).multiplicity("0..*")
				.column("autor_id", DataType.BIGINT).reference.foreign(autor)
				.multiplicity("1..*").build();
	}
}
