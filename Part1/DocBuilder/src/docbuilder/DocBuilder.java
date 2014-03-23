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
import java.util.Dictionary;

/**
 *
 * @author guilherme
 */
public class DocBuilder
{
  public void CreateDocument(Template template, Dictionary<String, String> values, String outputPath)
  {
    Yylex scanner = null;
    try {
      String inputFile = "";
      int initialState = Yylex.YYINITIAL;              
      
      switch(template)
      {
        case Contract:
          inputFile = "contract.rtf";
          initialState = Yylex.CONTRACT;
          break;
          
        case Receipt:
          inputFile = "receipt.rtf";
          initialState = Yylex.RECEIPT;          
          break;
        
        case Certificate:
          inputFile = "certificate.rtf";
          initialState = Yylex.CERTIFICATE;
          break;
      }
      
      InputStream istream = getClass().getResourceAsStream("resources/" + inputFile);
      Reader reader = new java.io.InputStreamReader(istream, "UTF-8");
      
      scanner = new Yylex(reader);
      scanner.yybegin(Yylex.CONTRACT);
      
      BufferedWriter writer = new BufferedWriter(new FileWriter(outputPath));
      while (scanner.yylex() != Yylex.YYEOF)
      {
      }
      writer.close();
    }
    catch (java.io.FileNotFoundException e) {
      System.out.println("File not found : \"resources/contract.rtt\"");
    }
    catch (java.io.IOException e) {
      System.out.println("IO error scanning file \"resources/contract.rtf\"");
      System.out.println(e);
    }
    catch (Exception e) {
      System.out.println("Unexpected exception:");
      e.printStackTrace();
    }
  }
    
  public static void main(String[] argv)
  {
    DocBuilder db = new DocBuilder();
    
    db.CreateDocument(Template.Contract, null, "blach.rtf");
  }
}
