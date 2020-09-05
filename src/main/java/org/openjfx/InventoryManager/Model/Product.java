/*
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.inventorymanager.model;

import java.util.ArrayList;
import java.util.List;
import org.openjfx.inventorymanager.IllegalInventoryException;
import org.openjfx.inventorymanager.IllegalNameException;
import org.openjfx.inventorymanager.IllegalPartsException;
import org.openjfx.inventorymanager.IllegalPriceException;

/**
 * @author tomas
 */
public class Product {

  private final ArrayList<Part> associatedParts = new ArrayList<>();
  private int productID;
  private String name;
  private double price;
  private int inStock;
  private int min;
  private int max;

  public Product() {
    this(-1, "default", 0, 0.0, 0, 0);
  }

  public Product(int id, String name, int inv, double price, int max, int min) {
    setProductID(id);
    setName(name);
    setInStock(inv);
    setPrice(price);
    setMax(max);
    setMin(min);
  }

  /**
   * Copy constructor
   *
   * @param p A Product object
   */
  public Product(Product p) {
    setProductID(p.getProductID());
    setName(p.getName());
    setInStock(p.getInStock());
    setPrice(p.getPrice());
    setMax(p.getMax());
    setMin(p.getMin());
    associatedParts.addAll(p.getAssociatedParts());
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

  public final void setMin(int min) throws IllegalInventoryException {
    this.min = min;
  }

  public final int getMax() {
    return this.max;
  }

  public final void setMax(int max) {
    this.max = max;
  }

  public void addAssociatedPart(Part part) {
    this.associatedParts.add(part);
  }

  public List<Part> getAssociatedParts() {
    return associatedParts;
  }

  /**
   * Attempts to remove part with the given partID from the associatedParts class member. Returns
   * true if the part was found and removed, otherwise false.
   *
   * @param partID The ID of the part
   */
  public void removeAssociatedPart(int partID) {
    boolean foundPartID = false;

    // Scan entire ArrayList, remove ALL
    // occurrences of given partID (in case multiple parts of the same
    // ID (e.g., washers) are added to a product
    int index = 0;
    for (int i = 0; i < associatedParts.size(); i++) {
      // If the part ID matches
      if (associatedParts.get(i).getPartID() == partID) {
        index = i;
        foundPartID = true;
        break;
      }
    }

    // Done separately, to avoid ConcurrentModificationException
    if (foundPartID) {
      associatedParts.remove(index);
    }

  }

  public final int getProductID() {
    return this.productID;
  }

  public final void setProductID(int id) {
    this.productID = id;
  }

  /**
   * Finds the total cost of the parts comprising this Product.
   *
   * @return double Total price
   */
  public double getTotalPartPrice() {
    double totPrice = 0.0;
    totPrice =
        associatedParts
            .stream()
            .map(Part::getPrice)
            .reduce(totPrice, Double::sum);
    return totPrice;
  }

  /**
   * Validates the Product according to the given criteria, and throws RuntimeExceptions if it finds
   * any errors.
   *
   * @throws IllegalInventoryException If the current inventory is irrational
   * @throws IllegalNameException If the name is empty
   */
  public final void validate() throws IllegalInventoryException,IllegalNameException,
      IllegalPriceException {

    if (min > max || inStock > max || min > inStock) {
      throw new IllegalInventoryException();
    }
    if (productID == -1 || name.equals("")) {
      throw new IllegalNameException();
    }
    if (!(associatedParts.size() > 0)) {
      throw new IllegalPartsException();
    }
    if (getTotalPartPrice() > price) {
      throw new IllegalPriceException();
    }
  }
}
