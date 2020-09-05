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
package org.openjfx.inventorymanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.openjfx.inventorymanager.model.Inhouse;
import org.openjfx.inventorymanager.model.Outsourced;
import org.openjfx.inventorymanager.model.Part;
import org.openjfx.inventorymanager.model.Product;

/**
 * Class to automatically generate collections of randomly populated Part and Product objects.
 *
 * @author tomas
 */
public class Populator {

  private final static int NUM_RECORDS = 20;

  public static List<Part> populatePartsTable() {

    List<Part> parts = new ArrayList<>();
    Random r = new Random();
    for (int i = 0; i <= NUM_RECORDS; i++) {
      int inventory = r.nextInt(500);
      double price = Math.round((10 + 100 * r.nextDouble()) * 100) / 100.0;
      if (i % 2 == 0) {
        parts.add(new Inhouse(i, "Part " + i, inventory, price, i + 10, i));
      } else {
        parts.add(new Outsourced(i, "Part " + i, inventory, price, i + 10, i));
      }
    }
    return parts;
  }

  public static List<Product> populateProductsTable() {
    List<Product> products = new ArrayList<>();
    Random r = new Random();
    for (int i = 0; i <= NUM_RECORDS; i++) {
      int inventory = r.nextInt(500);
      double price = Math.round((10 + 100 * r.nextDouble()) * 100) / 100.0;
      products.add(new Product(i, "Product " + i, inventory, price, i + 10, i));
    }

    return products;
  }
}    
