/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;

import autobackup.Data.Einstellungen;
import autobackup.settings.ISettings;
import autobackup.settings.Settings;
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
 *
 * @author WILDHACH
 */
public class GUIController implements Initializable {
    
    @FXML
    private Button close;
    
    @FXML
    private Button quellordner;
    
    @FXML
    private Button zielordner;
    
    @FXML
    private Log log = new Log(super.getClass().getSimpleName());
    
    private static String[] args;
    
    //private ISettings settings = new Settings(Einstellungen.);
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.setStdFilePath(Einstellungen.logFolder.get());
        log.clearLog();
        log.write("GUI wird initialisiert.");
        Einstellungen.init();
    }
    
    @FXML
    private void closeAction()
    {
        log.write("Das Fenster oder die Anwendung wird regulär beendet.");
        Stage stage = (Stage)close.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    private void quellordnerAction()
    {
        ISettings settings = new Settings(Einstellungen.configFile.get());
        String ordner = selectFolder(Einstellungen.namen.quellOrdner.toString(),settings);
        if (!ordner.equals(Einstellungen.quellOrdner.get()))
        {
            Einstellungen.quellOrdner.set(ordner);
            settings.saveSettings(); 
        }
    }
    
    @FXML
    private void zielordnerAction()
    {
        ISettings settings = new Settings(Einstellungen.configFile.get());
        String ordner = selectFolder(Einstellungen.namen.zielOrdner.toString(),settings);
        if (!ordner.equals(Einstellungen.zielOrdner.get()))
        {
            Einstellungen.zielOrdner.set(ordner);
            settings.saveSettings(); 
        }
    }

    public boolean setArgs(String[] args)
    {
        this.args = args;
        return true;
    }
    
    public String[] getArgs()
    {
        return args;
    }
    
    private String selectFolder(String name, ISettings settings)
    {        
        Einstellungen.load(settings);
        DirectoryChooser chooser = new DirectoryChooser();
        if (/*settings.loadSettingsResult() &&*/ settings.settingexists(name))
        {
            chooser.setInitialDirectory(new File(settings.getSetting(name)));
        }
        chooser.setTitle(name);
        File ordner = chooser.showDialog((Stage)this.quellordner.getScene().getWindow());
        if(ordner == null)
        {
            log.write("Es wurde keine Datei ausgewählt",LogLevel.WARNUNG);
            return null;
        }
        return ordner.getAbsolutePath();
    }
}
