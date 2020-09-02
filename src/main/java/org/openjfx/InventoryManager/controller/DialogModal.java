/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.inventorymanager.controller;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.openjfx.inventorymanager.InventoryManager;

/**
 * @author tomas
 */
public class DialogModal {

  private FXMLLoader loader;
  private Stage modalDialog;

  public DialogModal(String url, int w, int h) {

    try {
      loader = new FXMLLoader(getClass().getResource(url));
      modalDialog = new Stage();
      Parent modalDialogRoot = loader.load();
      Scene modalScene = new Scene(modalDialogRoot, w, h);
      modalScene.getStylesheets().add(
          InventoryManager.class.getResource("/View_Controller/InventoryManager.css")
              .toExternalForm()
      );
      modalDialog.setScene(modalScene);
      modalDialog.setResizable(false);

    } catch (IOException e) {
      System.out.println("Cannot find required FXML file: " + url);
      e.printStackTrace();
    }
  }

  public FXMLLoader getLoader() {
    return loader;
  }

  public void showDialogModal(Button root) {
    modalDialog.initOwner(root.getScene().getWindow());
    modalDialog.showAndWait();
  }
}
