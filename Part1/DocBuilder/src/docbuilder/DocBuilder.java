/*
 * Copyright (c) 2014 Guilherme Taschetto & Fernando Delazeri
 */

package docbuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class DocBuilder
{
  public void CreateDocument(Template template, Map<String, String> values, String outputPath) throws Exception
  {
    String inputPath = "resources/";
    int initialState = Yylex.YYINITIAL;              

    switch(template)
    {
      case Contract:
        inputPath += "contract.rtf";
        initialState = Yylex.CONTRACT;
        break;

      case Receipt:
        inputPath += "receipt.rtf";
        initialState = Yylex.RECEIPT;          
        break;

      case Certificate:
        inputPath += "certificate.rtf";
        initialState = Yylex.CERTIFICATE;
        break;
    }
     
    InputStream istream = getClass().getResourceAsStream(inputPath);
    Reader reader = new java.io.InputStreamReader(istream, "UTF-8");

    Path pathToFile = Paths.get(outputPath);
    Files.createDirectories(pathToFile.getParent());

    BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));

    Yylex scanner = new Yylex(reader, writer, values);
    scanner.yybegin(initialState);
    scanner.yylex();

    writer.close();
  }
}
