package model;

import com.seitenbau.stu.database.generator.hints.DomainDataHint;
import com.seitenbau.stu.database.generator.values.DomainData;

public class BookDomainData extends DomainData {

	@Override
	protected void initData() {

		DomainDataHint male = addData("gender", "male");
		DomainDataHint female = addData("gender", "female");

		DomainDataHint deutsch = addData("speech", "deutsch");
		DomainDataHint englisch = addData("speech", "englisch");
		DomainDataHint franz = addData("speech", "französisch");

		DomainDataHint robin = addData("firstname", "Robin");
		DomainDataHint adalgis = addData("firstname", "Adalgis");
		DomainDataHint adalie = addData("firstname", "Adalie");
		DomainDataHint adam = addData("firstname", "Adam");
		DomainDataHint aglaia = addData("firstname", "Aglaia");
		DomainDataHint alan = addData("firstname", "Alan");
		DomainDataHint sabrina = addData("firstname", "Sabrina");
		DomainDataHint siegmund = addData("firstname", "Siegmund");
		DomainDataHint tim = addData("firstname", "Tim");
		DomainDataHint tilly = addData("firstname", "Tilly");
		DomainDataHint jakob = addData("firstname", "Jakob");
		DomainDataHint tom = addData("firstname", "Tom");
		DomainDataHint jens = addData("firstname", "Jens");
		DomainDataHint rainer = addData("firstname", "Rainer");
		DomainDataHint thomas = addData("firstname", "Thomas");
		DomainDataHint juergen = addData("firstname", "Jürgen");

		DomainDataHint deutschland = addData("country", "Deutschland");
		DomainDataHint oesterreich = addData("country", "Österreich");
		DomainDataHint schweiz = addData("country", "Schweiz");

		DomainDataHint berlin = addData("city", "Berlin");
		DomainDataHint wien = addData("city", "Wien");
		DomainDataHint bern = addData("city", "Bern");

		DomainDataHint schlossallee = addData("street", "Schlossallee");
		DomainDataHint parkstrasse = addData("steet", "Parkstraße");
		DomainDataHint bahnhofstrasse = addData("street",
				"Bahnhofstraße");
		DomainDataHint hauptstrasse = addData("street", "Hauptstraße");
		DomainDataHint rathausstrasse = addData("street",
				"Rathausstraße");

		DomainDataHint bibel = addData("book", "Bibel");
		DomainDataHint odyssee = addData("book", "Odyssee");
		DomainDataHint apologie = addData("book", "Apologie");
		DomainDataHint daphnis = addData("book", "Daphnis und Chloe");
		DomainDataHint Bekenntnisse = addData("book", "Bekenntnisse");
		DomainDataHint tristan = addData("book", "Tristan");
		DomainDataHint utopia = addData("book", "Utopia");
		DomainDataHint essais = addData("book", "Essais");
		DomainDataHint candide = addData("book", "Candide");
		DomainDataHint siebenkaes = addData("book", "Siebenkäs");

		DomainDataHint mueller = addData("lastname", "Müller");
		DomainDataHint schmidt = addData("lastname", "Schmidt");
		DomainDataHint schneider = addData("lastname", "Schneider");
		DomainDataHint fischer = addData("lastname", "Fischer");
		DomainDataHint weber = addData("lastname", "Weber");
		DomainDataHint meyer = addData("lastname", "Meyer");
		DomainDataHint wagner = addData("lastname", "Wagner");
		DomainDataHint becker = addData("lastname", "Becker");
		DomainDataHint schulz = addData("lastname", "Schulz");
		DomainDataHint hoffmann = addData("lastname", "Hoffmann");

		DomainDataHint a1verlag = addData("publisher", "A1 Verlag");
		DomainDataHint akademieverlag = addData("publisher",
				"Akademie Verlag");
		DomainDataHint alexanderverlag = addData("publisher",
				"Alexander Verlag");
		DomainDataHint benteli = addData("publisher", "Benteli");
		DomainDataHint benziger = addData("publisher", "Benziger");
		DomainDataHint enkeverlag = addData("publisher", "Enke Verlag");
		DomainDataHint expertverlag = addData("publisher",
				"expert verlag");
		DomainDataHint friedrichverlag = addData("publisher",
				"Friedrich Verlag");
		DomainDataHint gmeinerverlag = addData("publisher",
				"Gmeiner-Verlag");
		DomainDataHint kindlerverlag = addData("publisher",
				"Kindler Verlag");

		combine2Constraints(robin, male);
		combine2Constraints(robin, female);
		combine2Constraints(robin, englisch);

		combine2Constraints(adalgis, male);
		combine2Constraints(adalgis, deutsch);
		combine2Constraints(adalgis, englisch);

		combine2Constraints(adalie, female);
		combine2Constraints(adalie, deutsch);
		combine2Constraints(adalie, franz);

		combine2Constraints(adam, male);
		combine2Constraints(adam, deutsch);
		combine2Constraints(adam, englisch);
		combine2Constraints(adam, franz);

		combine2Constraints(aglaia, female);
		combine2Constraints(aglaia, deutsch);
		combine2Constraints(aglaia, englisch);

		combine2Constraints(alan, male);
		combine2Constraints(alan, deutsch);
		combine2Constraints(alan, englisch);

		combine2Constraints(sabrina, female);
		combine2Constraints(sabrina, deutsch);
		combine2Constraints(sabrina, englisch);

		combine2Constraints(siegmund, male);
		combine2Constraints(siegmund, deutsch);

		combine2Constraints(tim, male);
		combine2Constraints(tim, deutsch);

		combine2Constraints(tilly, female);
		combine2Constraints(tilly, englisch);

		combine2Constraints(jakob, male);
		combine2Constraints(jakob, deutsch);

		combine2Constraints(tom, male);
		combine2Constraints(tom, deutsch);
		combine2Constraints(tom, englisch);
		combine2Constraints(tom, franz);

		combine2Constraints(jens, male);
		combine2Constraints(jens, deutsch);
		combine2Constraints(jens, englisch);

		combine2Constraints(rainer, male);
		combine2Constraints(rainer, deutsch);
		combine2Constraints(rainer, englisch);

		combine2Constraints(thomas, male);
		combine2Constraints(thomas, deutsch);
		combine2Constraints(thomas, englisch);

		combine2Constraints(juergen, male);
		combine2Constraints(juergen, deutsch);
		combine2Constraints(juergen, englisch);
		
		
		
		DomainDataHint plz78315 = addData("postalcode", "78315");
		DomainDataHint radolfzell = addData("city", "Radolfzell");
		combine2Constraints(plz78315, radolfzell);
		combine2Constraints(radolfzell, deutschland);
		
		DomainDataHint plz78462 = addData("postalcode", "78462");
		DomainDataHint plz78464 = addData("postalcode", "78464");
		DomainDataHint plz78465 = addData("postalcode", "78465");
		DomainDataHint plz78467 = addData("postalcode", "78467");
		DomainDataHint konstanz = addData("city", "Konstanz");
		combine2Constraints(plz78462, konstanz);
		combine2Constraints(plz78464, konstanz);
		combine2Constraints(plz78465, konstanz);
		combine2Constraints(plz78467, konstanz);
		combine2Constraints(konstanz, deutschland);
		
		DomainDataHint plz78476 = addData("postalcode", "78476");
		DomainDataHint allensbach = addData("city", "Allensbach");
		combine2Constraints(plz78476, allensbach);
		combine2Constraints(allensbach, deutschland);

	}
}
