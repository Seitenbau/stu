package com.seitenbau.stu.database.generator.values;

import java.util.Random;

public class BuchNameGenerator implements ValueGenerator
{

  private Random random;

  private final String[] values = {"\"Bibel\"", "\"Odyssee\"", "\"Apologie\"", "\"Aeneis\"", "\"Germania\"",
      "\"Daphnis und Chloe\"", "\"Bekenntnisse\"", "\"Die Erzählungen aus den tausendundein Nächten\"", "\"Parzival\"",
      "\"Tristan\"", "\"Das Nibelungenlied\"", "\"Die Göttliche Komödie\"", "\"Das Decameron\"", "\"Utopia\"",
      "\"Gargantua und Pantagruel\"", "\"Essais\"", "\"Der abenteuerliche Simplicissimus\"", "\"Pensées\"",
      "\"Robinson Crusoe\"", "\"Gullivers Reisen\"", "\"Tom Jones\"",
      "\"Leben und Ansichten von Tristram Shandy, Gentleman\"", "\"Candide\"", "\"Die Leiden des jungen Werthers\"",
      "\"Anti-Goeze\"", "\"Die Bekenntnisse\"", "\"Anton Reiser\"", "\"Zum ewigen Frieden\"",
      "\"Der arme Mann im Tockenburg\"", "\"Ästhetische Schriften\"", "\"Jacques der Fatalist und sein Herr\"",
      "\"Siebenkäs\"", "\"Hyperion\"", "\"Sudelbücher\"", "\"Die Wahlverwandtschaften\"", "\"Erzählungen\"",
      "\"Das Schatzkästlein des Rheinischen Hausfreunds\"", "\"Kinder- und Hausmärchen\"",
      "\"Kater Murr und Kreisler\"", "\"Geschichte meines Lebens\"", "\"Aus dem Leben eines Taugenichts\"",
      "\"Rot und Schwarz\"", "\"Lenz\"", "\"Verlorene Illusionen\"", "\"Oliver Twist\"", "\"Die toten Seelen\"",
      "\"Entweder - Oder\"", "\"Deutschland. Ein Wintermärchen\"", "\"Phantastische Erzählungen\"", "\"Moby Dick\"",
      "\"Parerga und Paralipomena\"", "\"Der achtzehnte Brumaire des Louis Bonaparte\"", "\"Märchen\"",
      "\"Der grüne Heinrich\"", "\"Madame Bovary\"", "\"Oblomow\"", "\"Die Elenden\"", "\"Alice im Wunderland\"",
      "\"Väter und Söhne\"", "\"Abu Telfan oder Die Heimkehr vom Mondgebirge\"", "\"Krieg und Frieden\"",
      "\"Erzählungen\"", "\"Die Dämonen\"", "\"Menschliches, Allzumenschliches\"", "\"Germinal\"",
      "\"Sohn einer Magd\"", "\"Hunger\"", "\"Das Bildnis des Dorian Gray\"", "\"Erzählungen\"", "\"Der Stechlin\"",
      "\"Buddenbrooks\"", "\"Die Verwirrungen des Zöglings Törleß\"",
      "\"Die Aufzeichnungen des Malte Laurids Brigge\"", "\"Der Untertan\"",
      "\"Auf der Suche nach der verlorenen Zeit\"", "\"Die Abenteuer des braven Soldaten Schwejk\"", "\"Ulysses\"",
      "\"Manhattan Transfer\"", "\"Das Schloss\"", "\"Der Steppenwolf\"", "\"Berlin Alexanderplatz\"", "\"Spuren\"",
      "\"Das Unbehagen in der Kultur\"", "\"Mein Leben\"", "\"Licht im August\"", "\"Erzählungen\"", "\"Tagebücher\"",
      "\"Das siebte Kreuz\"", "\"Der Fremde\"", "\"Erzählungen\"", "\"Querelle\"", "\"Der alte Mann und das Meer\"",
      "\"Stiller\"", "\"Traurige Tropen\"", "\"Das letzte Band\"", "\"Die Blechtrommel\"", "\"Die Wörter\"",
      "\"Geschichten vom Herrn Keuner\"", "\"Jahrestage\"", "\"Don Quijote\""};

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
      return new BuchNameGenerator();
    }

  }
}
