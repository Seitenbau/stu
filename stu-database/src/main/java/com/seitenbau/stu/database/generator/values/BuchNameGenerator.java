package com.seitenbau.stu.database.generator.values;

import java.util.Random;

public class BuchNameGenerator implements ValueGenerator
{

  private Random random;

  private final String[] values = {"\"Bibel\"", "\"Odyssee\"", "\"Apologie\"", "\"Aeneis\"", "\"Germania\"",
      "\"Daphnis und Chloe\"", "\"Bekenntnisse\"", "\"Die Erz�hlungen aus den tausendundein N�chten\"", "\"Parzival\"",
      "\"Tristan\"", "\"Das Nibelungenlied\"", "\"Die G�ttliche Kom�die\"", "\"Das Decameron\"", "\"Utopia\"",
      "\"Gargantua und Pantagruel\"", "\"Essais\"", "\"Der abenteuerliche Simplicissimus\"", "\"Pens�es\"",
      "\"Robinson Crusoe\"", "\"Gullivers Reisen\"", "\"Tom Jones\"",
      "\"Leben und Ansichten von Tristram Shandy, Gentleman\"", "\"Candide\"", "\"Die Leiden des jungen Werthers\"",
      "\"Anti-Goeze\"", "\"Die Bekenntnisse\"", "\"Anton Reiser\"", "\"Zum ewigen Frieden\"",
      "\"Der arme Mann im Tockenburg\"", "\"�sthetische Schriften\"", "\"Jacques der Fatalist und sein Herr\"",
      "\"Siebenk�s\"", "\"Hyperion\"", "\"Sudelb�cher\"", "\"Die Wahlverwandtschaften\"", "\"Erz�hlungen\"",
      "\"Das Schatzk�stlein des Rheinischen Hausfreunds\"", "\"Kinder- und Hausm�rchen\"",
      "\"Kater Murr und Kreisler\"", "\"Geschichte meines Lebens\"", "\"Aus dem Leben eines Taugenichts\"",
      "\"Rot und Schwarz\"", "\"Lenz\"", "\"Verlorene Illusionen\"", "\"Oliver Twist\"", "\"Die toten Seelen\"",
      "\"Entweder - Oder\"", "\"Deutschland. Ein Winterm�rchen\"", "\"Phantastische Erz�hlungen\"", "\"Moby Dick\"",
      "\"Parerga und Paralipomena\"", "\"Der achtzehnte Brumaire des Louis Bonaparte\"", "\"M�rchen\"",
      "\"Der gr�ne Heinrich\"", "\"Madame Bovary\"", "\"Oblomow\"", "\"Die Elenden\"", "\"Alice im Wunderland\"",
      "\"V�ter und S�hne\"", "\"Abu Telfan oder Die Heimkehr vom Mondgebirge\"", "\"Krieg und Frieden\"",
      "\"Erz�hlungen\"", "\"Die D�monen\"", "\"Menschliches, Allzumenschliches\"", "\"Germinal\"",
      "\"Sohn einer Magd\"", "\"Hunger\"", "\"Das Bildnis des Dorian Gray\"", "\"Erz�hlungen\"", "\"Der Stechlin\"",
      "\"Buddenbrooks\"", "\"Die Verwirrungen des Z�glings T�rle�\"",
      "\"Die Aufzeichnungen des Malte Laurids Brigge\"", "\"Der Untertan\"",
      "\"Auf der Suche nach der verlorenen Zeit\"", "\"Die Abenteuer des braven Soldaten Schwejk\"", "\"Ulysses\"",
      "\"Manhattan Transfer\"", "\"Das Schloss\"", "\"Der Steppenwolf\"", "\"Berlin Alexanderplatz\"", "\"Spuren\"",
      "\"Das Unbehagen in der Kultur\"", "\"Mein Leben\"", "\"Licht im August\"", "\"Erz�hlungen\"", "\"Tageb�cher\"",
      "\"Das siebte Kreuz\"", "\"Der Fremde\"", "\"Erz�hlungen\"", "\"Querelle\"", "\"Der alte Mann und das Meer\"",
      "\"Stiller\"", "\"Traurige Tropen\"", "\"Das letzte Band\"", "\"Die Blechtrommel\"", "\"Die W�rter\"",
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
