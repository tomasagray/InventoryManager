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

/**
 * @author tomas
 */
public class Inhouse extends Part {

  private int machineID;

  public Inhouse() {
    super();
  }

  /**
   * Copy constructor.
   *
   * @param part The Part object to be copied
   */
  public Inhouse(Inhouse part) {
    this.setPartID(part.getPartID());
    this.setName(part.getName());
    this.setInStock(part.getInStock());
    this.setMax(part.getMax());
    this.setMin(part.getMin());
    this.setPrice(part.getPrice());
    this.setMachineID(part.getMachineID());
  }

  public Inhouse(Integer id, String name, Integer inv, Double price, Integer max, Integer min) {
    super(id, name, inv, price, max, min);
  }

  public final int getMachineID() {
    return this.machineID;
  }

  public final void setMachineID(int id) {
    this.machineID = id;
  }

}
