/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;

import autobackup.Data.Einstellungen;
import autobackup.settings.ISettings;
import autobackup.settings.Settings;
import hilfreich.FileUtil;
import hilfreich.Log;
import hilfreich.LogLevel;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * Dies ist der übergeordnete GUI Controller für alle Fenster
 * @author WILDHACH
 */
public class GUIController implements Initializable {
    
    /**
     * Dies ist der Button der zum Schließen des Fensters verwendet wird.
     */
    @FXML
    private Button close;
    
    /**
     * Mit diesem Button kann der Quellordnere ausgewählt werden.
     */
    @FXML
    private Button quellordner;
    
    /**
     * Mit diesem Button kann der Zielordner ausgewählt werden.
     */
    @FXML
    private Button zielordner;
    
    /**
     * Mein Log
     */
    @FXML
    private Log log = new Log(super.getClass().getSimpleName());
    
    /**
     * Die Argumente die der Main übergeben wurden.
     */
    private static String[] args;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.setStdFilePath(Einstellungen.logFolder.get());
        log.clearLog();
        log.write("GUI wird initialisiert.");
    }
    
    /**
     * Dies ist die Action die aufgerufen wird, wenn der Close Button gedrückt wird.
     * Hiermit wird das Fenster geschlossen.
     */
    @FXML
    private void closeAction()
    {
        log.write("Das Fenster oder die Anwendung wird regulär beendet.");
        Stage stage = (Stage)close.getScene().getWindow();
        stage.close();
    }
    
    /**
     * Dies ist die Action die aufgerufen wird, wenn der quellordner Button gedrückt wird.
     * Hiermit wird der Quellordner ausgewählt.
     */
    @FXML
    private void quellordnerAction()
    {
        ISettings settings = new Settings(Einstellungen.configFile.get());
        String ordner = selectFolder(Einstellungen.namen.quellOrdner.toString(),settings,quellordner);
        if (ordner != null && !ordner.equals(Einstellungen.quellOrdner.get()))
        {
            Einstellungen.quellOrdner.set(ordner);
            settings.saveSettings(); 
        }
    }
    
    /**
     * Dies ist die Action die aufgerufen wird, wenn der zielordner Button gedrückt wird.
     * Hiermit wird der Zielordner ausgewählt.
     */
    @FXML
    private void zielordnerAction()
    {
        ISettings settings = new Settings(Einstellungen.configFile.get());
        String ordner = selectFolder(Einstellungen.namen.zielOrdner.toString(),settings,zielordner);
        if (ordner != null && !ordner.equals(Einstellungen.zielOrdner.get()))
        {
            Einstellungen.zielOrdner.set(ordner);
            settings.saveSettings(); 
        }
    }
    
    /**
     * Hirmit werden die argumente angegeben die Main übergeben wurden.
     * @param args Die Startparamenter
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean setArgs(String[] args)
    {
        this.args = args;
        return true;
    }
    
    /**
     * Dioes gibt die Startparameter aus.
     * @return Die Startparameter
     */
    public String[] getArgs()
    {
        return args;
    }
    
    /**
     * Dies öffnet ein Fenster bei dem der Ordner ausgewählt werden kann.
     * @param name Der Name der Einstellung
     * @param settings Das Einstellungen Objekt
     * @param aufrufer Der Aufrufer, damit das Rchtige Fenster als Owner gesetzt wird.
     * @return Den ausgewählten Ordner, oder null falls abgebrochen wurde.
     */
    protected String selectFolder(String name, ISettings settings, Button aufrufer)
    {        
        Einstellungen.load(settings);
        DirectoryChooser chooser = new DirectoryChooser();
        if (settings.loadSettingsResult() && settings.settingexists(name) && FileUtil.isFolder(settings.getSetting(name)))
        {
            chooser.setInitialDirectory(new File(settings.getSetting(name)));
        }
        chooser.setTitle(name);
        File ordner = chooser.showDialog((Stage)aufrufer.getScene().getWindow()); 
        if(ordner == null)
        {
            log.write("Es wurde keine Datei ausgewählt",LogLevel.WARNUNG);
            return null;
        }
        return ordner.getAbsolutePath();
    }
}
