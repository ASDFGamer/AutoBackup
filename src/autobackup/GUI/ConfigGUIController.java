/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;


import autobackup.Data.Const;
import autobackup.Data.Einstellungen;
import autobackup.settings.ISettings;
import autobackup.settings.Settings;
import hilfreich.Log;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

/**
 * Dies ist der GUI controller für das Config Fenster.
 * @author WILDHACH
 */
public class ConfigGUIController extends GUIController{
    
    /**
     * Der button mit dem der Logorder ausgewählt wird.
     */
    @FXML
    private Button logordner;    
    
    /**
     * Die Box bei der die Logeigenschaften ausgewählt werden können.
     */
    @FXML
    private ComboBox<String> logeigenschaften;
    
    /**
     * Mein Log
     */
    Log log = new Log(super.getClass().getSimpleName());
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.write("ConfigGUI wird intialisiert.");
        logeigenschaften.getItems().addAll(Const.LOGEIGENSCHAFTEN);
        logeigenschaften.getSelectionModel().select(Const.LOGEIGENSCHAFTEN[0]);
        
    }
    
    /**
     * Die Aktion die ausgeführt wird, wenn der Button Logordner gedrückt wird.
     * Hiermit wird der Logordner ausgewählt.
     */
    @FXML
    private void logordnerAction()
    {
        ISettings settings = new Settings(Einstellungen.configFile.get());
        String ordner = selectFolder(Einstellungen.namen.logFolder.toString(),settings,logordner);
        if (ordner != null && !ordner.equals(Einstellungen.logFolder.get()))
        {
            Einstellungen.logFolder.set(ordner);
            settings.saveSettings(); 
        }
    }

    
}
