/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package docbuilder;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;

/**
 *
 * @author Guilherme Taschetto
 */
public class DocBuilder
{
  public void CreateDocument(Template template, Map<String, String> values, String outputPath)
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
      
    try {
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
    catch (Exception e) {
      System.out.println("Unexpected exception:");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }
}
