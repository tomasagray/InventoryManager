/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.inventorymanager.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.openjfx.inventorymanager.InventoryManager;
import org.openjfx.inventorymanager.model.Part;
import org.openjfx.inventorymanager.model.Product;
import org.openjfx.inventorymanager.Populator;

/**
 * Controller for main user interface screen.
 *
 * @author tomas
 */
public class MainScreenController implements Initializable {

  // UI components
  @FXML
  private TextField partSearch;
  @FXML
  private TableView<Part> partsList;
  @FXML
  private TableColumn<Part, Integer> partID;
  @FXML
  private TableColumn<Part, String> partName;
  @FXML
  private TableColumn<Part, Integer> partInv;
  @FXML
  private TableColumn<Part, Double> partPrice;
  @FXML
  private Button addPartButton;
  @FXML
  private Button modifyPartButton;
  @FXML
  private Button deletePartButton;

  @FXML
  private TextField productSearch;
  @FXML
  private TableView<Product> productsList;
  @FXML
  private TableColumn<Product, Integer> productID;
  @FXML
  private TableColumn<Product, String> productName;
  @FXML
  private TableColumn<Product, Integer> productInv;
  @FXML
  private TableColumn<Product, Double> productPrice;
  @FXML
  private Button addProductButton;
  @FXML
  private Button modifyProductButton;
  @FXML
  private Button deleteProductButton;

  /**
   * Searches Inventory for the part number specified in the partSearch TextField.
   */
  @FXML
  private void handlePartSearch() {
      if (partSearch.getText().equals("")) {
          partsList.getItems().setAll(InventoryManager.inv.getParts());
      } else {
          try {
              Part p = InventoryManager.inv
                  .lookupPart(Integer.parseInt(partSearch.getText()));
              partsList.getItems().setAll(p);
          } catch (NumberFormatException e) {
              IMSWarning.showWarning("Please enter a valid Part ID");
              System.out.println(partSearch.getText());
          }
      }
  }

  /**
   * Searches Inventory for the product ID number specified in the productSearch TextField.
   */
  @FXML
  private void handleProductSearch() {
      if (productSearch.getText().equals("")) {
          productsList.getItems().setAll(InventoryManager.inv.getProducts());
      } else {
          try {
              Product p = InventoryManager.inv
                  .lookupProduct(Integer.parseInt(productSearch.getText()));
              productsList.getItems().setAll(p);
          } catch (NumberFormatException e) {
              IMSWarning.showWarning("Please enter a valid Product ID");
              System.out.println(productSearch.getText());
          }
      }
  }


  /**
   * Show the Add Part modal dialog
   *
   * @param e ActionEvent the event that triggered the method call.
   */
  @FXML
  private void handleAddPart(ActionEvent e) {
    DialogModal addPart = new DialogModal("/View_Controller/AddModifyPart.fxml", 400, 450);
    addPart.showDialogModal((Button) e.getSource());

    // Re-populate tables
    partsList.getItems().setAll(InventoryManager.inv.getParts());
  }

  /**
   * Show the Add Part dialog, but populate it with data from the Part object selected in the
   * partsList TableView. Also, change the "Add Part" Label to "Modify Part".
   *
   * @param e ActionEvent the event that triggered the method call.
   */
  @FXML
  private void handleModifyPart(ActionEvent e) {
    // If no part is selected, silently return
      if (partsList.getSelectionModel().getSelectedIndex() == -1) {
          return;
      }

    DialogModal modifyPart = new DialogModal("/View_Controller/AddModifyPart.fxml", 400, 450);
    FXMLLoader loader = modifyPart.getLoader();
    AddModifyPartController controller
        = loader.getController();
    controller.initData(partsList.getSelectionModel().getSelectedItem());
    modifyPart.showDialogModal((Button) e.getSource());

    // Re-populate tables
    partsList.getItems().setAll(InventoryManager.inv.getParts());
  }

  /**
   * Delete the Part object selected in the partsList TableView from Inventory.
   */
  @FXML
  private void handleDeletePart() {
    Part p = partsList.getSelectionModel().getSelectedItem();
    int i = partsList.getSelectionModel().getSelectedIndex();
    if (i != -1) {
      if (IMSWarning.showConfirmation("Delete " + p.getName() + "?")) {
        InventoryManager.inv.deletePart(p);
        partsList.getItems().setAll(InventoryManager.inv.getParts());
      }
    }
  }

  /**
   * Show the Add Product modal dialog
   */
  @FXML
  private void handleAddProduct(ActionEvent e) {
    DialogModal addProduct = new DialogModal("/View_Controller/AddModifyProduct.fxml", 800, 500);
    addProduct.showDialogModal((Button) e.getSource());

    // Refresh table
    productsList.getItems().setAll(InventoryManager.inv.getProducts());
  }

  @FXML
  private void handleModifyProduct(ActionEvent e) {
    // If no product is selected, silently return
      if (productsList.getSelectionModel().getSelectedIndex() == -1) {
          return;
      }

    DialogModal modifyProduct = new DialogModal("/View_Controller/AddModifyProduct.fxml", 800, 500);
    FXMLLoader loader = modifyProduct.getLoader();
    AddModifyProductController controller
        = loader.getController();
    controller.initData(productsList.getSelectionModel().getSelectedItem());
    modifyProduct.showDialogModal((Button) e.getSource());

    // Re-populate tables
    productsList.getItems().setAll(InventoryManager.inv.getProducts());
  }

  @FXML
  private void handleDeleteProduct() {
    Product p = productsList.getSelectionModel().getSelectedItem();
    int i = productsList.getSelectionModel().getSelectedIndex();
    if (i != -1) {
      if (IMSWarning.showConfirmation("Delete " + p.getName() + "?")) {
        InventoryManager.inv.removeProduct(p.getProductID());
        productsList.getItems().setAll(InventoryManager.inv.getProducts());
      }
    }
  }

  /**
   * Quit the program
   */
  @FXML
  private void handleExit(ActionEvent event) {
    System.exit(0);
  }

  /**
   * @param url The URL
   * @param rb ResourceBundle
   */
  @Override
  public void initialize(URL url, ResourceBundle rb) {
    // Initialize tables
    partID.setCellValueFactory(new PropertyValueFactory<>("partID"));
    partName.setCellValueFactory(new PropertyValueFactory<>("name"));
    partInv.setCellValueFactory(new PropertyValueFactory<>("inStock"));
    partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
    productName.setCellValueFactory(new PropertyValueFactory<>("name"));
    productInv.setCellValueFactory(new PropertyValueFactory<>("inStock"));
    productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

    // Populate tables
    InventoryManager.inv.addParts(Populator.populatePartsTable());
    partsList.getItems().setAll(InventoryManager.inv.getParts());
    InventoryManager.inv.addProducts(Populator.populateProductsTable());
    productsList.getItems().setAll(InventoryManager.inv.getProducts());

  }
}
