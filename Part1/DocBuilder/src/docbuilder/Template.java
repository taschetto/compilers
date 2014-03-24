/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package docbuilder;

import oracle.jrockit.jfr.tools.ConCatRepository;

/**
 *
 * @author Guilherme Taschetto
 */
public enum Template {
  Contract,
  Receipt,
  Certificate;
  
  @Override
  public String toString()
  {
    switch(this)
    {
      case Contract:
        return "Contrato";
      case Certificate:
        return "Certificado";
      case Receipt:
        return "Recibo";
      default:
        return "";
    }
  }
}
