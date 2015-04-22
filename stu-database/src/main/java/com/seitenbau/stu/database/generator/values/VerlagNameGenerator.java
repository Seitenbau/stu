package com.seitenbau.stu.database.generator.values;

import java.util.Random;

import com.seitenbau.stu.database.generator.data.EntityBlueprint;

public class VerlagNameGenerator extends ValueGenerator {

	private Random random;	

	@Override
	public void initialize(long seed) {
		random = new Random(seed);
		
		values = new String[]{ "\"A1 Verlag\"", "\"Abenteuer Medien Verlag\"", "\"ACABUS Verlag\"",
				"\"Achterbahn Verlag\"", "\"Aisthesis Verlag\"", "\"Akademie Verlag\"",
				"\"Akademische Arbeitsgemeinschaft Verlag\"", "\"Akademische Druck- und Verlagsanstalt\"",
				"\"Verlag Karl Alber\"", "\"Alexander Verlag\"", "\"Alibaba Verlag\"", "\"Alibri Verlag\"",
				"\"Allitera Verlag\"", "\"Amadeus Verlag\"", "\"Amalthea Signum Verlag\"", "\"Ammann Verlag\"",
				"\"Schulbuchverlag Anadolu\"", "\"Andiamo Verlag\"", "\"AOK-Verlag\"", "\"AOL-Verlag\"",
				"\"Appelhans Verlag\"", "\"Apprimus Wissenschaftsverlag\"", "\"Ararat-Verlag\"",
				"\"Arbeitsgemeinschaft für zeitgemäßes Bauen e. V.\"", "\"Arche Verlag\"", "\"Arco Verlag\"",
				"\"Arena Verlag\"", "\"Argon Verlag\"", "\"Argument Verlag\"", "\"Arovell Verlag\"", "\"Arquus Verlag\"",
				"\"Ars Edition\"", "\"Das Arsenal\"", "\"AS Verlag\"", "\"Aschendorff Verlag\"", "\"Assoziation A\"",
				"\"aLiVe, Assoziation Linker Verlage\"", "\"AT Verlag\"", "\"ATHENA-Verlag\"", "\"Athesia\"",
				"\"Atlas Verlag\"", "\"Atlantis Verlag\"", "\"Der Audio Verlag(DAV)\"", "\"Auer Verlag\"",
				"\"Carl-Auer-Verlag\"", "\"Aufbau Verlagsgruppe\"", "\"Aula-Verlag\"", "\"Aviva Verlag\"",
				"\"AZ Medien Gruppe\"", "\"b books\"", "\"J.P. Bachem Verlag\"", "\"Badischer Verlag\"",
				"\"Bajazzo Verlag\"", "\"C. Bange Verlag\"", "\"Verlag Barbara Budrich\"", "\"Bassermann\"",
				"\"Bärenreiter-Verlag\"", "\"BASTEI Lübbe Taschenbücher(BLT)\"", "\"Bauer Verlagsgruppe\"",
				"\"Bayard Media\"", "\"be.bra verlag\"", "\"Verlag C. H. Beck\"", "\"Musikverlag M. P. Belaieff\"",
				"\"Belser-Verlag\"", "\"Beltz-Verlag\"", "\"Benteli\"", "\"Benziger\"",
				"\"Bergstadtverlag Wilhelm Gottlieb Korn\"", "\"Berlin Verlag\"", "\"Berlin Story Verlag\"",
				"\"Bernstein-Verlag\"", "\"Bertelsmann\"", "\"C. Bertelsmann Verlag\"", "\"W. Bertelsmann Verlag\"",
				"\"Bertz + Fischer Verlag\"", "\"Beuth Verlag\"", "\"BEV-Kartenverlag\"", "\"Bibliographisches Institut\"",
				"\"Bielefelder Verlag(BVA)\"", "\"bilgerverlag\"", "\"BIOCOM AG\"", "\"Birkhäuser Verlag\"",
				"\"Black Ink Verlag\"", "\"Blanvalet\"", "\"J. G. Bläschke Verlag\"", "\"Blumenbar Verlag\"",
				"\"BLV Verlag\"", "\"Bohem Press\"", "\"Böhlau Verlag\"", "\"Georg Bondi Verlag\"",
				"\"Bonnier Media Holding\"", "\"Books on Demand\"", "\"Born-Verlag\"", "\"BPX Edition\"",
				"\"Christian Brandstätter Verlagsgesellschaft\"", "\"Verlag Braumüller\"", "\"C. M. Brendle Verlag\"",
				"\"Joh. Brendow & Sohn Verlag\"", "\"F.A. Brockhaus AG\"", "\"R. Brockhaus Verlag\"",
				"\"Bruno Gmünder Verlag\"", "\"Brunnen Verlag\"", "\"BS-Verlag-Rostock\"", "\"Budrich UniPress\"",
				"\"Bund-Verlag\"", "\"BundesanzeigerVerlag\"", "\"Bundes-Verlag\"", "\"Bunte Raben Verlag\"",
				"\"Burda Verlag\"", "\"Busche Verlagsgesellschaft\"", "\"Helmut Buske Verlag\"", "\"ça ira Verlag\"",
				"\"Calwer Verlag\"", "\"Campus Verlag\"", "\"Carlsen Verlag\"", "\"Castrum Peregrini Uitgeverij\"",
				"\"cbj Kinder & Jugendbücher\"", "\"Centaurus Verlag\"", "\"Christliche Literatur-Verbreitung\"",
				"\"Christliche Schriftenverbreitunge. V.\"", "\"Chronos Verlag\"", "\"Claassen-Verlag\"",
				"\"Compact Verlag\"", "\"Computer- und Literaturverlag\"", "\"Computec Media AG\"",
				"\"Connewitzer Verlagsbuchhandlung\"", "\"Conte Verlag\"", "\"context verlag Augsburg\"",
				"\"Coppenrath Verlag\"", "\"Cornelsen Verlag\"", "\"Corvinus Presse\"", "\"Cosmos-Verlag\"",
				"\"Cotta'sche Verlagsbuchhandlung\"", "\"Cross Cult\"", "\"Czernin Verlag\"", "\"Verlag Dashöfer\"",
				"\"Data Becker\"", "\"Walter de Gruyter\"", "\"Delius Klasing Verlag\"", "\"Delphin-Verlag\"",
				"\"Delphin Verlag\"", "\"Deubner Verlag\"", "\"Verlag Harri Deutsch\"",
				"\"Verlag Deutsche Polizeiliteratur\"", "\"Deutsche Verlags-Anstalt (DVA)\"",
				"\"Verlag für die Deutsche Wirtschaft\"", "\"Deutscher Apotheker Verlag\"", "\"Deutscher Ärzte-Verlag\"",
				"\"Deutscher Fachverlag\"", "\"Deutscher Kunstverlag\"", "\"Deutscher Literatur Verlag\"",
				"\"Deutscher SparkassenverlagGmbH\"", "\"Deutscher Taschenbuch Verlag\"",
				"\"Deutscher Universitäts-Verlag\"", "\"Deutscher Verlag der Wissenschaften\"", "\"\"",
				"\"Deutscher Wissenschafts-Verlag\"", "\"Diaphanes Verlag\"", "\"DIE NEUE SACHLICHKEIT\"",
				"\"Verlag Moritz Diesterweg\"", "\"Dieterich'sche Verlagsbuchhandlung\"", "\"Dingsda-Verlag\"",
				"\"Diogenes Verlag\"", "\"Diplomica Verlag\"", "\"Directmedia Publishing\"", "\"Distanz Verlag\"",
				"\"Dittrich Verlag\"", "\"Verlagsedition Dittmer\"", "\"Verlag Dohr\"", "\"Dölling und Galitz Verlag\"",
				"\"Domino Verlag\"", "\"Verlag Donat & Temmen\"", "\"Donat Verlag & Antiquariat\"", "\"Dörlemann Verlag\"",
				"\"Verlagsgruppe Dornier\"", "\"Draksal Fachverlag\"", "\"Drei Eichen Verlag\"",
				"\"Cecilie-Dressler-Verlag\"", "\"Droemer Knaur\"", "\"Droschl\"", "\"Droste\"", "\"DSZ-Verlag\"",
				"\"M. DuMont SchaubergVerlag\"", "\"Echter Verlag\"", "\"Echtzeit Verlag\"", "\"Econ Verlag\"",
				"\"Ecowin Verlag\"", "\"Edition Antaios\"", "\"Edition AV\"", "\"Edition Büchergilde\"",
				"\"Edition Das fröhliche Wohnzimmer\"", "\"Edition Delta\"", "\"Edition Epoca\"", "\"Edition Erdmann\"",
				"\"Edition Hamouda\"", "\"Edition Keiper\"", "\"edition kürbis\"", "\"Edition Leipzig\"",
				"\"Edition Nautilus\"", "\"edition ost\"", "\"Edition Peters\"", "\"Edition Phantasia\"",
				"\"Edition Raetia\"", "\"Edition Rugerup\"", "\"Edition Ruprecht\"", "\"Edition Sigma\"",
				"\"edition sisyphos\"", "\"edition taberna kritika\"", "\"Edition Temmen\"",
				"\"Edition Tiamat (Verlag Klaus Bittermann)\"", "\"Edition Va Bene\"", "\"Edition YE\"",
				"\"Egmont Ehapa Verlag\"", "\"Eichborn Verlag\"", "\"Elfenbein Verlag\"", "\"Elk Verlag\"",
				"\"Verlag Heinrich Ellermann\"", "\"Ellert & Richter Verlag\"", "\"Emons Verlag\"",
				"\"Verlag Empirische Pädagogik\"", "\"Urs Engeler Editor\"", "\"Engelsdorfer Verlag\"",
				"\"Engelhorn Verlag\"", "\"Enke Verlag\"", "\"Verlag Enzyklopädie\"", "\"epodium Verlag\"",
				"\"Verlag epOs music\"", "\"Eremiten-Presse\"", "\"Ergon-Verlag\"", "\"Ernst & Sohn\"",
				"\"Esslinger Verlag J. F. Schreiber\"", "\"Ernst Eulenburg\"", "\"ERF Verlag\"",
				"\"Evangelische Verlagsanstalt\"", "\"Eulenspiegel Verlagsgruppe\"", "\"Europa Verlag\"",
				"\"Europa-Lehrmittel\"", "\"exclusiv\"", "\"expert verlag\"", "\"Fachbuchverlag Leipzig\"",
				"\"Falken-Verlag\"", "\"Festa Verlag\"", "\"Kunstverlag Josef Fink\"", "\"Wilhelm Fink Verlag\"",
				"\"FinanzBuch Verlag\"", "\"Finanz Colloquium Heidelberg\"", "\"Gustav Fischer Verlag\"",
				"\"S. Fischer Verlag\"", "\"Fischer Taschenbuch Verlag\"", "\"Focus Verlag\"", "\"Förster & Borries\"",
				"\"Forum Media Group\"", "\"Frankfurter Verlagsanstalt\"", "\"Frank & Timme\"", "\"Franzis Verlag\"",
				"\"Verlag für die Frau\"", "\"Verlag Freies Geistesleben\"", "\"Friedenauer Presse\"",
				"\"Friedrich Verlag\"", "\"Frieling-Verlag\"", "\"Frommann-Holzboog Verlag\"", "\"Gabler Verlag\"",
				"\"Gabriele-Verlag Das Wort (Universelles Leben)\"", "\"Galileo Press\"", "\"Ganske-Verlagsgruppe\"",
				"\"Geest-Verlag\"", "\"Geistkirch Verlag\"", "\"Gerstenberg Verlag\"", "\"Gerth Medien\"",
				"\"GFMK Verlagsgesellschaft\"", "\"Gimpel Verlag\"", "\"Glaux Verlag Christine Jäger\"",
				"\"Gmeiner-Verlag\"", "\"Godewind Verlag\"", "\"Goethe-Verlag\"", "\"Goldegg Verlag\"",
				"\"Wilhelm Goldmann Verlag\"", "\"Gollenstein Verlag\"", "\"G. J. Göschen'sche Verlagsbuchhandlung\"",
				"\"Gräfe und Unzer Verlag\"", "\"Grafit Verlag\"", "\"Greifenverlag\"", "\"GRIN Verlag\"",
				"\"Gruner + Jahr\"", "\"Grupello Verlag\"", "\"grünes herz\"", "\"Verlag Peter Guhl\"",
				"\"Gütersloher Verlagshaus\"", "\"GWV Fachverlage\"", "\"Hamburger Lesehefte\"", "\"Hänssler Verlag\"",
				"\"Haffmans Verlag\"", "\"Verlag Hahnsche Buchhandlung\"", "\"Peter Hammer Verlag\"",
				"\"Carl Hanser Verlag\"", "\"Harenberg Verlag\"", "\"Rudolf Haufe Verlag\"", "\"Haug Verlag\"",
				"\"Haupt Verlag\"", "\"Haymon Verlag\"", "\"Heel Verlag\"", "\"Verlag Heinz Heise\"", "\"Henschel Musik\"",
				"\"hep verlag\"", "\"Verlag Herder\"", "\"Herold Business Data\"", "\"Norbert Hethke Verlag\"",
				"\"Heye Verlag\"", "\"Carl Heymanns Verlag\"", "\"Heyne-Verlag\"",
				"\"hier + jetzt, Verlag für Kultur und Geschichte\"", "\"Anton Hiersemann Verlag\"", "\"Hirmer Verlag\"",
				"\"Hippokrates Verlag\"", "\"hnb-verlag\"", "\"Hoffmann und Campe\"", "\"Hogrefe Verlag\"",
				"\"Holle Verlag\"", "\"Verlagsgruppe Georg von Holtzbrinck\"", "\"Alfred Holz Verlag\"", "\"Ed. Hölzel\"",
				"\"Holzmann Medien\"", "\"Kai Homilius Verlag\"", "\"Verlag Peter Hopf\"", "\"Der Hörverlag (DHV)\"",
				"\"Huber Verlag\"", "\"Hueber Verlag\"", "\"Verlagsgruppe Husum\"", "\"Verlagsgruppe Hüthig Jehle Rehm\"",
				"\"IKS Garamond Verlag\"", "\"Imprimatur Verlag Rudolf Kring\"", "\"Info Verlag\"", "\"Insel Verlag\"",
				"\"Isensee Verlag\"", "\"Jackwerth Verlag\"", "\"Verlagshaus Jacoby & Stuart\"", "\"Jahreszeiten Verlag\"",
				"\"Verlag Michael John\"", "\"Jüdischer Verlag\"", "\"Verlag der Jugendbewegung\"",
				"\"Junfermann Verlag\"", "\"Jung und Jung\"", "\"Buchverlag Junge Welt\"", "\"Junius Verlag\"",
				"\"K&K Verlagsanstalt\"", "\"Kulturverlag Kadmos\"", "\"Kairos Edition\"", "\"Karolinger Verlag\"",
				"\"Kartographia Winterthur\"", "\"Katzengraben-Presse\"", "\"Kawohl-Verlag\"", "\"Kehrer Verlag\"",
				"\"Kein & Aber\"", "\"Martin Kelter Verlag\"", "\"Gustav Kiepenheuer Verlag\"", "\"Kiepenheuer & Witsch\"",
				"\"Kinderbuchverlag Berlin\"", "\"Kindler Verlag\"", "\"Kiribati Medienverlag\"", "\"Kitab-Verlag\"",
				"\"Mediengruppe Klambt\"", "\"Klartext Verlag\"", "\"zu Klampen Verlag\"", "\"Ernst Klett Verlag\"",
				"\"Klett Cotta\"", "\"Klett Perthes\"", "\"Erika Klopp Verlag\"", "\"Verlag Julius Klinkhardt\"",
				"\"Verlag Vittorio Klostermann\"", "\"Albrecht Knaus Verlag\"", "\"Verlag Josef Knecht\"",
				"\"Knesebeck Verlag\"", "\"Kober-Kümmerly+Frey\"", "\"Komplett-Media GmbH\"", "\"Königshausen & Neumann\"",
				"\"Kohl Verlag\"", "\"Kohlhammer Verlag\"", "\"Konkursbuch Verlag\"", "\"Konradin Mediengruppe\"",
				"\"Kookbooks\"", "\"Kopp Verlag\"", "\"Kösel-Verlag\"", "\"Verlag Dr. Kovac\"", "\"Karin Kramer Verlag\"",
				"\"Karl Krämer Verlag\"", "\"Alfred Kröner Verlag\"", "\"Verlag Kreuz\"", "\"Bücher Krüger\"",
				"\"Kulturmaschinen\"", "\"Verlag der Kunst\"", "\"Verlag Kultur und Fortschritt\"",
				"\"Verlag Antje Kunstmann\"", "\"Laika-Verlag\"", "\"Lambertus Verlag\"", "\"Lamuv Verlag\"",
				"\"Peter Lang Verlagsgruppe\"", "\"Langenscheidt Verlagsgruppe\"", "\"Langen Müller Verlag\"",
				"\"Verlag Langewiesche\"", "\"Lars Müller Publishers\"", "\"Lenos Verlag\"",
				"\"Leipziger Universitätsverlag\"", "\"Liberalis Verlag\"", "\"Verlag libertäre Assoziation\"",
				"\"Lichtdruck- und Bildverlag der Kunst\"", "\"Literarisches Comptoir Zürich und Winterthur\"",
				"\"Liebeskind Verlag\"", "\"Limmat Verlag\"", "\"Linde Verlag\"", "\"Lingen Verlag\"",
				"\"Ch. Links Verlag\"", "\"LIT Verlag\"", "\"literaturca Verlag\"", "\"Luchterhand Fachverlag\"",
				"\"Luchterhand Literaturverlag\"", "\"Luftschacht\"", "\"Lusatia Verlag\"", "\"luxbooks\"",
				"\"Lyrikedition 2000\"", "\"m+a Verlag für Messen, Ausstellungen und Kongresse\"", "\"Maas & Peither\"",
				"\"MairDumont\"", "\"mairisch Verlag\"", "\"Malik-Verlag\"", "\"MANA-Verlag\"", "\"Manesse Verlag\"",
				"\"Manhattan\"", "\"Manuela Kinzel Verlag\"", "\"Manutius Verlag\"", "\"Manz Verlag\"", "\"Maro Verlag\"",
				"\"März Verlag\"", "\"Matthaes Verlag\"", "\"Matthes & Seitz\"", "\"Karl-May-Verlag\"",
				"\"medhochzwei Verlag GmbH\"", "\"Medizinisch Wissenschaftliche Verlagsgesellschaft\"",
				"\"Meinders & Elstermann\"", "\"Meiner Verlag\"", "\"Meininger-Verlag\"", "\"Memento Verlag\"",
				"\"Mercator-Verlag\"", "\"Merlin Verlag\"", "\"Merve Verlag\"", "\"J.B. Metzler Verlag\"",
				"\"Johann Heinrich Meyer Verlag\"", "\"Peter Meyer Verlag\"", "\"Piet Meyer Verlag\"", "\"Meyer & Meyer\"",
				"\"michason & may\"", "\"Verlagsgruppe Milchstrasse\"", "\"Mildenberger Verlag\"", "\"Miriam-Verlag\"",
				"\"Mitteldeutscher Verlag(mdv)\"", "\"E. S. Mittler & Sohn\"", "\"MM Verlag\"",
				"\"Verlag moderne industrie\"", "\"Verlag für moderne Kunst\"", "\"Verlag Ch. Möllmann\"",
				"\"Mohn Media\"", "\"Mohr Siebeck Verlag\"", "\"Molden Verlag\"", "\"Morstadt Verlag\"",
				"\"Mosquito Verlag\"", "\"Motor Presse Stuttgart\"", "\"C. F. Müller Verlag\"", "\"MünchenVerlag\"",
				"\"Munzinger-Archiv\"", "\"mvg Verlag\"", "\"Nagel & Kimche Verlag\"", "\"Verlag der Nation\"",
				"\"Nautische Veröffentlichung Verlag\"", "\"NDV Neue Darmstädter Verlagsanstalt\"", "\"Neisse Verlag\"",
				"\"Das Neue Berlin\"", "\"Neue Impulse Verlag\"", "\"Verlag Neue Kritik\"", "\"Verlag Neue Musik\"",
				"\"Neuer Deutscher Verlag\"", "\"Verlag Neues Leben\"", "\"Neufeld Verlag\"",
				"\"Neukirchener Verlagsgesellschaft\"", "\"Neumann Verlag\"", "\"Verlag J. Neumann-Neudamm\"",
				"\"Neuromedizin Verlag\"", "\"New Era Publications\"",
				"\"Nicolai Verlag(Nicolaische Verlagsbuchhandlung)\"", "\"Niederösterreichisches Pressehaus\"",
				"\"Max Niemeyer Verlag\"", "\"Niekao Lernwelten Verlag\"", "\"Niggli Verlag\"", "\"Nomos Verlag\"",
				"\"NordSüd Verlag\"", "\"NWB Verlag\"", "\"nymphenburger Verlag\"", "\"Oberlausitzer Verlag\"",
				"\"Offizin Zürich Verlag\"", "\"Oktober Verlag\"", "\"Verlag Österreich\"",
				"\"Verlag der Österreichischen Akademie der Wissenschaften\"", "\"Österreichischer Bundesverlag\"",
				"\"Verlagsgruppe Oetinger\"", "\"Edition Olms\"", "\"Georg Olms Verlag\"", "\"R. Oldenbourg Verlag\"",
				"\"Olympia-Verlag\"", "\"Oncken Verlag\"", "\"Ontos Verlag\"", "\"Orlanda Verlag\"",
				"\"OWC-Verlag für Außenwirtschaft\"", "\"Pabel-Moewig\"", "\"Palabros de Cologne\"",
				"\"Pahl-Rugenstein Verlag\"", "\"Panama Verlag\"", "\"Panini Verlag\"", "\"Panini Comics\"",
				"\"PapyRossa Verlag\"", "\"Patzer Verlag\"", "\"Paul Parey Verlag\"", "\"Passagen Verlag\"",
				"\"Patmos Verlagsgruppe\"", "\"Pattloch Verlag\"", "\"Pendo Verlag\"", "\"Persen Verlag\"",
				"\"Phaidon Verlag\"", "\"Piper Verlag\"", "\"Planet Manga\"", "\"Plöttner Verlag\"", "\"poetenladen\"",
				"\"Pressedruck Mediengruppe\"", "\"Guido Pressler Verlag\"", "\"Primus Verlag\"", "\"Prisma Verlag\"",
				"\"Prestel Verlag\"", "\"Projektverlag\"", "\"Propyläen Verlag\"", "\"Prospero Verlag\"",
				"\"Provinz Verlag\"", "\"Psychosozial-Verlag\"", "\"Verlag Friedrich Pustet\"", "\"Quelle & Meyer\"",
				"\"Quer Verlag\"", "\"Dr. Josef Raabe Verlags-GmbH\"", "\"Rabenpresse\"", "\"Random House Verlagsgruppe\"",
				"\"Ravensburger Buchverlag\"", "\"Reclam-Verlag\"", "\"Philipp Reclam jun.\"", "\"Reprodukt\"",
				"\"Residenz Verlag\"", "\"Rimbaud Verlag\"", "\"Ritter Verlag\"", "\"Verlag Rockstuhl\"",
				"\"Rogner & Bernhard\"", "\"Romantruhe\"", "\"Römerhof Verlag\"", "\"Romiosini Verlag\"",
				"\"Verlag Rosa Winkel\"", "\"Rotbuch Verlag\"", "\"Rotpunktverlag\"", "\"Rowohlt Verlag\"",
				"\"Verlag Rüegger\"", "\"Verlag an der Ruhr\"", "\"Rüffer & Rub\"", "\"Rütten & Loening\"",
				"\"Sankt Ulrich Verlag\"", "\"Salis Verlag\"", "\"Salleck Publications\"", "\"Sanssouci Verlag\"",
				"\"Sarto Verlag\"", "\"K. G. Saur Verlag\"", "\"Sauerländer Verlage\"", "\"Schardt Verlag\"",
				"\"Verlag Scheidegger und Spiess\"", "\"Scherz Verlag\"", "\"Verlag Hans Schiler\"", "\"Schirner Verlag\"",
				"\"Schlehdorn Verlag\"", "\"Schlütersche Verlagsgesellschaft\"", "\"Schmetterling Verlag\"",
				"\"Erich Schmidt Verlag\"", "\"Schmidt-Römhild\"", "\"Verlag Schnell und Steiner\"",
				"\"Schöffling & Co.\"", "\"Verlag Ferdinand Schöningh\"", "\"Schott Music\"", "\"Franz Schneider Verlag\"",
				"\"Schulthess Juristische Medien\"", "\"Carl Schünemann-Verlag\"", "\"Schüren Verlag\"",
				"\"Schwabe Verlag\"", "\"Schwarzer Turm Verlag\"", "\"Schwarzkopf & Schwarzkopf Verlag\"",
				"\"Schweers + Wall\"", "\"Schweizerische Gesellschaft für GesundheitspolitikSGGP\"",
				"\"Verlag C. A. Schwetschke & Sohn\"", "\"E. A. Seemann\"", "\"Peter-Segler-Verlag\"",
				"\"Semmel Verlach\"", "\"Severus Verlag\"", "\"Shaker Verlag\"", "\"Siedler Verlag\"",
				"\"Silberburg-Verlag\"", "\"Simon Verlag für Bibliothekswissen\"", "\"Sonnenberg Verlag\"",
				"\"Sonntag Verlag\"", "\"Spaeth & Linde\"", "\"Special Media SDL\"", "\"Spektrum Akademischer Verlag\"",
				"\"Spitta Verlag\"", "\"Splitter Verlag\"", "\"Springer-Verlag\"", "\"Axel Springer AG\"",
				"\"Paul Steegemann Verlag\"", "\"Steidl\"", "\"Conrad Stein Verlag\"", "\"Verlag J. F. Steinkopf\"",
				"\"Sterne Verlag\"", "\"Leopold Stocker Verlag\"", "\"St. Benno-Verlag\"", "\"Stroemfeld Verlag\"",
				"\"Styria\"", "\"Süddeutscher Verlag GmbH\"", "\"Südwest-Verlag\"", "\"Suhrkamp Verlag\"",
				"\"Tamedia AG\"", "\"Taschen Verlag\"", "\"teamart Verlag\"", "\"Tectum Verlag\"", "\"Terzio Verlag\"",
				"\"Teubner Verlag\"", "\"Teubnersche Verlagsbuchhandlung\"", "\"Thieme Verlagsgruppe\"",
				"\"Tessloff Verlag\"", "\"Buchwerkstatt Thanhäuser\"", "\"The Green Box - Kunst Editionen\"",
				"\"Konrad Theiss Verlag\"", "\"Thienemann Verlag\"", "\"Tigerpress Verlag\"", "\"Tisch 7\"",
				"\"Titania Verlag\"", "\"Tokyopop\"", "\"Tologo Verlag\"", "\"transcript Verlag\"",
				"\"Transit Buchverlag\"", "\"Transpress Verlag\"", "\"Trescher Verlag\"", "\"Tropen Verlag\"",
				"\"Trotzdem Verlag\"", "\"UBooks-Verlag\"", "\"Verlag Carl Ueberreuter\"", "\"Verlag Eugen Ulmer\"",
				"\"Unrast Verlag\"", "\"Ullstein Verlag\"", "\"Ullstein Buchverlage\"", "\"Ulrike Helmer Verlag\"",
				"\"Unionsverlag\"", "\"Universum Verlag\"", "\"Verlag Urachhaus\"", "\"Urania Verlag\"",
				"\"Herbert Utz Verlag\"", "\"UVK Verlagsgesellschaft\"", "\"Verlag Franz Vahlen\"",
				"\"Vandenhoeck & Ruprecht\"", "\"VBE-Verlag\"", "\"VDE-Verlag\"", "\"Velbrück Wissenschaft\"",
				"\"Verbrecher-Verlag\"", "\"Veritas Verlag\"", "\"vgs\"", "\"Vieweg Verlag\"",
				"\"Viola FalkenbergVerlag\"", "\"Vogel Medien Gruppe\"", "\"Voggenreiter Verlag\"",
				"\"Verlag Volk und Welt\"", "\"Voland & Quist\"", "\"Volk und Wissen\"",
				"\"VS Verlag für Sozialwissenschaften\"", "\"VSA-Verlag\"", "\"Wachholtz Verlag\"",
				"\"Verlag Klaus Wagenbach\"", "\"Wallstein-Verlag\"", "\"Wasmuth Verlag\"", "\"Weger Verlag\"",
				"\"Christian WegnerVerlag\"", "\"Wehrhahn Verlag\"", "\"Weidle Verlag\"", "\"Weingarten Verlag\"",
				"\"weissbooks.w\"", "\"Weitbrecht Verlag\"", "\"WEKA-Verlagsgruppe\"", "\"Wellhausen & Marquardt Medien\"",
				"\"Weltbild Verlagsgruppe\"", "\"Martin Werhand Verlag\"", "\"Westend Verlag\"",
				"\"Verlag Die Werkstatt\"", "\"Westermann Druck- und Verlagsgruppe\"", "\"Westdeutscher Verlag\"",
				"\"Werner-Verlag\"", "\"Erich Wewel Verlag\"", "\"WeymannBauer Verlag\"", "\"Wichern Verlag\"",
				"\"Wichmann-Verlag\"", "\"Wieser Verlag\"", "\"Wissen Media Verlag\"", "\"Wißner-Verlag\"",
				"\"Wiley-VCH Verlag\"", "\"Wochenschau Verlag\"", "\"Robert Wohlleben Verlag\"", "\"Kurt Wolff\"",
				"\"Wolters Kluwer Deutschland\"", "\"Wort & Bild Verlag\"", "\"WRS Verlag\"", "\"YinYang Media Verlag\"",
				"\"Zauberkreis Verlag\"", "\"Zenodot Verlagsgesellschaft\"", "\"Paul Zsolnay Verlag\"",
				"\"Zweitausendeins\"", "\"Zwiebelzwerg Verlag\"", "\"Ziegler Druck- und Verlags-AG\"",
				"\"J. F. Ziegler KG Druckerei und Verlag\"", "\"Zytglogge Verlag\"" };
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
			return new VerlagNameGenerator();
		}
	}
	
	@Override
	public Integer getMaxIndex() {
		return values.length;
	}
}
