package model;

import com.seitenbau.stu.database.generator.DataType;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.TableBuilder;
import com.seitenbau.stu.database.generator.values.DomainGenerator;
import com.seitenbau.stu.database.generator.values.IntegerGenerator;
import com.seitenbau.stu.database.generator.values.constraints.functional.AddConstraint;
import com.seitenbau.stu.database.generator.values.constraints.functional.ModConstraint;
import com.seitenbau.stu.database.generator.values.constraints.logical.GreaterConstraint;
import com.seitenbau.stu.database.generator.values.valuetypes.IntValue;

public class BookDatabaseModel extends DatabaseModel {

	public BookDatabaseModel() {
		database("BookDatabase");
		packageName("com.seitenbau.stu.bookdatabase.model");
		// enableTableModelClassesGeneration();
		// disableTableDSLGeneration();
		infinite(1);
		domainDataSource(new BookDomainData());

		TableBuilder address = table("address");
		TableBuilder author = table("author");
		TableBuilder book = table("book");
		TableBuilder copy = table("copy");
		TableBuilder publisher = table("publisher");
		TableBuilder right = table("right");
		TableBuilder section = table("section");
		TableBuilder user = table("user");

		constraint(new ModConstraint("author.birthyear", new IntValue(4),
				new IntValue(2)));

		constraint(new AddConstraint(new IntValue(11), new IntValue(50), "address.number"));
		constraint(new GreaterConstraint("author.memberyear",
				"author.birthyear"));

		address.column("id", DataType.BIGINT) //
				.defaultIdentifier() //
				.autoInvokeNext() //
				//
				.column("street", DataType.VARCHAR) //
				.generator(new DomainGenerator("street")) //
				//
				.column("number", DataType.INTEGER) //
				.generator(new IntegerGenerator(1, 99)) //
				//
				.column("postalcode", DataType.INTEGER) //
//				.generator(new DomainGenerator("postalcode")) //
//				.constraint().domain("address.city") //
				.generator(new IntegerGenerator(70000, 80000)) //
				.constraint().range(new IntValue(78100), new IntValue(78200)) //
				//
				.column("city", DataType.VARCHAR) //
				.generator(new DomainGenerator("city")) //
//				.constraint().domain("address.country") //
				//
				.column("country", DataType.VARCHAR) //
				.generator(new DomainGenerator("country")) //
				//
				.build();

		author //
		.column("id", DataType.BIGINT) //
				.defaultIdentifier() //
				.autoInvokeNext() //
				//
				.column("firstname", DataType.VARCHAR) //
				.generator(new DomainGenerator("firstname")) //
				.constraint().unique() //
				//
				.column("lastname", DataType.VARCHAR) //
				.generator(new DomainGenerator("lastname")) //
				//
				.column("gender", DataType.VARCHAR) //
				.generator(new DomainGenerator("gender")) //
				.constraint().domain("author.firstname") //
				//
				.column("birthyear", DataType.INTEGER) //
				.generator(new IntegerGenerator(1980, 2020)) //

				//
				.column("memberyear", DataType.INTEGER) //
//				.generator(new IntegerGenerator(1980, 2000)) //
				.constraint().range(new IntValue(1980), "author.lastlogin")
				// 
				.constraint().greaterEqual("author.birthyear") //

				.column("lastlogin", DataType.INTEGER) //
				.generator(new IntegerGenerator(1980, 2000)) //
//				.constraint().greater("author.memberyear") //

				.column("book", DataType.INTEGER) //
				.generator(new IntegerGenerator(0, 100)) //
				.constraint().range(new IntValue(1), new IntValue(50)) //
				.constraint().unique() //
				//
				.column("addressid", DataType.BIGINT) //
		.reference.local.name("belongsTo").description("").foreign(address)
				.name("hasMembers").description("") //
				//
				.build();

		book //
		.column("id", DataType.BIGINT)
				//
				.defaultIdentifier()
				//
				.autoInvokeNext()
				//
				.column("name", DataType.VARCHAR)
				.generator(new DomainGenerator("book"))
				//
				.column("price", DataType.INTEGER)
				.generator(new IntegerGenerator(1, 20))
				//
				.column("publisher", DataType.BIGINT) //
		.reference.local.foreign(publisher.ref("id")).multiplicity("0..*") //
				.build();

		copy //
		.column("id", DataType.BIGINT) //
				.defaultIdentifier() //
				.autoInvokeNext() //
				//
				.column("instance", DataType.INTEGER) //
				.generator(new IntegerGenerator(1, 100)) //
				//
				.column("bookid", DataType.BIGINT).reference //
		.local.name("belongsTo").description("").foreign(book)
				.name("hasMembers").description("")//
				//
				.build();

		publisher //
				.column("id", DataType.BIGINT) //
				.defaultIdentifier() //
				.autoInvokeNext() //
				//
				.column("name", DataType.VARCHAR) //
				.generator(new DomainGenerator("publisher")) //
				//
				.column("addressid", DataType.BIGINT).reference //
		.local.name("belongsTo").description("").foreign(address)
				.name("hasMembers").description("")//
				.build();

		right //
		.column("id", DataType.BIGINT) //
				.defaultIdentifier() //
				.autoInvokeNext() //
				//
				.column("name", DataType.VARCHAR) //
				.generator(new DomainGenerator("publisher")) //
				.build();

		section //
		.column("id", DataType.BIGINT) //
				//
				.defaultIdentifier() //
				//
				.autoInvokeNext() //
				//
				.column("name", DataType.VARCHAR) //
				//
				.column("floor", DataType.INTEGER) //
				.constraint().range(new IntValue(0), new IntValue(3)) //
				//
				.build();

		user //
		.column("id", DataType.BIGINT) //
				//
				.defaultIdentifier() //
				//
				.autoInvokeNext() //
				//
				.column("firstname", DataType.VARCHAR) //
				.generator(new DomainGenerator("firstname")) //
				//
				.column("lastname", DataType.VARCHAR) //
				.generator(new DomainGenerator("lastname")) //
				// EMail Adresse
				//
				.column("cardnumber", DataType.INTEGER) //
				.generator(new IntegerGenerator(50000, 4999999)) //
				.constraint().unique() //
				//
				.column("birthday", DataType.INTEGER) //
				.generator(new IntegerGenerator(1900, 2015)) //
				//
				.column("lastlogin", DataType.INTEGER) //
				.generator(new IntegerGenerator(2000, 2015)) //
				.constraint().greater(new IntValue(2012)) //
				//
				.column("sessionid", DataType.VARCHAR) //
				//
				.column("rightid", DataType.BIGINT) //
		.reference.local.name("belongsTo").description("").foreign(right)
				.name("hasMembers").description("") //
				//
				.build();

		associativeTable("bookauthor").column("bookid", DataType.BIGINT).reference
				.foreign(book).multiplicity("0..*")
				.column("authorid", DataType.BIGINT).reference.foreign(author)
				.multiplicity("1..*").build();

		associativeTable("bookuser").column("bookid", DataType.BIGINT).reference
				.foreign(book).multiplicity("0..*")
				.column("userid", DataType.BIGINT).reference.foreign(user)
				.multiplicity("1..*").build();
	}
}
// constraint(new DomainSpecificDataConstraint("autor.vorname",
// "autor.geschlecht"));
// constraint(new DomainSpecificDataConstraint("autor.vorname",
// "autor.sprache"));
// constraint(new UniqueConstraint("autor.vorname"));
// //
// constraint(new RangeConstraint("adresse.nummer", 1, 50));
// constraint(new RangeConstraint("autor.nachnamelaenge", 1, 40));
// //
// // constraint(new EqualConstraint("autor.summebuecher", new IntValue(77)));
// // constraint(new EqualConstraint("autor.summebuecher",
// "autor.anzahlbuecher"));
// constraint(new GreaterEqualConstraint("autor.lastlogin",
// "autor.mitgliedseit"));
// constraint(new GreaterEqualConstraint("autor.mitgliedseit",
// "autor.geburtsjahr"));
//
// constraint(new AddConstraint("autor.nachnamelaenge", new IntValue(20), new
// IntValue(50)));
// // constraint(new AddConstraint("autor.mitgliedseit", "autor.geburtsjahr",
// new IntValue(4000)));
// constraint(new MultiConstraint("autor.anzahlbuecher", "autor.summebuecher",
// new IntValue(3626)));
// // constraint(new EqualConstraint("autor.nachnamelaenge", new IntValue(20)));

// constraint(new SubConstraint("adresse.nummer", new IntValue(20), new
// IntValue(30)));

// ///////////////////////////////////////////////////////////////////////////////////////

// Conststaints
// constraint(new ExpressionConstraint("autor.mitgliedseit",
// "autor.mitgliedseit >= autor.geburtsjahr + 16 && autor.mitgliedseit % 4 == 0",
// "autor.geburtsjahr", "autor.mitgliedseit"));
//
// constraint(new ExpressionConstraint("autor.mitgliedseit",
// "autor.mitgliedseit >= autor.geburtsjahr + 16", "autor.geburtsjahr",
// "autor.mitgliedseit"));

// constraint(new ExpressionConstraint("autor.lastlogin",
// "autor.lastlogin >= autor.mitgliedseit",
// "autor.lastlogin", "autor.mitgliedseit"));

// constraint(new UniqueConstraint("autor.geburtsjahr"));

// constraint(new UniqueConstraint("autor.nachname"));

// constraint(new UniqueConstraint("adresse.nummer"));
// constraint(new EqualConstraint("autor.anzahlbuecher", "autor.summebuecher"));
// constraint(new NotEqualConstraint("autor.mitgliedseit", new IntValue(2008)));
// constraint(new NotEqualConstraint("autor.mitgliedseit", "autor.lastlogin"));
// constraint(new SmallerEqualConstraint("autor.mitgliedseit",
// "autor.lastlogin"));

// constraint(new GreaterEqualConstraint("autor.nachnamelaenge", new
// DoubleValue(44.)));
// constraint(new UniqueConstraint("autor.geburtsjahr"));

// constraint(new SmallerConstraint("autor.mitgliedseit", "autor.geburtsjahr"));
// constraint(new SmallerConstraint("autor.lastlogin", "autor.mitgliedseit"));

// ----------------------------------

// constraint(new SuperConstraint(String column, String expression,
// String...targets))

// constraint(new GlobalConstraint(
// "autor.land",
// "autor.land == adresse.land && !(true == false) && -12345 == (-12345 * 2) / 2 && -123.45 == -123.45 && 8999999999999999999 == 8999999999999999999 && 195.23 == 95.13 + 100.1 && (true || false && true) == true && false != true && true && 5 > 4 && 'Test_String' == 'Test_String' && 50 % 30 == 20 && 20 / 10 == 2",
// "adresse.land"));
// constraint(new ExpressionConstraint("autor.mitgliedseit",
// "autor.mitgliedseit >= autor.geburtsjahr", "autor.geburtsjahr",
// "autor.mitgliedseit"));

// constraint(new GlobalConstraint("autor.anzahl_buecher",
// "autor.anzahl_buecher == COUNT(buch)"));
// constraint(new GlobalConstraint("buch.anzahl",
// "buch.anzahl == COUNT(buch)"));
// constraint(new GlobalConstraint("autor.vorname",
// "autor.vorname <= 'Robin'"));
// constraint(new GlobalConstraint("autor.summe_buecher",
// "autor.summe_buecher == SUM(buch.preis)"));
// //constraint(new SuperConstraint("autor.nachname_laenge",
// "autor.nachname_laenge == LENGTH(autor.nachname)", "autor.nachname"));
// constraint(new GlobalConstraint("autor.nachname_laenge",
// "autor.nachname_laenge == LENGTH(adresse.land)", "adresse.land"));
//

// constraint(new RConstraint("autor.geburtsjahr", 1950, 2015));
// constraint(new RConstraint("autor.mitgliedseit", 1985, 2015));
// constraint(new RConstraint("autor.lastlogin", 1997, 2015));

// constraint(new SolverConstraint(Constraint.XplusClteqZ, "autor.geburtsjahr",
// 16, "autor.mitglied_seit"));

// constraint(new SuperConstraint("autor.geschlecht",
// "this > 1 && autor.geschlecht == autor.geschlecht[1]"));
// constraint(new SuperConstraint("autor.nachname",
// "autor.nachname != autor.nachname[0]")
// constraint(new SuperConstraint("autor.nachname",
// "autor.nachname != autor.nachname[this-1]")
// constraint(new SuperConstraint("autor.nachname", "(adresse ... == null)?
// asdas : asdasd)

// constraint(new CompareValueConstraint(CompareType.EQUAL),
// "autor.land", new
// CompareValueConstraint(CompareType.EQUAL),"adresse.land");
// constraint(constraint, "land",//
// "--person2adresse[aktiv=1]-->adresse.land")

// constraint(EqualConstraint("autor.land", "adresse.land")

// .constraint(new DataConstraint("vorname"), "vorname", new
// DataConstraint("geschlecht"), "geschlecht")
// .constraint(new DataConstraint("vorname"), "vorname", new
// DataConstraint("sprache"), "sprache")
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

// .constraint(new RangeConstraint(1900, 2015), "geburtsjahr")
// .constraint(new RangeConstraint(1985, 2015), "mitglied_seit")
// .constraint(new RangeConstraint(1997, 2015), "last_login")

// .constraint("geburtsjahr", new Range(1900, 2015));
// .constraint("mitglied", new Range(1990, 2015));
// .constraint("geburtsjahr",
// "geburtsjahr <= "mitglied", "mitglied");

