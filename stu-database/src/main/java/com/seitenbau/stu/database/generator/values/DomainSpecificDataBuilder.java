package com.seitenbau.stu.database.generator.values;


import java.util.ArrayList;
import java.util.HashMap;

import com.seitenbau.stu.database.generator.hints.DomainSpecificDataHint;
import com.seitenbau.stu.database.generator.values.valuetypes.StringValue;
import com.seitenbau.stu.database.generator.values.valuetypes.Value;

public class DomainSpecificDataBuilder {

	public HashMap<String, ArrayList<DomainSpecificDataHint>> data = new HashMap<String, ArrayList<DomainSpecificDataHint>>();
		
	public void combine2Constraints(DomainSpecificDataHint c1, DomainSpecificDataHint c2){
		if(combine(c1, c2))
			combine(c2, c1);
	}
	
	private boolean combine(DomainSpecificDataHint c1, DomainSpecificDataHint c2){
		if(!data.containsKey(c1.getKey()) || !data.containsKey(c2.getKey()))
			return false;
					
		int index = data.get(c1.getKey()).indexOf(c1);
		if(index < 0){
			return false;
		}else{
			if(data.get(c1.getKey()).get(index).dataHashMap.containsKey(c2.getKey())){
				data.get(c1.getKey()).get(index).dataHashMap.get(c2.getKey()).add(c2);
			}else{
				ArrayList<DomainSpecificDataHint> al = new ArrayList<DomainSpecificDataHint>();
				al.add(c2);
				data.get(c1.getKey()).get(index).dataHashMap.put(c2.getKey(), al);
			}
			return true;
		}
	}
	
	public DomainSpecificDataHint addData(String key, String value){
		DomainSpecificDataHint dataConstraint = new DomainSpecificDataHint(null, key, new StringValue(value));
		
		if(!data.containsKey(key)){
			ArrayList<DomainSpecificDataHint> list = new ArrayList<DomainSpecificDataHint>();
			list.add(dataConstraint);
			data.put(key, list);
		}else{
			data.get(key).add(dataConstraint);
		}

		return dataConstraint;		
	}
	
	public boolean isValid(String key1, Comparable<?> value1, String key2, Comparable<?> value2){
		if(!data.containsKey(key1) || !data.containsKey(key2))
			return false;		
		
		if(String.class.isInstance(value1)){
			value1 = ((String) value1).replaceAll("\"", "");
		}
		
		if(String.class.isInstance(value2)){
			value2 = ((String) value2).replaceAll("\"", "");
		}
		
		ArrayList<DomainSpecificDataHint> list = data.get(key1);
		for(DomainSpecificDataHint entry : list){
			if(entry.getValue().equals(value1)){
				if(entry.dataHashMap.containsKey(key2)){
					for(DomainSpecificDataHint e2 : entry.dataHashMap.get(key2)){
						if(e2.getValue().equals(value2))
							return true;
					}
					return false;
				}else{
					return false;
				}
			}
		}
		
		return false;
	}
	
	public DomainSpecificDataBuilder() {
		
		// http://www.datendieter.de/item/Postleitzahlen-Datenbank_Deutschland
		
		DomainSpecificDataHint male = addData("gender", "male");
		DomainSpecificDataHint female = addData("gender", "female");
		
		DomainSpecificDataHint deutsch = addData("sprache", "deutsch");
		DomainSpecificDataHint englisch = addData("sprache", "englisch");
		DomainSpecificDataHint franz = addData("sprache", "franz�sisch");
		
		DomainSpecificDataHint robin = addData("firstname", "Robin");
		DomainSpecificDataHint adalgis = addData("firstname", "Adalgis"); 
		DomainSpecificDataHint adalie = addData("firstname", "Adalie");
		DomainSpecificDataHint adam = addData("firstname", "Adam");
		DomainSpecificDataHint aglaia = addData("firstname", "Aglaia");
		DomainSpecificDataHint alan = addData("firstname", "Alan");
		DomainSpecificDataHint sabrina = addData("firstname", "Sabrina");
		DomainSpecificDataHint siegmund = addData("firstname", "Siegmund");
		DomainSpecificDataHint tim = addData("firstname", "Tim");
		DomainSpecificDataHint tilly = addData("firstname", "Tilly");
		DomainSpecificDataHint jakob = addData("firstname", "Jakob");
		DomainSpecificDataHint tom = addData("firstname", "Tom");
		DomainSpecificDataHint jens = addData("firstname", "Jens");
		DomainSpecificDataHint rainer = addData("firstname", "Rainer");
		DomainSpecificDataHint thomas = addData("firstname", "Thomas");
		DomainSpecificDataHint juergen = addData("firstname", "J�rgen");
		
		DomainSpecificDataHint deutschland = addData("country", "Deutschland");
		DomainSpecificDataHint oesterreich = addData("country", "�sterreich");
		DomainSpecificDataHint schweiz = addData("country", "Schweiz");
		
		DomainSpecificDataHint berlin = addData("city", "Berlin");
		DomainSpecificDataHint wien = addData("city", "Wien");
		DomainSpecificDataHint bern = addData("city", "Bern");
		
		DomainSpecificDataHint schlossallee = addData("street", "Schlossallee");
		DomainSpecificDataHint parkstrasse = addData("steet", "Parkstra�e");
		DomainSpecificDataHint bahnhofstrasse = addData("street", "Bahnhofstra�e");
		DomainSpecificDataHint hauptstrasse = addData("street", "Hauptstra�e");
		DomainSpecificDataHint rathausstrasse = addData("street", "Rathausstra�e");
		
		
		
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
		// "\"Bj�rn\"", "\"Bodo\"", "\"Brandolf\"", "\"Brun\"", "\"Bruno\"",
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
		// "\"G�nther\"", "\"Gustav\"", "\"Hadwinga\"", "\"Hedda\"",
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
		// "\"Rudrich\"", "\"R�diger\"", "\"Rudger\"", "\"Ruger\"",
		// "\"Rutger\"", "\"Ruland\"", "\"Rudland\"", "\"Rudolf\"",
		// "\"Ralf\"", "\"Rolf\"", "\"Ruthard\"", "\"Runhild\"", "\"Runa\"",
		// "\"Salgard\"", "\"Sarhild\"", "\"Sarolf\"", "\"Saskia\"",
		// "\"Schwanhilde\"", "\"Swantje\"", "\"Sebald\"", "\"Siegbald\"",
		// "\"Siegfried\"", "\"Siggi\"", "\"Siegmund\"", "\"Sigmund\"",
		// "\"Sif\"", "\"Siv\"", "\"Sigga\"", "\"Siglinde\"", "\"Sieglinde\"",
		// "\"Sigmar\"", "\"Sigrid\"", "\"Sigrun\"", "\"Sindolf\"",
		// "\"Sisgards\"", "\"Solveig\"", "\"Sonnwinn\"", "\"Stilla\"",
		// "\"Sunhild\"", "\"Sunna\"", "\"Sunje\"", "\"Sunja\"", "\"Sven\"",
		// "\"Swidger\"", "\"Tankred\"", "\"firstname\"", "\"Tassilo\"",
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
