package model;

public class Generator {
  
  public static void main(String[] args) throws Exception {
    PersonDatabaseModel model = new PersonDatabaseModel();
    model.generatedSourceFolder(args[0]);
    model.generate();
  }
  
}
