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
package org.openjfx.inventorymanager.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import org.openjfx.inventorymanager.IllegalInventoryException;
import org.openjfx.inventorymanager.IllegalNameException;
import org.openjfx.inventorymanager.InventoryManager;
import org.openjfx.inventorymanager.model.Inhouse;
import org.openjfx.inventorymanager.model.Outsourced;
import org.openjfx.inventorymanager.model.Part;

/**
 * @author tomas
 */
public class AddModifyPartController implements Initializable {

  @FXML
  private Label heading;
  @FXML
  private RadioButton inHouseRadio;
  @FXML
  private RadioButton outSourcedRadio;

  @FXML
  private TextField partID;
  @FXML
  private TextField partName;
  @FXML
  private TextField partInv;
  @FXML
  private TextField partPrice;
  @FXML
  private TextField partMax;
  @FXML
  private TextField partMin;

  // Toggle-able fields
  @FXML
  private Label partCompanyMachineLabel;
  @FXML
  private TextField partCompanyMachineField;

  @FXML
  private Button partSave;
  @FXML
  private Button partCancel;

  private Inhouse inHousePart = new Inhouse();
  private Outsourced outsourcedPart = new Outsourced();

  /**
   * Change the machine ID / company name Label and synchronize Part objects.
   */
  @FXML
  private void setInHousePart() {
    inHouseRadio.setSelected(true);
    inHousePart.setPartID(outsourcedPart.getPartID());
    partCompanyMachineLabel.setText("Machine ID");
  }


  /**
   * Change the machine ID / company name Label and synchronize Part objects.
   */
  @FXML
  private void setOutsourcedPart() {
    outSourcedRadio.setSelected(true);
    outsourcedPart.setPartID(inHousePart.getPartID());
    partCompanyMachineLabel.setText("Company Name");
  }


  private void closeModal() {
    partCancel.getScene().getWindow().hide();
  }

  /**
   * If the part ID in the partID TextField matches a part ID already in Inventory, delete that part
   * and replace it with the local form data. Otherwise, add a new Part.
   * <p>
   * Also validates form data.
   */
  @FXML
  private void handleSaveParts() {
    try {
      int inv = Integer.parseInt(partInv.getText());
      double price = Double.parseDouble(partPrice.getText());
      int max = Integer.parseInt(partMax.getText());
      int min = Integer.parseInt(partMin.getText());

      if (inHouseRadio.isSelected()) {
        int machineID = Integer.parseInt(partCompanyMachineField.getText());
        inHousePart.setMachineID(machineID);

        inHousePart.setName(partName.getText());
        inHousePart.setInStock(inv);
        inHousePart.setPrice(price);
        inHousePart.setMax(max);
        inHousePart.setMin(min);

        // If we're adding a new part, get a new part ID
          if (inHousePart.getPartID() == -1) {
              inHousePart.setPartID(InventoryManager.inv.getNextPartID());
          } else // we're updating a part...
          {
              InventoryManager.inv.deletePart(inHousePart);
          }

        inHousePart.validate();
        InventoryManager.inv.addPart(inHousePart);

      } else {
        outsourcedPart.setCompanyName(partCompanyMachineField.getText());
        outsourcedPart.setName(partName.getText());
        outsourcedPart.setInStock(inv);
        outsourcedPart.setPrice(price);
        outsourcedPart.setMax(max);
        outsourcedPart.setMin(min);

        // If we're adding a new part, get a new part ID
          if (outsourcedPart.getPartID() == -1) {
              outsourcedPart.setPartID(InventoryManager.inv.getNextPartID());
          } else // we're updating a part...
          {
              InventoryManager.inv.deletePart(outsourcedPart);
          }

        outsourcedPart.validate();
        InventoryManager.inv.addPart(outsourcedPart);
      }

      // Close & reset modal
      closeModal();

    } catch (IllegalNameException | NumberFormatException e) {
      IMSWarning.showWarning("Ensure correct data types.\nAll fields required.");
    } catch (IllegalInventoryException e) {
      IMSWarning.showWarning("Inventory minimum must be less than maximum");
    }
  }

  @FXML
  private void handleCancelModal() {
      if (IMSWarning.showConfirmation("CANCEL: Are you sure? All entered data will be lost.")) {
          closeModal();
      }
  }

  /**
   * Initialize modal with data from a Part object. Also, synchronize the local Part object(s).
   *
   * @param p The Part
   */
  public void initData(Part p) {
    heading.setText("Modify Part");
//        partID.setDisable(false);
    partID.setText(p.getPartID() + "");
    partName.setText(p.getName() + "");
    partInv.setText(p.getInStock() + "");
    partPrice.setText(String.format("%.2f", p.getPrice()));
    partMax.setText(p.getMax() + "");
    partMin.setText(p.getMin() + "");

    if (p instanceof Inhouse) {
      setInHousePart();
      inHousePart = new Inhouse((Inhouse) p);
      int id = inHousePart.getMachineID();
      partCompanyMachineField.setText(Integer.toString(id));
    } else {
      setOutsourcedPart();
      outsourcedPart = new Outsourced((Outsourced) p);
      partCompanyMachineField.setText(outsourcedPart.getCompanyName());
    }
  }

  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // Disable part ID input (auto-generated)
    partID.setDisable(true);
    // Default setting
    setInHousePart();

    // Instantiate part object for modal
    inHousePart = new Inhouse();
    outsourcedPart = new Outsourced();
  }
}
