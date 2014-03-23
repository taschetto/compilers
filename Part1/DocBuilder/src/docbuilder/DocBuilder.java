/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package docbuilder;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.Reader;
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
      BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));

      Yylex scanner = new Yylex(reader, writer, values);
      scanner.yybegin(initialState);
      scanner.yylex();
      
      writer.close();
    }
    catch (java.io.FileNotFoundException e) {
      System.out.println("File not found : \"" + inputPath + "\"");
    }
    catch (java.io.IOException e) {
      System.out.println("IO error scanning file \"" + inputPath + "\"");
      System.out.println(e);
    }
    catch (Exception e) {
      System.out.println("Unexpected exception:");
      e.printStackTrace();
    }
  }
  
  public static void main(String[] argv)
  {
    DocBuilderUI ui = new DocBuilderUI();
    ui.setVisible(true);
    
    Map<String, String> values = new HashMap<String, String>();
    values.put("#local#", "Porto Alegre");
    values.put("#nome#", "Guilherme Taschetto");
    values.put("#valor#", "12345,99");
    values.put("#numero#", "10");
    values.put("#juros#", "0.8");
    
    DocBuilder db = new DocBuilder();
    db.CreateDocument(Template.Contract, values, "newContract.rtf");
  }
}
