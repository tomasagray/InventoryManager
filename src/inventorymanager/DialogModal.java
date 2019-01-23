/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventorymanager;

import View_Controller.AddModifyPartController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author tomas
 */
public class DialogModal
{
    private String fxmlURL;
    private int width;
    private int height;
    
    private FXMLLoader loader;
    private Stage modalDialog;
    private Parent modalDialogRoot;
    private Scene modalScene;
    
    public DialogModal( String url, int w, int h )
    {
        fxmlURL = url;
        width = w;
        height = h;
        
        try { 
            loader = new FXMLLoader(getClass().getResource( fxmlURL ));
            modalDialog = new Stage();
            modalDialogRoot = loader.load();
            modalScene = new Scene( modalDialogRoot, width, height );
            modalScene.getStylesheets().add(
                    InventoryManager.class.getResource("/View_Controller/InventoryManager.css").toExternalForm()
            );
            modalDialog.setScene(modalScene);
            modalDialog.setResizable(false);
            
        } catch( IOException e) {
            System.out.println("Cannot find required FXML file: " + fxmlURL );
            e.printStackTrace();
        }
    }
    
    public FXMLLoader getLoader()
    {
        return loader;
    }
    
    public void showDialogModal( Button root )
    {
        modalDialog.initOwner(root.getScene().getWindow());
        modalDialog.showAndWait();
    }
}
