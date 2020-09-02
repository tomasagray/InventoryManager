/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.inventorymanager.model;

/**
 * @author tomas
 */
public class Outsourced extends Part {

  private String companyName;

  public Outsourced() {
    super();
  }

  /**
   * Copy constructor
   *
   * @param part The Part to be copied
   */
  public Outsourced(Outsourced part) {
    this.setPartID(part.getPartID());
    this.setName(part.getName());
    this.setInStock(part.getInStock());
    this.setMax(part.getMax());
    this.setMin(part.getMin());
    this.setPrice(part.getPrice());
    this.setCompanyName(part.getCompanyName());
  }

  public Outsourced(Integer id, String name, Integer inv, Double price, Integer max, Integer min) {
    super(id, name, inv, price, max, min);
  }

  public final String getCompanyName() {
    return this.companyName;
  }

  public final void setCompanyName(String name) {
    this.companyName = name;
  }
}
