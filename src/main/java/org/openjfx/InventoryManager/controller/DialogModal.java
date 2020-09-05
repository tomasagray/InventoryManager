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
