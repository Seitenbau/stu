package model;

import com.seitenbau.stu.database.generator.DataType;
import com.seitenbau.stu.database.generator.DatabaseModel;
import com.seitenbau.stu.database.generator.TableBuilder;
import com.seitenbau.stu.database.generator.values.VerlagNameGenerator;

public class BookDatabaseModel extends DatabaseModel
{
  public BookDatabaseModel()
  {
    database("BookDatabase");
    packageName("com.seitenbau.stu.bookdatabase.model");
    enableTableModelClassesGeneration();
    //disbaleTableDSLGeneration();

    TableBuilder buch = table("buch"); 
    TableBuilder verlag = table("verlag"); 
    TableBuilder autor = table("autor"); 
        
    buch //
        .column("id", DataType.BIGINT) //
          .defaultIdentifier() //
          .autoInvokeNext() //
        .column("name", DataType.VARCHAR) //
        .column("verlag", DataType.BIGINT) //
          .reference
            .foreign(verlag.ref("id"))
              .multiplicity("1..1")
      .build();

    verlag //
        .column("id", DataType.BIGINT) //
          .defaultIdentifier() //
          .autoInvokeNext() //
        .column("name", DataType.VARCHAR) //
          .generator(new VerlagNameGenerator())
      .build();

    autor //
        .column("id", DataType.BIGINT) //
          .defaultIdentifier() //
          .autoInvokeNext() //
        .column("vorname", DataType.VARCHAR) //
        .column("nachname", DataType.VARCHAR) //
      .build(); //

    associativeTable("buch_autor")
        .column("buch_id", DataType.BIGINT)
          .reference
            .foreign(buch)
        .column("autor_id", DataType.BIGINT)
          .reference
            .foreign(autor)
      .build();
  }

}
