/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;


import autobackup.Data.Const;
import autobackup.Data.Einstellungen;
import hilfreich.Log;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 *
 * @author WILDHACH
 */
public class ConfigGUIController extends GUIController{
    
    @FXML
    private Button logordner;    
    
    @FXML
    private ComboBox logeigenschaften;
    
    Log log = new Log(super.getClass().getSimpleName());
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.write("ConfigGUI wird intialisiert.");
        logeigenschaften.getItems().addAll(Const.LOGEIGENSCHAFTEN);
        logeigenschaften.getSelectionModel().select(Const.LOGEIGENSCHAFTEN[0]);
        
    }
    
    @FXML
    private void logordnerAction()
    {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Logordner");
        File logordner = chooser.showDialog((Stage)this.logordner.getScene().getWindow());
        Einstellungen.logFolder.set(logordner.getAbsolutePath());
    }

    
}
