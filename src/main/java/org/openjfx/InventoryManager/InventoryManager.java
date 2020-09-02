/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.openjfx.inventorymanager;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.openjfx.inventorymanager.model.Inventory;

/**
 * @author tomas
 */
public class InventoryManager extends Application {

  public static Inventory inv;

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(@org.jetbrains.annotations.NotNull Stage stage) throws Exception {
    inv = new Inventory();

    // Attach user interface
    final URL mainScreen = getClass().getResource("/View_Controller/MainScreen.fxml");
    Parent root = FXMLLoader.load(mainScreen);
    Scene scene = new Scene(root);
    scene.getStylesheets().add(
        InventoryManager.class.getResource("/View_Controller/InventoryManager.css")
            .toExternalForm()
    );

    stage.setTitle("Inventory Manager");
    stage.setScene(scene);
    stage.show();

  }
}
