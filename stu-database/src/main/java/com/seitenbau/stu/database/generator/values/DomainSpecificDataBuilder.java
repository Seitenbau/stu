package com.seitenbau.stu.database.generator.values;


import java.util.ArrayList;
import java.util.HashMap;

public class DomainSpecificDataBuilder {

	public HashMap<String, ArrayList<DomainSpecificData>> data = new HashMap<String, ArrayList<DomainSpecificData>>();
		
	public void combine2Constraints(DomainSpecificData c1, DomainSpecificData c2){
		if(combine(c1, c2))
			combine(c2, c1);
	}
	
	private boolean combine(DomainSpecificData c1, DomainSpecificData c2){
		if(!data.containsKey(c1.getKey()) || !data.containsKey(c2.getKey()))
			return false;
					
		int index = data.get(c1.getKey()).indexOf(c1);
		if(index < 0){
			return false;
		}else{
			if(data.get(c1.getKey()).get(index).dataHashMap.containsKey(c2.getKey())){
				data.get(c1.getKey()).get(index).dataHashMap.get(c2.getKey()).add(c2);
			}else{
				ArrayList<DomainSpecificData> al = new ArrayList<DomainSpecificData>();
				al.add(c2);
				data.get(c1.getKey()).get(index).dataHashMap.put(c2.getKey(), al);
			}
			return true;
		}
	}
	
	public DomainSpecificData addData(String key, Comparable value){
		DomainSpecificData dataConstraint = new DomainSpecificData(key, value);
		
		if(!data.containsKey(key)){
			ArrayList<DomainSpecificData> list = new ArrayList<DomainSpecificData>();
			list.add(dataConstraint);
			data.put(key, list);
		}else{
			data.get(key).add(dataConstraint);
		}

		return dataConstraint;		
	}
	
	public DomainSpecificDataBuilder() {
		
		DomainSpecificData maennlich = addData("geschlecht", "männlich");
		DomainSpecificData weiblich = addData("geschlecht", "weiblich");
		
		DomainSpecificData deutsch = addData("sprache", "deutsch");
		DomainSpecificData englisch = addData("sprache", "englisch");
		DomainSpecificData franz = addData("sprache", "französisch");
		
		DomainSpecificData robin = addData("vorname", "Robin");
		DomainSpecificData adalgis = addData("vorname", "Adalgis"); 
		DomainSpecificData adalie = addData("vorname", "Adalie");
		DomainSpecificData adam = addData("vorname", "Adam");
		DomainSpecificData aglaia = addData("vorname", "Aglaia");
		DomainSpecificData alan = addData("vorname", "Alan");
		DomainSpecificData sabrina = addData("vorname", "Sabrina");
		DomainSpecificData siegmund = addData("vorname", "Siegmund");
		DomainSpecificData tim = addData("vorname", "Tim");
		DomainSpecificData tilly = addData("vorname", "Tilly");
		DomainSpecificData jakob = addData("vorname", "Jakob");
		DomainSpecificData tom = addData("vorname", "Tom");
		
		DomainSpecificData deutschland = addData("land", "Deutschland");
		DomainSpecificData oesterreich = addData("land", "Österreich");
		DomainSpecificData schweiz = addData("land", "Schweiz");
		
		DomainSpecificData schlossallee = addData("strasse", "Schlossallee");
		DomainSpecificData parkstrasse = addData("strasse", "Parkstraße");
		DomainSpecificData bahnhofstrasse = addData("strasse", "Bahnhofstraße");
		DomainSpecificData hauptstrasse = addData("strasse", "Hauptstraße");
		DomainSpecificData rathausstrasse = addData("strasse", "Rathausstraße");
		
		combine2Constraints(robin, maennlich);
		combine2Constraints(robin, weiblich);
		combine2Constraints(robin, englisch);
		
		combine2Constraints(adalgis, maennlich);
		combine2Constraints(adalgis, deutsch);
		combine2Constraints(adalgis, englisch);
		
		combine2Constraints(adalie, weiblich);
		combine2Constraints(adalie, deutsch);
		combine2Constraints(adalie, franz);
		
		combine2Constraints(adam, maennlich);
		combine2Constraints(adam, deutsch);
		combine2Constraints(adam, englisch);
		combine2Constraints(adam, franz);
		
		combine2Constraints(aglaia, weiblich);
		combine2Constraints(aglaia, deutsch);
		combine2Constraints(aglaia, englisch);		
		
		combine2Constraints(alan, maennlich);
		combine2Constraints(alan, deutsch);
		combine2Constraints(alan, englisch);			
		
		combine2Constraints(sabrina, weiblich);
		combine2Constraints(sabrina, deutsch);
		combine2Constraints(sabrina, englisch);	
		
		combine2Constraints(siegmund, maennlich);
		combine2Constraints(siegmund, deutsch);
		
		combine2Constraints(tim, maennlich);
		combine2Constraints(tim, deutsch);
		
		combine2Constraints(tilly, weiblich);
		combine2Constraints(tilly, englisch);
		
		combine2Constraints(jakob, maennlich);
		combine2Constraints(jakob, deutsch);
		
		combine2Constraints(tom, maennlich);
		combine2Constraints(tom, deutsch);
		combine2Constraints(tom, englisch);
		combine2Constraints(tom, franz);

		
		// OLD
		
		// private final String[] values = { "\"Adelberga\"", "\"Adelgard\"",
		// "\"Adelheid\"", "\"Adelmar\"", "\"Adolf\"", "\"Almut\"",
		// "\"Almuth\"", "\"Almudis\"", "\"Alrun\"", "\"Alfred\"",
		// "\"Alveradis\"", "\"Alsuna\"", "\"Amalie\"", "\"Amalia\"",
		// "\"Ansgar\"", "\"Ansger\"", "\"Ansmund\"", "\"Answald\"",
		// "\"Answin\"", "\"Arbogast\"", "\"Archibald\"", "\"Armin\"",
		// "\"Arnfried\"", "\"Arnhelm\"", "\"Arnold\"", "\"Arnulf\"",
		// "\"Arwin\"", "\"Astrid\"", "\"Aswin\"", "\"Balder\"", "\"Baldur\"",
		// "\"Balduin\"", "\"Baldwin\"", "\"Balko\"", "\"Baltrun\"",
		// "\"Benno\"", "\"Bernfried\"", "\"Bernger\"", "\"Bernhard\"",
		// "\"Bernd\"", "\"Bernold\"", "\"Bernulf\"", "\"Bernward\"",
		// "\"Bero\"", "\"Bert\"", "\"Berta\"", "\"Bertha\"", "\"Bertfried\"",
		// "\"Berthold\"", "\"Bertold\"", "\"Bertlinde\"", "\"Bertram\"",
		// "\"Bertrand\"", "\"Bertrun\"", "\"Bertwin\"", "\"Bihildis\"",
		// "\"Björn\"", "\"Bodo\"", "\"Brandolf\"", "\"Brun\"", "\"Bruno\"",
		// "\"Bruna\"", "\"Brunhilde\"", "\"Brunold\"", "\"Burghard\"",
		// "\"Burchard\"", "\"Burkhard\"", "\"Dagmar\"", "\"Dagny\"",
		// "\"Dagobert\"", "\"Dankmar\"", "\"Dankrad\"", "\"Dankrun\"",
		// "\"Dankward\"", "\"Degenar\"", "\"Degenhard\"", "\"Detlef\"",
		// "\"Detlev\"", "\"Dietbald\"", "\"Dietbert\"", "\"Dieter\"",
		// "\"Dietfried\"", "\"Dietger\"", "\"Diethard\"", "\"Diethelm\"",
		// "\"Diether\"", "\"Diethold\"", "\"Dietold\"", "\"Dietwald\"",
		// "\"Dietmar\"", "\"Ditmar\"", "\"Detmar\"", "\"Dietolf\"",
		// "\"Dietulf\"", "\"Dietram\"", "\"Dietrich\"", "\"Dietrun\"",
		// "\"Dietwin\"", "\"Durs\"", "\"Eberhard\"", "\"Eberwin\"",
		// "\"Edburga\"", "\"Edda\"", "\"Eduard\"", "\"Edelgard\"",
		// "\"Edeltraud\"", "\"Elke\"", "\"Edigna\"", "\"Eckbert\"",
		// "\"Eckbrecht\"", "\"Egbert\"", "\"Ekbert\"", "\"Ekkehard\"",
		// "\"Eckard\"", "\"Eckehard\"", "\"Eckehart\"", "\"Eckhard\"",
		// "\"Eggert\"", "\"Egmont\"", "\"Eila\"", "\"Einar\"", "\"Elfgard\"",
		// "\"Elfriede\"", "\"Elfrun\"", "\"Elmar\"", "\"Elvira\"",
		// "\"Emma\"", "\"Engelbert\"", "\"Erich\"", "\"Erik\"", "\"Erika\"",
		// "\"Erlgard\"", "\"Ernst\"", "\"Erwin\"", "\"Ewald\"", "\"Ewalt\"",
		// "\"Ewaldt\"", "\"Falko\"", "\"Fara\"", "\"Farold\"", "\"Fehild\"",
		// "\"Ferdinand\"", "\"Ferun\"", "\"Finja\"", "\"Folkward\"",
		// "\"Frank\"", "\"Frauke\"", "\"Freya\"", "\"Freia\"",
		// "\"Friederike\"", "\"Frigga\"", "\"Friedrich\"", "\"Fritz\"",
		// "\"Frigga\"", "\"Gaidemar\"", "\"Gandolf\"", "\"Gandulf\"",
		// "\"Gefion\"", "\"Gelsa\"", "\"Gerald\"", "\"Gerda\"",
		// "\"Gerhard\"", "\"Gerd\"", "\"Gerlinde\"", "\"Gertrud\"",
		// "\"Gerwin\"", "\"Giesela\"", "\"Gisela\"", "\"Gilbert\"",
		// "\"Gisbert\"", "\"Gismara\"", "\"Godelief\"", "\"Godwin\"",
		// "\"Goswin\"", "\"Gotmar\"", "\"Gottfried\"", "\"Gotthilf\"",
		// "\"Gottlieb\"", "\"Gottlob\"", "\"Gudrun\"", "\"Gunhild\"",
		// "\"Günther\"", "\"Gustav\"", "\"Hadwinga\"", "\"Hedda\"",
		// "\"Heidi\"", "\"Hagarun\"", "\"Hagen\"", "\"Hallgard\"",
		// "\"Harald\"", "\"Harold\"", "\"Hartmut\"", "\"Hedwig\"",
		// "\"Heidrun\"", "\"Heike\"", "\"Henrike\"", "\"Heinrich\"",
		// "\"Heinz\"", "\"Hendrik\"", "\"Heiko\"", "\"Helga\"", "\"Hella\"",
		// "\"Helgard\"", "\"Helmar\"", "\"Helmut\"", "\"Hellmut\"",
		// "\"Heilmut\"", "\"Herbert\"", "\"Herline\"", "\"Hermann\"",
		// "\"German\"", "\"Hermine\"", "\"Herta\"", "\"Hildegard\"",
		// "\"Hilke\"", "\"Hilde\"", "\"Holger\"", "\"Horst\"", "\"Hubert\"",
		// "\"Iduna\"", "\"Idun\"", "\"Ida\"", "\"Igor\"", "\"Ingmar\"",
		// "\"Ingo\"", "\"Inga\"", "\"Inge\"", "\"Ingrid\"", "\"Ingrun\"",
		// "\"Inka\"", "\"Irmbert\"", "\"Irmin\"", "\"Irmela\"", "\"Irma\"",
		// "\"Irmgard\"", "\"Irmina\"", "\"Irminar\"", "\"Irvin\"",
		// "\"Isbert\"", "\"Isolde\"", "\"Iwo\"", "\"Jelto\"", "\"Karl\"",
		// "\"Knut\"", "\"Konrad\"", "\"Kriemhild\"", "\"Kunheide\"",
		// "\"Kunna\"", "\"Kunolf\"", "\"Kunrada\"", "\"Kurt\"",
		// "\"Landerun\"", "\"Lando\"", "\"Landogar\"", "\"Leif\"",
		// "\"Leonhard\"", "\"Leonard\"", "\"Leopold\"", "\"Liebgard\"",
		// "\"Linda\"", "\"Lioba\"", "\"Lothar\"", "\"Ludwig\"",
		// "\"Luitberga\"", "\"Luithilde\"", "\"Malte\"", "\"Malvin\"",
		// "\"Melvin\"", "\"Manfred\"", "\"Marbod\"", "\"Margund\"",
		// "\"Markward\"", "\"Mathilde\"", "\"Merlinde\"", "\"Minna\"",
		// "\"Minnegard\"", "\"Mutbrecht\"", "\"Nanna\"", "\"Neidhart\"",
		// "\"Norbert\"", "\"Nordger\"", "\"Nordwin\"", "\"Norman\"",
		// "\"Northild\"", "\"Nordgard\"", "\"Nortrud\"", "\"Nortrun\"",
		// "\"Norwiga\"", "\"Notker\"", "\"Oda\"", "\"Odila\"",
		// "\"Odalinde\"", "\"Odin\"", "\"Olaf\"", "\"Ortlind\"",
		// "\"Orthilde\"", "\"Ortrun\"", "\"Ortwin\"", "\"Osgard\"",
		// "\"Oskar\"", "\"Osrun\"", "\"Ostara\"", "\"Oswald\"", "\"Oswin\"",
		// "\"Othilde\"", "\"Otmar\"", "\"Otto\"", "\"Otthein\"",
		// "\"Ottokar\"", "\"Raban\"", "\"Radolf\"", "\"Radulf\"", "\"Ralf\"",
		// "\"Ralph\"", "\"Rolf\"", "\"Rathard\"", "\"Ratmar\"",
		// "\"Raimund\"", "\"Raymund\"", "\"Reimund\"", "\"Rainbert\"",
		// "\"Reinbert\"", "\"Rainer\"", "\"Rainher\"", "\"Reiner\"",
		// "\"Reinher\"", "\"Raingard\"", "\"Eeingard\"", "\"Reinmar\"",
		// "\"Raimar\"", "\"Raimer\"", "\"Rainmar\"", "\"Reimar\"",
		// "\"Reimer\"", "\"Reinfried\"", "\"Rainfried\"", "\"Reinhard\"",
		// "\"Rainhard\"", "\"Reinhild\"", "\"Raginhild\"", "\"Rainhild\"",
		// "\"Rainhilde\"", "\"Reinhilde\"", "\"Reinhold\"", "\"Rainald\"",
		// "\"Rainhold\"", "\"Raynald\"", "\"Rainulf\"", "\"Rainolf\"",
		// "\"Reinolf\"", "\"Reinulf\"", "\"Reinward\"", "\"Rainward\"",
		// "\"Rambert\"", "\"Rambo\"", "\"Rambod\"", "\"Rambold\"",
		// "\"Rambald\"", "\"Ramgar\"", "\"Randolf\"", "\"Randulf\"",
		// "\"Randwig\"", "\"Rango\"", "\"Ranmar\"", "\"Rasso\"",
		// "\"Richard\"", "\"Richardis\"", "\"Richardse\"", "\"Richbert\"",
		// "\"Richgard\"", "\"Richwin\"", "\"Rigmor\"", "\"Rinelda\"",
		// "\"Robert\"", "\"Rupert\"", "\"Ruppert\"", "\"Rupprecht\"",
		// "\"Ruprecht\"", "\"Rochbert\"", "\"Rochold\"", "\"Roland\"",
		// "\"Romilda\"", "\"Romhild\"", "\"Rumhild\"", "\"Rumold\"",
		// "\"Roswin\"", "\"Roswitha\"", "\"Rudgar\"", "\"Roderich\"",
		// "\"Rudrich\"", "\"Rüdiger\"", "\"Rudger\"", "\"Ruger\"",
		// "\"Rutger\"", "\"Ruland\"", "\"Rudland\"", "\"Rudolf\"",
		// "\"Ralf\"", "\"Rolf\"", "\"Ruthard\"", "\"Runhild\"", "\"Runa\"",
		// "\"Salgard\"", "\"Sarhild\"", "\"Sarolf\"", "\"Saskia\"",
		// "\"Schwanhilde\"", "\"Swantje\"", "\"Sebald\"", "\"Siegbald\"",
		// "\"Siegfried\"", "\"Siggi\"", "\"Siegmund\"", "\"Sigmund\"",
		// "\"Sif\"", "\"Siv\"", "\"Sigga\"", "\"Siglinde\"", "\"Sieglinde\"",
		// "\"Sigmar\"", "\"Sigrid\"", "\"Sigrun\"", "\"Sindolf\"",
		// "\"Sisgards\"", "\"Solveig\"", "\"Sonnwinn\"", "\"Stilla\"",
		// "\"Sunhild\"", "\"Sunna\"", "\"Sunje\"", "\"Sunja\"", "\"Sven\"",
		// "\"Swidger\"", "\"Tankred\"", "\"Vorname\"", "\"Tassilo\"",
		// "\"Teuderun\"", "\"Thilo\"", "\"Thor\"", "\"Thorbrand\"",
		// "\"Thorleif\"", "\"Thorsten\"", "\"Throals\"", "\"Thusnelda\"",
		// "\"Tilrun\"", "\"Traute\"", "\"Trautwin\"", "\"Trudildis\"",
		// "\"Trude\"", "\"Tyra\"", "\"Uland\"", "\"Ulrich\"", "\"Udo\"",
		// "\"Ulbert\"", "\"Ulf\"", "\"Ulrike\"", "\"Ute\"", "\"Uta\"",
		// "\"Uwe\"", "\"Uwo\"", "\"Vanadis\"", "\"Volker\"", "\"Volkher\"",
		// "\"Volkmar\"", "\"Volkwin\"", "\"Volkward\"", "\"Walburga\"",
		// "\"Walda\"", "\"Waldemar\"", "\"Walter\"", "\"Walther\"",
		// "\"Waltraud\"", "\"Werner\"", "\"Wibke\"", "\"Wiborg\"",
		// "\"Widogard\"", "\"Widukind\"", "\"Wigberg\"", "\"Wilberta\"",
		// "\"Wilfriede\"", "\"Wilgard\"", "\"Wilhelm\"", "\"Willibald\"",
		// "\"Wiltraud\"", "\"Wiltraut\"", "\"Winimar\"", "\"Wisgard\"",
		// "\"Wolfgang\"", "\"Wolfram\"", "\"Wunna\"" };
	}
}
