/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;

import autobackup.AutoBackup;
import hilfreich.Log;
import static hilfreich.LogLevel.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * Der Controller für das Normale GUI
 * @author ASDFGamer
 */
public class NormalGUIController extends GUIController
{
    /**
     * Der Button für erweiterte Einstellungen.
     */
    @FXML
    private Button erweiterteEinstellungen;
    
    /**
     * Mein Log
     */
    private Log log = new Log(super.getClass().getSimpleName());
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
    }
    
    /**
     * Dies wird von dem Backup Button aufgerufen und startet das Backup.
     */
    public void startBackup() 
    {
        AutoBackup backup = new AutoBackup();
        backup.backup(getArgs());
    }
    
    /**
     * Dies  wird von dem Button für erweiterte Einstellungen aufgerufen und öffnet ein Configfenster.
     */
    public void moreConfig()
    {
        Stage stage = new Stage();
        try {
            Stage thisStage = (Stage)erweiterteEinstellungen.getScene().getWindow();
            ConfigStage confstage = new ConfigStage(stage,thisStage);
        } catch (IOException ex) {
            log.write("Es gab ein Problem beim öffnen der Configseite",FEHLER);
            ex.printStackTrace();
            stage.close();
        }
    }
    
    
}
