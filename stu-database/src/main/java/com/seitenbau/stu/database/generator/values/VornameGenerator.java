package com.seitenbau.stu.database.generator.values;

import java.util.Random;

public class VornameGenerator implements ValueGenerator
{

  private Random random;

  private final String[] values = {"\"Adelberga\"", "\"Adelgard\"", "\"Adelheid\"", "\"Adelmar\"", "\"Adolf\"",
      "\"Almut\"", "\"Almuth\"", "\"Almudis\"", "\"Alrun\"", "\"Alfred\"", "\"Alveradis\"", "\"Alsuna\"", "\"Amalie\"",
      "\"Amalia\"", "\"Ansgar\"", "\"Ansger\"", "\"Ansmund\"", "\"Answald\"", "\"Answin\"", "\"Arbogast\"",
      "\"Archibald\"", "\"Armin\"", "\"Arnfried\"", "\"Arnhelm\"", "\"Arnold\"", "\"Arnulf\"", "\"Arwin\"",
      "\"Astrid\"", "\"Aswin\"", "\"Balder\"", "\"Baldur\"", "\"Balduin\"", "\"Baldwin\"", "\"Balko\"", "\"Baltrun\"",
      "\"Benno\"", "\"Bernfried\"", "\"Bernger\"", "\"Bernhard\"", "\"Bernd\"", "\"Bernold\"", "\"Bernulf\"",
      "\"Bernward\"", "\"Bero\"", "\"Bert\"", "\"Berta\"", "\"Bertha\"", "\"Bertfried\"", "\"Berthold\"",
      "\"Bertold\"", "\"Bertlinde\"", "\"Bertram\"", "\"Bertrand\"", "\"Bertrun\"", "\"Bertwin\"", "\"Bihildis\"",
      "\"Björn\"", "\"Bodo\"", "\"Brandolf\"", "\"Brun\"", "\"Bruno\"", "\"Bruna\"", "\"Brunhilde\"", "\"Brunold\"",
      "\"Burghard\"", "\"Burchard\"", "\"Burkhard\"", "\"Dagmar\"", "\"Dagny\"", "\"Dagobert\"", "\"Dankmar\"",
      "\"Dankrad\"", "\"Dankrun\"", "\"Dankward\"", "\"Degenar\"", "\"Degenhard\"", "\"Detlef\"", "\"Detlev\"",
      "\"Dietbald\"", "\"Dietbert\"", "\"Dieter\"", "\"Dietfried\"", "\"Dietger\"", "\"Diethard\"", "\"Diethelm\"",
      "\"Diether\"", "\"Diethold\"", "\"Dietold\"", "\"Dietwald\"", "\"Dietmar\"", "\"Ditmar\"", "\"Detmar\"",
      "\"Dietolf\"", "\"Dietulf\"", "\"Dietram\"", "\"Dietrich\"", "\"Dietrun\"", "\"Dietwin\"", "\"Durs\"",
      "\"Eberhard\"", "\"Eberwin\"", "\"Edburga\"", "\"Edda\"", "\"Eduard\"", "\"Edelgard\"", "\"Edeltraud\"",
      "\"Elke\"", "\"Edigna\"", "\"Eckbert\"", "\"Eckbrecht\"", "\"Egbert\"", "\"Ekbert\"", "\"Ekkehard\"",
      "\"Eckard\"", "\"Eckehard\"", "\"Eckehart\"", "\"Eckhard\"", "\"Eggert\"", "\"Egmont\"", "\"Eila\"", "\"Einar\"",
      "\"Elfgard\"", "\"Elfriede\"", "\"Elfrun\"", "\"Elmar\"", "\"Elvira\"", "\"Emma\"", "\"Engelbert\"", "\"Erich\"",
      "\"Erik\"", "\"Erika\"", "\"Erlgard\"", "\"Ernst\"", "\"Erwin\"", "\"Ewald\"", "\"Ewalt\"", "\"Ewaldt\"",
      "\"Falko\"", "\"Fara\"", "\"Farold\"", "\"Fehild\"", "\"Ferdinand\"", "\"Ferun\"", "\"Finja\"", "\"Folkward\"",
      "\"Frank\"", "\"Frauke\"", "\"Freya\"", "\"Freia\"", "\"Friederike\"", "\"Frigga\"", "\"Friedrich\"",
      "\"Fritz\"", "\"Frigga\"", "\"Gaidemar\"", "\"Gandolf\"", "\"Gandulf\"", "\"Gefion\"", "\"Gelsa\"", "\"Gerald\"",
      "\"Gerda\"", "\"Gerhard\"", "\"Gerd\"", "\"Gerlinde\"", "\"Gertrud\"", "\"Gerwin\"", "\"Giesela\"", "\"Gisela\"",
      "\"Gilbert\"", "\"Gisbert\"", "\"Gismara\"", "\"Godelief\"", "\"Godwin\"", "\"Goswin\"", "\"Gotmar\"",
      "\"Gottfried\"", "\"Gotthilf\"", "\"Gottlieb\"", "\"Gottlob\"", "\"Gudrun\"", "\"Gunhild\"", "\"Günther\"",
      "\"Gustav\"", "\"Hadwinga\"", "\"Hedda\"", "\"Heidi\"", "\"Hagarun\"", "\"Hagen\"", "\"Hallgard\"", "\"Harald\"",
      "\"Harold\"", "\"Hartmut\"", "\"Hedwig\"", "\"Heidrun\"", "\"Heike\"", "\"Henrike\"", "\"Heinrich\"",
      "\"Heinz\"", "\"Hendrik\"", "\"Heiko\"", "\"Helga\"", "\"Hella\"", "\"Helgard\"", "\"Helmar\"", "\"Helmut\"",
      "\"Hellmut\"", "\"Heilmut\"", "\"Herbert\"", "\"Herline\"", "\"Hermann\"", "\"German\"", "\"Hermine\"",
      "\"Herta\"", "\"Hildegard\"", "\"Hilke\"", "\"Hilde\"", "\"Holger\"", "\"Horst\"", "\"Hubert\"", "\"Iduna\"",
      "\"Idun\"", "\"Ida\"", "\"Igor\"", "\"Ingmar\"", "\"Ingo\"", "\"Inga\"", "\"Inge\"", "\"Ingrid\"", "\"Ingrun\"",
      "\"Inka\"", "\"Irmbert\"", "\"Irmin\"", "\"Irmela\"", "\"Irma\"", "\"Irmgard\"", "\"Irmina\"", "\"Irminar\"",
      "\"Irvin\"", "\"Isbert\"", "\"Isolde\"", "\"Iwo\"", "\"Jelto\"", "\"Karl\"", "\"Knut\"", "\"Konrad\"",
      "\"Kriemhild\"", "\"Kunheide\"", "\"Kunna\"", "\"Kunolf\"", "\"Kunrada\"", "\"Kurt\"", "\"Landerun\"",
      "\"Lando\"", "\"Landogar\"", "\"Leif\"", "\"Leonhard\"", "\"Leonard\"", "\"Leopold\"", "\"Liebgard\"",
      "\"Linda\"", "\"Lioba\"", "\"Lothar\"", "\"Ludwig\"", "\"Luitberga\"", "\"Luithilde\"", "\"Malte\"",
      "\"Malvin\"", "\"Melvin\"", "\"Manfred\"", "\"Marbod\"", "\"Margund\"", "\"Markward\"", "\"Mathilde\"",
      "\"Merlinde\"", "\"Minna\"", "\"Minnegard\"", "\"Mutbrecht\"", "\"Nanna\"", "\"Neidhart\"", "\"Norbert\"",
      "\"Nordger\"", "\"Nordwin\"", "\"Norman\"", "\"Northild\"", "\"Nordgard\"", "\"Nortrud\"", "\"Nortrun\"",
      "\"Norwiga\"", "\"Notker\"", "\"Oda\"", "\"Odila\"", "\"Odalinde\"", "\"Odin\"", "\"Olaf\"", "\"Ortlind\"",
      "\"Orthilde\"", "\"Ortrun\"", "\"Ortwin\"", "\"Osgard\"", "\"Oskar\"", "\"Osrun\"", "\"Ostara\"", "\"Oswald\"",
      "\"Oswin\"", "\"Othilde\"", "\"Otmar\"", "\"Otto\"", "\"Otthein\"", "\"Ottokar\"", "\"Raban\"", "\"Radolf\"",
      "\"Radulf\"", "\"Ralf\"", "\"Ralph\"", "\"Rolf\"", "\"Rathard\"", "\"Ratmar\"", "\"Raimund\"", "\"Raymund\"",
      "\"Reimund\"", "\"Rainbert\"", "\"Reinbert\"", "\"Rainer\"", "\"Rainher\"", "\"Reiner\"", "\"Reinher\"",
      "\"Raingard\"", "\"Eeingard\"", "\"Reinmar\"", "\"Raimar\"", "\"Raimer\"", "\"Rainmar\"", "\"Reimar\"",
      "\"Reimer\"", "\"Reinfried\"", "\"Rainfried\"", "\"Reinhard\"", "\"Rainhard\"", "\"Reinhild\"", "\"Raginhild\"",
      "\"Rainhild\"", "\"Rainhilde\"", "\"Reinhilde\"", "\"Reinhold\"", "\"Rainald\"", "\"Rainhold\"", "\"Raynald\"",
      "\"Rainulf\"", "\"Rainolf\"", "\"Reinolf\"", "\"Reinulf\"", "\"Reinward\"", "\"Rainward\"", "\"Rambert\"",
      "\"Rambo\"", "\"Rambod\"", "\"Rambold\"", "\"Rambald\"", "\"Ramgar\"", "\"Randolf\"", "\"Randulf\"",
      "\"Randwig\"", "\"Rango\"", "\"Ranmar\"", "\"Rasso\"", "\"Richard\"", "\"Richardis\"", "\"Richardse\"",
      "\"Richbert\"", "\"Richgard\"", "\"Richwin\"", "\"Rigmor\"", "\"Rinelda\"", "\"Robert\"", "\"Rupert\"",
      "\"Ruppert\"", "\"Rupprecht\"", "\"Ruprecht\"", "\"Rochbert\"", "\"Rochold\"", "\"Roland\"", "\"Romilda\"",
      "\"Romhild\"", "\"Rumhild\"", "\"Rumold\"", "\"Roswin\"", "\"Roswitha\"", "\"Rudgar\"", "\"Roderich\"",
      "\"Rudrich\"", "\"Rüdiger\"", "\"Rudger\"", "\"Ruger\"", "\"Rutger\"", "\"Ruland\"", "\"Rudland\"", "\"Rudolf\"",
      "\"Ralf\"", "\"Rolf\"", "\"Ruthard\"", "\"Runhild\"", "\"Runa\"", "\"Salgard\"", "\"Sarhild\"", "\"Sarolf\"",
      "\"Saskia\"", "\"Schwanhilde\"", "\"Swantje\"", "\"Sebald\"", "\"Siegbald\"", "\"Siegfried\"", "\"Siggi\"",
      "\"Siegmund\"", "\"Sigmund\"", "\"Sif\"", "\"Siv\"", "\"Sigga\"", "\"Siglinde\"", "\"Sieglinde\"", "\"Sigmar\"",
      "\"Sigrid\"", "\"Sigrun\"", "\"Sindolf\"", "\"Sisgards\"", "\"Solveig\"", "\"Sonnwinn\"", "\"Stilla\"",
      "\"Sunhild\"", "\"Sunna\"", "\"Sunje\"", "\"Sunja\"", "\"Sven\"", "\"Swidger\"", "\"Tankred\"", "\"Vorname\"",
      "\"Tassilo\"", "\"Teuderun\"", "\"Thilo\"", "\"Thor\"", "\"Thorbrand\"", "\"Thorleif\"", "\"Thorsten\"",
      "\"Throals\"", "\"Thusnelda\"", "\"Tilrun\"", "\"Traute\"", "\"Trautwin\"", "\"Trudildis\"", "\"Trude\"",
      "\"Tyra\"", "\"Uland\"", "\"Ulrich\"", "\"Udo\"", "\"Ulbert\"", "\"Ulf\"", "\"Ulrike\"", "\"Ute\"", "\"Uta\"",
      "\"Uwe\"", "\"Uwo\"", "\"Vanadis\"", "\"Volker\"", "\"Volkher\"", "\"Volkmar\"", "\"Volkwin\"", "\"Volkward\"",
      "\"Walburga\"", "\"Walda\"", "\"Waldemar\"", "\"Walter\"", "\"Walther\"", "\"Waltraud\"", "\"Werner\"",
      "\"Wibke\"", "\"Wiborg\"", "\"Widogard\"", "\"Widukind\"", "\"Wigberg\"", "\"Wilberta\"", "\"Wilfriede\"",
      "\"Wilgard\"", "\"Wilhelm\"", "\"Willibald\"", "\"Wiltraud\"", "\"Wiltraut\"", "\"Winimar\"", "\"Wisgard\"",
      "\"Wolfgang\"", "\"Wolfram\"", "\"Wunna\""};

  @Override
  public void initialize(long seed)
  {
    random = new Random(seed);
  }

  @Override
  public String nextValue()
  {
    return values[random.nextInt(values.length)];
  }

  public static class Factory implements ValueGeneratorFactory
  {

    @Override
    public ValueGenerator createGenerator()
    {
      return new VornameGenerator();
    }

  }
}
