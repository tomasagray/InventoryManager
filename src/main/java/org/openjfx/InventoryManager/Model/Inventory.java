/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.inventorymanager.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tomas
 */
public class Inventory {

  private final ArrayList<Product> products = new ArrayList<>();
  private final ArrayList<Part> allParts = new ArrayList<>();
  private int nextPartID;
  private int nextProductID;

  public void addProduct(Product product) {
    products.add(product);
    nextProductID++;
  }

  public void addProducts(List<Product> products) {
    for (Product p : products) {
      addProduct(p);
    }
  }

  public List<Product> getProducts() {
    return products;
  }

  /**
   * Attempt to remove the product with the given product ID.
   *
   * @param productID The ID of the Product
   */
  public void removeProduct(int productID) {
    Product p = lookupProduct(productID);
    if (p != null) {
      products.remove(p);
    }
  }

  /**
   * Search the inventory for the given productID
   *
   * @param productID int
   * @return The Product, or null of not found
   */
  public Product lookupProduct(int productID) {
    if (!products.isEmpty()) {
      for (Product p : products) {
          if (p.getProductID() == productID) {
              return p;
          }
      }
    }

    // The given productID was not found,
    // or products is empty
    return null;
  }

  public void addPart(Part part) {
    allParts.add(part);
    nextPartID++;
  }

  public void addParts(List<Part> parts) {
    parts.forEach(this::addPart);
  }

  public ArrayList<Part> getParts() {
    return allParts;
  }

  /**
   * Remove the given part from the inventory. Returns true if the part was successfully removed,
   * false otherwise.
   *
   * @param part The Part to be removed
   */
  public void deletePart(Part part) {
    int index = 0;
    boolean partFound = false;

    for (int i = 0; i < allParts.size(); i++) {
      if (allParts.get(i).getPartID() == part.getPartID()) {
        index = i;      // we've found our target
        partFound = true;
        break;          // stop searching
      }
    }

    // Delete the part from allParts. Done separately to avoid
    // ConcurrentModificationException
      if (partFound) {
          allParts.remove(index);
      }
  }

  /**
   * Search the inventory for the given part ID.
   *
   * @param partID The ID of the Part
   * @return The Part, or null if not found
   */
  public Part lookupPart(int partID) {
    if (!allParts.isEmpty()) {
      for (Part p : allParts) {
          if (p.getPartID() == partID) {
              return p;
          }
      }
    }

    // Part ID was not found or allParts is empty
    return null;
  }

  public int getNextPartID() {
    return nextPartID;
  }

  public int getNextProductID() {
    return nextProductID;
  }
}
