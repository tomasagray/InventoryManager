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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.inventorymanager.IllegalInventoryException;
import org.openjfx.inventorymanager.IllegalNameException;
import org.openjfx.inventorymanager.IllegalPartsException;
import org.openjfx.inventorymanager.IllegalPriceException;
import org.openjfx.inventorymanager.InventoryManager;
import org.openjfx.inventorymanager.model.Part;
import org.openjfx.inventorymanager.model.Product;


/**
 * FXML Controller class
 *
 * @author tomas
 */
public class AddModifyProductController implements Initializable {

  // UI components
  @FXML
  private Label productHeading;

  @FXML
  private TextField productID;
  @FXML
  private TextField productName;
  @FXML
  private TextField productInv;
  @FXML
  private TextField productPrice;
  @FXML
  private TextField productMax;
  @FXML
  private TextField productMin;

  @FXML
  private Button partSearchButton;
  @FXML
  private TextField partSearch;
  @FXML
  private Button cancelProductButton;

  @FXML
  private TableView<Part> partSearchResults;
  @FXML
  private TableColumn<Part, Integer> partSearchPartID;
  @FXML
  private TableColumn<Part, String> partSearchPartName;
  @FXML
  private TableColumn<Part, Integer> partSearchPartInv;
  @FXML
  private TableColumn<Part, Double> partSearchPartPrice;

  @FXML
  private TableView<Part> partsInProduct;
  @FXML
  private TableColumn<Part, Integer> partProductPartID;
  @FXML
  private TableColumn<Part, String> partProductPartName;
  @FXML
  private TableColumn<Part, Integer> partProductPartInv;
  @FXML
  private TableColumn<Part, Double> partProductPartPrice;

  // Local Product object represented by the modal.
  private Product product;

  @FXML
  private void handlePartSearch() {
      if (partSearch.getText().equals("")) {
          partSearchResults.getItems().setAll(InventoryManager.inv.getParts());
      } else {
          try {
              Part p = InventoryManager.inv.lookupPart(Integer.parseInt(partSearch.getText()));
              partSearchResults.getItems().setAll(p);
          } catch (NumberFormatException e) {
              IMSWarning.showWarning("Please enter a valid Part ID");
              System.out.println(partSearch.getText());
          }
      }
  }

  @FXML
  private void handleAddPart() {
    Part p = partSearchResults.getSelectionModel().getSelectedItem();
    int i = partSearchResults.getSelectionModel().getSelectedIndex();
    if (i != -1) {
      product.addAssociatedPart(p);
      partsInProduct.getItems().setAll(product.getAssociatedParts());
    }
  }

  @FXML
  private void handleDeletePart() {
    Part p = partsInProduct.getSelectionModel().getSelectedItem();
    int i = partsInProduct.getSelectionModel().getSelectedIndex();
    if (i != -1) {
      if (IMSWarning.showConfirmation("Delete " + p.getName() + "?")) {
        product.removeAssociatedPart(p.getPartID());
        partsInProduct.getItems().setAll(product.getAssociatedParts());
      }
    }
  }

  /**
   * If the product id number is already in Inventory, replace it with the current product.
   * Otherwise, add a new product. This method also validates form data, showing a warning relevant
   * to any errors.
   */
  @FXML
  private void handleSaveProduct() throws IllegalNameException {
    try {   // Validate product form
      product.setName(productName.getText());
      if (product.getProductID() == -1) {
        product.setProductID(InventoryManager.inv.getNextProductID());
      } else {
        // Are we adding a new product or updating one? If this product
        // ID is already in use, remove old version
        if (InventoryManager.inv.lookupProduct(product.getProductID()) != null) {
          InventoryManager.inv.removeProduct(product.getProductID());
        }
      }

      // Exceptions are thrown for invalid data
      product.setInStock(Integer.parseInt(productInv.getText()));
      product.setPrice(Double.parseDouble(productPrice.getText()));
      product.setMax(Integer.parseInt(productMax.getText()));
      product.setMin(Integer.parseInt(productMin.getText()));

      product.validate();

      // If everything has passed muster
      InventoryManager.inv.addProduct(product);
      closeModal();

    } catch (IllegalInventoryException e) {
      IMSWarning.showWarning("Inventory minimum must be less than maximum.");
    } catch (IllegalPartsException e) {
      IMSWarning.showWarning("Please ensure at least 1 part is associated with product.");
    } catch (IllegalPriceException e) {
      IMSWarning.showWarning("The price entered for this product is less than the\n"
          + "parts which comprise it. Please enter a higher price.");
    } catch (NumberFormatException e) {
      IMSWarning.showWarning("Illegal numerical data, please correct: " + e.getMessage());
    }
  }

  @FXML
  private void handleCancelModal() {
      if (IMSWarning.showConfirmation("CANCEL: Are you sure? All entered data will be lost.")) {
          closeModal();
      }
  }


  private void closeModal() {
    cancelProductButton.getScene().getWindow().hide();
  }

  /**
   * Gets a Product object from the main window, and populates the modal form with the object's
   * data. Also synchronizes the local product object.
   *
   * @param p The Product object
   */
  public void initData(Product p) {
    // Copy passed data to product object for modal
    product = new Product(p);

    productHeading.setText("Modify Product");
    productID.setText(product.getProductID() + "");
//        productID.setDisable(false);
    productName.setText(product.getName());
    productInv.setText(product.getInStock() + "");
    productPrice.setText(String.format("%.2f", product.getPrice()));
    productMax.setText(product.getMax() + "");
    productMin.setText(product.getMin() + "");

    // Populate component parts table
    partsInProduct.getItems().setAll(product.getAssociatedParts());
  }

  /**
   * Initializes the controller class.
   *
   * @param url The URL
   * @param rb ResourceBundle
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // Initialize product instance
    product = new Product();

    // Initialize tables
    partSearchPartID.setCellValueFactory(new PropertyValueFactory<>("partID"));
    partSearchPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
    partSearchPartInv.setCellValueFactory(new PropertyValueFactory<>("inStock"));
    partSearchPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    partProductPartID.setCellValueFactory(new PropertyValueFactory<>("partID"));
    partProductPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
    partProductPartInv.setCellValueFactory(new PropertyValueFactory<>("inStock"));
    partProductPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    // Disable product ID field (auto-generated)
    productID.setDisable(true);

    // Auto-populate parts table for available parts
    partSearchResults.getItems().setAll(InventoryManager.inv.getParts());
  }

}
