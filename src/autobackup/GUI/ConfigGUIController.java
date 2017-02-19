/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;


import autobackup.Data.Const;
import autobackup.Data.Einstellungen;
import hilfreich.Log;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Dies ist der GUI controller für das Config Fenster.
 * @author ASDFGamer
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
    
    @FXML
    private ChoiceBox<String> backuptiefe;
    
    @FXML
    private Button einstellungsdatei;
    
    @FXML
    private Button dateibaumpfad;
    
    @FXML
    private CheckBox onlyChange;
    
    @FXML
    private TextField ftpUser;
    
    @FXML
    private TextField ftpPasswort;
    
    @FXML
    private ChoiceBox<String> versionen;
    /**
     * Mein Log
     */
    private Log log = new Log(super.getClass().getSimpleName());
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        logeigenschaften.getItems().addAll(Const.LOGEIGENSCHAFTEN.getAllText());
        logeigenschaften.getSelectionModel().select(Const.LOGEIGENSCHAFTEN.getAllText()[0]);
        logeigenschaften.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) ->
        {
            log.write("Der Logtype wurde auf " + Const.LOGEIGENSCHAFTEN.values()[newValue.intValue()].getText() + " gesetzt.");
            Einstellungen.logType.getWert().setInt(newValue.intValue());
        });
        
        backuptiefe.getItems().addAll(Const.ANZAHLEN);
        backuptiefe.getSelectionModel().select(Const.ANZAHLEN[0]);
        backuptiefe.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) ->
        {
            log.write("Die Backuptiefe wurde auf " + Const.ANZAHLEN[newValue.intValue()] + " gesetzt.");
            Einstellungen.backuptiefe.getWert().setInt(newValue.intValue());
        });
        
        versionen.getItems().addAll(Const.ANZAHLEN);
        versionen.getSelectionModel().select(Const.ANZAHLEN[0]);
        versionen.getSelectionModel().selectedIndexProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) ->
        {
            log.write("Die maximalen Versionen wurden auf " + Const.ANZAHLEN[newValue.intValue()] + " gesetzt.");
            Einstellungen.versionen.getWert().setInt(newValue.intValue());
        });
    }
    
    /**
     * Die Aktion die ausgeführt wird, wenn der Button Logordner gedrückt wird.
     * Hiermit wird der Logordner ausgewählt.
     */
    @FXML
    private void logordnerAction()
    {
        String ordner = selectFolder(Einstellungen.logFolder,logordner);
        if (ordner != null && !ordner.equals(Einstellungen.logFolder.getWert().get()))
        {
            Einstellungen.logFolder.getWert().set(ordner); 
        }
    }
    
    @FXML
    private void dateibaumpfadAction()
    {
        String ordner = selectFile(Einstellungen.dateibaumPfad,dateibaumpfad);
        if (ordner != null && !ordner.equals(Einstellungen.dateibaumPfad.getWert().get()))
        {
            Einstellungen.dateibaumPfad.getWert().set(ordner);
        }
    }

    @FXML
    private void einstellungsdateiAction()
    {
        String ordner = selectFile(Einstellungen.configFile,einstellungsdatei);
        if (ordner != null && !ordner.equals(Einstellungen.configFile.getWert().get()))
        {
            Einstellungen.configFile.getWert().set(ordner);
        }
    }
    
    @FXML
    private void ftpPasswortAction()
    {
        log.write(ftpPasswort.getText());
    }
    
}
