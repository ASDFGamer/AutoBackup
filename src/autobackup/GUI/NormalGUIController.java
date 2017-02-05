/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;

import autobackup.AutoBackup;
import hilfreich.Log;
import hilfreich.LogLevel;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 *
 * @author WILDHACH
 */
public class NormalGUIController extends GUIController
{
    @FXML
    Button erweiterteEinstellungen;
    
    
    
    Log log = new Log(super.getClass().getSimpleName());
    
    public void startBackup() 
    {
        AutoBackup backup = new AutoBackup();
        backup.backup(getArgs());
    }
    
    public void moreConfig()
    {
        Stage stage = new Stage();
        try {
            Stage thisStage = (Stage)erweiterteEinstellungen.getScene().getWindow();
            ConfigStage confstage = new ConfigStage(stage,thisStage);
        } catch (IOException ex) {
            log.write("Es gab ein Problem beim öffnen der Configseite",LogLevel.FEHLER);
            ex.printStackTrace();
            stage.close();
        }
    }
    
    
}
