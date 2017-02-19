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
import static hilfreich.LogLevel.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Dies ist der übergeordnete GUI Controller für alle Fenster
 * @author ASDFGamer
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
     * Dies ist das Einstellungelement
     */
    private ISettings settings;
    
    /**
     * Die Argumente die der Main übergeben wurden.
     */
    private static String[] args;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.setStdFilePath(Einstellungen.logFolder.getWert().get());
        log.clearLog();
        log.write("GUI wird initialisiert.");
        settings = new Settings(Einstellungen.configFile.getWert().get());
    }
    
    /**
     * Dies ist die Action die aufgerufen wird, wenn der Close Button gedrückt wird.
     * Hiermit wird das Fenster geschlossen.
     */
    @FXML
    private void closeAction()
    {
        settings.saveSettings();
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
        
        String ordner = selectFolder(Einstellungen.quellOrdner,quellordner);
        if (ordner != null && !ordner.equals(Einstellungen.quellOrdner.getWert().get()))
        {
            Einstellungen.quellOrdner.getWert().set(ordner);
            //settings.saveSettings(); 
        }
    }
    
    /**
     * Dies ist die Action die aufgerufen wird, wenn der zielordner Button gedrückt wird.
     * Hiermit wird der Zielordner ausgewählt.
     */
    @FXML
    private void zielordnerAction()
    {
        String ordner = selectFolder(Einstellungen.zielOrdner,zielordner);
        if (ordner != null && !ordner.equals(Einstellungen.zielOrdner.getWert().get()))
        {
            Einstellungen.zielOrdner.getWert().set(ordner);
            //settings.saveSettings(); 
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
     * @param aufrufer Der Aufrufer, damit das Rchtige Fenster als Owner gesetzt wird.
     * @return Den ausgewählten Ordner, oder null falls abgebrochen wurde.
     */
    protected String selectFolder(Einstellungen name, Button aufrufer)
    {        
        DirectoryChooser chooser = new DirectoryChooser();
        if (FileUtil.isFolder(name.getWert().get()))
        {
            chooser.setInitialDirectory(new File(settings.getSetting(name.getWert().get())));
        }
        chooser.setTitle(name.toString());
        File ordner = chooser.showDialog((Stage)aufrufer.getScene().getWindow()); 
        if(ordner == null)
        {
            log.write("Es wurde keine Datei ausgewählt",WARNUNG);
            return null;
        }
        try {
            return ordner.toURI().toURL().toExternalForm();
        } catch (MalformedURLException ex) {
            log.write("Es gab ein Problem beim phrasen des ausgewählten Ordners: " + ordner.getAbsolutePath(),FEHLER);
            return null;
        }
    }
    
    /**
     * Dies öffnet ein Fenster bei dem die Datei ausgewählt werden kann.
     * @param name Der Name der Einstellung
     * @param aufrufer Der Aufrufer, damit das Rchtige Fenster als Owner gesetzt wird.
     * @return Den ausgewählten Ordner, oder null falls abgebrochen wurde.
     */
    protected String selectFile(Einstellungen name, Button aufrufer)
    {        

        FileChooser chooser = new FileChooser();
        if (FileUtil.isFolder(name.getWert().get()))
        {
            chooser.setInitialDirectory(new File(settings.getSetting(name.getWert().get())));
        }
        chooser.setTitle(name.toString());
        File ordner = chooser.showSaveDialog((Stage)aufrufer.getScene().getWindow()); 
        if(ordner == null)
        {
            log.write("Es wurde keine Datei ausgewählt",WARNUNG);
            return null;
        }
        try {
            return ordner.toURI().toURL().toExternalForm();
        } catch (MalformedURLException ex) {
            log.write("Es gab ein Problem beim phrasen des ausgewählten Ordners: " + ordner.getAbsolutePath(),FEHLER);
            return null;
        }
    }
}
