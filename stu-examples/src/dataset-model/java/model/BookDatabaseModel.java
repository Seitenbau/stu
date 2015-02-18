package model;

import com.seitenbau.stu.database.generator.DataType;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.TableBuilder;
import com.seitenbau.stu.database.generator.values.BuchNameGenerator;
import com.seitenbau.stu.database.generator.values.DataGenerator;
import com.seitenbau.stu.database.generator.values.IntegerGenerator;
import com.seitenbau.stu.database.generator.values.NachnameGenerator;
import com.seitenbau.stu.database.generator.values.VerlagNameGenerator;
import com.seitenbau.stu.database.generator.values.constraints.CompareValueConstraint;
import com.seitenbau.stu.database.generator.values.constraints.CompareValueConstraint.CompareType;
import com.seitenbau.stu.database.generator.values.constraints.ConstraintsData;
import com.seitenbau.stu.database.generator.values.constraints.DataConstraint;
import com.seitenbau.stu.database.generator.values.constraints.RConstraint;
import com.seitenbau.stu.database.generator.values.constraints.RangeConstraint;
import com.seitenbau.stu.database.generator.values.constraints.GlobalConstraint;
import com.seitenbau.stu.database.generator.values.constraints.SolverConstraint;
import com.seitenbau.stu.database.generator.values.constraints.SolverConstraint.Constraint;
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
		TableBuilder adresse = table("adresse");
		
		//constraint(new SuperConstraint(String column, String expression, String...targets))

		constraint(new GlobalConstraint(
				"autor.land",
				"autor.land == adresse.land && !(true == false) && -12345 == (-12345 * 2) / 2 && -123.45 == -123.45 && 8999999999999999999 == 8999999999999999999 && 195.23 == 95.13 + 100.1 && (true || false && true) == true && false != true && true && 5 > 4 && 'Test_String' == 'Test_String' && 50 % 30 == 20 && 20 / 10 == 2",
				"adresse.land"));
		constraint(new GlobalConstraint("autor.mitglied_seit",
				"autor.mitglied_seit >= autor.geburtsjahr + 16 && autor.mitglied_seit % 4 == 0", "autor.geburtsjahr"));
		constraint(new GlobalConstraint("autor.last_login", "autor.last_login >= autor.mitglied_seit",
				"autor.mitglied_seit"));
		constraint(new GlobalConstraint("autor.anzahl_buecher", "autor.anzahl_buecher == COUNT(buch)"));
		constraint(new GlobalConstraint("buch.anzahl", "buch.anzahl == COUNT(buch)"));
		constraint(new GlobalConstraint("autor.vorname", "autor.vorname <= 'Robin'"));
		constraint(new GlobalConstraint("autor.summe_buecher", "autor.summe_buecher == SUM(buch.preis)"));
		//constraint(new SuperConstraint("autor.nachname_laenge", "autor.nachname_laenge == LENGTH(autor.nachname)", "autor.nachname"));
		constraint(new GlobalConstraint("autor.nachname_laenge", "autor.nachname_laenge == LENGTH(adresse.land)", "adresse.land"));
		
		constraint(new RConstraint("autor.geburtsjahr", 1900, 2015));
		constraint(new RConstraint("autor.mitglied_seit", 1985, 2015));
		constraint(new RConstraint("autor.last_login", 1997, 2015));
		
		constraint(new SolverConstraint(Constraint.XplusClteqZ, "autor.geburtsjahr", 16, "autor.mitglied_seit"));
	
		
		
		//constraint(new SuperConstraint("autor.geschlecht", "this > 1 && autor.geschlecht == autor.geschlecht[1]"));
		//constraint(new SuperConstraint("autor.nachname", "autor.nachname != autor.nachname[0]")
		//constraint(new SuperConstraint("autor.nachname", "autor.nachname != autor.nachname[this-1]")
		//constraint(new SuperConstraint("autor.nachname", "(adresse ... == null)? asdas : asdasd)
		
		
		// constraint(new CompareValueConstraint(CompareType.EQUAL),
		// "autor.land", new
		// CompareValueConstraint(CompareType.EQUAL),"adresse.land");
		// constraint(constraint, "land",//
		// "--person2adresse[aktiv=1]-->adresse.land")

		// constraint(EqualConstraint("autor.land", "adresse.land")

		buch //
		.column("id", DataType.BIGINT)
		//
				.defaultIdentifier()
				//
				.autoInvokeNext()
				//
				.column("name", DataType.VARCHAR).generator(new BuchNameGenerator())
				//
				.column("preis", DataType.INTEGER).generator(new IntegerGenerator(1, 20))
				//
				.column("anzahl", DataType.INTEGER).generator(new IntegerGenerator(1, 20))
				//
				.column("verlag", DataType.BIGINT).reference.local.foreign(verlag.ref("id")).multiplicity("0..*")
				.build();

		verlag //
		.column("id", DataType.BIGINT) //
				.defaultIdentifier() //
				.autoInvokeNext() //
				.column("name", DataType.VARCHAR) //
				.generator(new VerlagNameGenerator()).build();

		autor //
//		 .constraint(new DataConstraint("vorname"), "vorname", new
//		 DataConstraint("geschlecht"), "geschlecht")
//		 .constraint(new DataConstraint("vorname"), "vorname", new
//		 DataConstraint("sprache"), "sprache")
		// .constraint(new CompareValueConstraint(CompareType.GREATER_EQUAL),
		// "geburtsjahr", "mitglied_seit")
		// .constraint(new CompareValueConstraint(CompareType.SMALLER_EQUAL),
		// "mitglied_seit", "last_login")
		// .constraint(new CompareValueConstraint(CompareType.EQUAL), "land",
		// "adresse_id.land")

		// .constraint(constraint, "land", "adresse.land")
		// .constraint(constraint, "land",
		// "--person2adresse[aktiv=1]-->adresse.land")

		// .constraint(new CompareValueConstraint(CompareType.SMALLER),
		// "last_login", "mitglied_seit")
		// .constraint(new UniqueConstraint(), "vorname")

//		 .constraint(new RangeConstraint(1900, 2015), "geburtsjahr")
//		 .constraint(new RangeConstraint(1985, 2015), "mitglied_seit")
//		 .constraint(new RangeConstraint(1997, 2015), "last_login")

		// .constraint("geburtsjahr", new Range(1900, 2015));
		// .constraint("mitglied", new Range(1990, 2015));
		// .constraint("geburtsjahr",
		// "geburtsjahr <= "mitglied", "mitglied");


		.column("id", DataType.BIGINT)
		//
				.defaultIdentifier()
				//
				.autoInvokeNext()
				//
				.column("vorname", DataType.VARCHAR).generator(new DataGenerator("vorname"))
				//
				.column("nachname_laenge", DataType.INTEGER).generator(new IntegerGenerator(0, 100))
				//
				.column("nachname", DataType.VARCHAR).generator(new NachnameGenerator())
				//
				
				//
				.column("geschlecht", DataType.VARCHAR).generator(new DataGenerator("geschlecht"))
				//
				.column("sprache", DataType.VARCHAR).generator(new DataGenerator("sprache"))
				//
				.column("land", DataType.VARCHAR).generator(new DataGenerator("land"))
				//
				.column("geburtsjahr", DataType.INTEGER)
				//
				.column("mitglied_seit", DataType.INTEGER).generator(new IntegerGenerator(1990, 2015))
				//
				.column("last_login", DataType.INTEGER).generator(new IntegerGenerator(1990, 2015))
				//
				.column("anzahl_buecher", DataType.INTEGER).generator(new IntegerGenerator(0, 100))
				//
				.column("summe_buecher", DataType.INTEGER).generator(new IntegerGenerator(0, 100))
				//
				.column("adresse_id", DataType.BIGINT) //
		.reference //
		.local.name("belongsTo").description("").foreign(adresse).name("hasMembers").description("")

		//
				.build();

		adresse.column("id", DataType.BIGINT)
		//
				.defaultIdentifier()
				//
				.autoInvokeNext()
				//
				.column("strasse", DataType.VARCHAR).generator(new DataGenerator("strasse"))
				//
				.column("nummer", DataType.VARCHAR).generator(new IntegerGenerator(1, 99))
				//
				.column("plz", DataType.VARCHAR).generator(new IntegerGenerator(78000, 79000))
				//
				// .column("stadt", DataType.VARCHAR).generator(new
				// NachnameGenerator())
				//
				.column("land", DataType.VARCHAR).generator(new DataGenerator("land"))
				//
				.build();

		associativeTable("buch_autor").column("buch_id", DataType.BIGINT).reference.foreign(buch).multiplicity("0..*")
				.column("autor_id", DataType.BIGINT).reference.foreign(autor).multiplicity("1..*").build();
	}
}
