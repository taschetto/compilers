/*
 * Copyright (c) 2014
 *
 * 12180247-4 Guilherme Taschetto (gtaschetto@gmail.com)
 * 07104168-5 Fernando Delazeri (panchos3t@gmail.com)
 * 11100166-5 Tomas Ceia Ramos de Souza (tomas.souza@gmail.com)
 */

import java.util.ArrayList;
import java.util.List;

public class Symbol
{
  private String id;
  private SymbolClass sclass;  
  private String scope;
  private Symbol type;
  private int elements;
  private Symbol baseType;
  private SymbolTable locals;

  public Symbol(String id, Symbol type, int elements, Symbol baseType, String scope, SymbolClass sclass) {
    this.id = id;
    this.type = type;
    this.elements = elements;
    this.baseType = baseType;
    this.scope = scope;
    this.sclass = sclass;
    this.locals = new SymbolTable();
  }

  public Symbol(String id, Symbol type, String scope, SymbolClass sclass) {
     this(id, type, -1, null, scope, sclass);
  }

  public String getId() {
    return id; 
  }

  public SymbolClass getSymbolClass()
  {
    return sclass;
  }

  public String getScope()
  {
    return scope;
  }

  public Symbol getType() {
    return type; 
  }
   
  public String getTypeStr() {
    return type2str(this); 
  }

  public int getElements() {
    return elements; 
  }

  public Symbol getBaseType() {
    return baseType;
  }

  public SymbolTable getLocals()
  {
    return locals;
  }

  public String toString() {
    StringBuilder aux = new StringBuilder("");
        
    aux.append("Id: ");
    aux.append(String.format("%-10s", id));
    aux.append("\tClass: ");
    aux.append(sclass);
    aux.append("\tScope: ");
    aux.append(String.format("%-4s", scope));
    aux.append("\tType: "); 
    aux.append(type2str(type)); 
   
    if (this.type == Parser.Tp_ARRAY) {
      aux.append(" (ne: ");
      aux.append(elements);
      aux.append(", baseType: ");
      aux.append(type2str(baseType));
      aux.append(")");
    }    

    List<Symbol> list = locals.getList();
    for (Symbol t : list) {
      aux.append("\n\t");
      aux.append(t.toString());
    }

    return aux.toString();
  }

  public String type2str(Symbol type) {
    if (type == null)                   return "null"; 
    else if (type == Parser.Tp_INT)     return "int"; 
    else if (type == Parser.Tp_BOOL)    return "boolean"; 
    else if (type == Parser.Tp_FLOAT)   return "float";
    else if (type == Parser.Tp_STRING)  return "string";
    else if (type == Parser.Tp_STRUCT)  return "struct";
    else if (type == Parser.Tp_ARRAY)   return "array";
    else if (type == Parser.Tp_ERRO)    return  "_erro_";
    else return type.getId();
  }

//  public void insereLocal(String id, int tp, ClasseID cl) {
//    locais.insert(new TS_entry(id, tp, cl));
//  }
}
