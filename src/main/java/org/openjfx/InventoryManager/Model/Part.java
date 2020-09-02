/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.inventorymanager.model;

import org.openjfx.inventorymanager.IllegalInventoryException;
import org.openjfx.inventorymanager.IllegalNameException;

/**
 * Represents a part in inventory.
 *
 * @author tomas
 */
public abstract class Part {

  private int partID;
  private String name;
  private double price;
  private int inStock;
  private int min;
  private int max;

  public Part() {
    this(-1, "", 0, 0.0, 0, 0);
  }

  public Part(int id, String name, int inv, double price, int max, int min) {
    setPartID(id);
    setName(name);
    setInStock(inv);
    setPrice(price);
    setMax(max);
    setMin(min);
  }

  public final String getName() {
    return this.name;
  }

  public final void setName(String name) {
    this.name = name;
  }

  public final double getPrice() {
    return this.price;
  }

  public final void setPrice(double price) {
    this.price = price;
  }

  public final int getInStock() {
    return this.inStock;
  }

  public final void setInStock(int qty) {
    this.inStock = qty;
  }

  public final int getMin() {
    return this.min;
  }

  public final void setMin(int min) {
    this.min = min;
  }

  public final int getMax() {
    return this.max;
  }

  public final void setMax(int max) {
    this.max = max;
  }

  public final int getPartID() {
    return this.partID;
  }

  public final void setPartID(int id) {
    this.partID = id;
  }

  /**
   * Validates the Part object according to the given criteria. Throws relevant exceptions to be
   * handled by the caller.
   *
   * @throws IllegalInventoryException If inventory is irrational
   * @throws IllegalNameException If the name is blank
   */
  public void validate() throws IllegalInventoryException, IllegalNameException {
    if (min > max || inStock > max || min > inStock) {
      throw new IllegalInventoryException();
    }
    if (partID == -1 || name.equals("")) {
      throw new IllegalNameException();
    }
  }
}
