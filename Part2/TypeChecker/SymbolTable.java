/*
 * Copyright (c) 2014
 *
 * 12180247-4 Guilherme Taschetto (gtaschetto@gmail.com)
 * 07104168-5 Fernando Delazeri (panchos3t@gmail.com)
 * 11100166-5 Tomas Ceia Ramos de Souza (tomas.souza@gmail.com)
 */

import java.util.ArrayList;
import java.util.List;

public class SymbolTable
{
  private List<Symbol> list;
    
  public SymbolTable()
  {
    list = new ArrayList<>();
  }
    
  public void insert(Symbol symbol)
  {
    list.add(symbol);
  }    
    
  public void print() {
    System.out.println("\n\nListagem da tabela de simbolos:\n");
    for (Symbol symbol : list) {
      System.out.println(symbol);
    }
  }

  public Symbol search(String id)
  {
    return search(id, "");
  }
      
  public Symbol search(String id, String scope)
  {
    for (Symbol symbol : list) {
      if (symbol.getId().equals(id) && symbol.getScope().equals(scope)) {
        return symbol;
      }
    }
    return null;
  }

  public List<Symbol> getList() {return list;}
}
