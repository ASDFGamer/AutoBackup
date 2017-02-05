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
        Log log = new Log();
        log.setStdFilePath(Einstellungen.logFolder.get());
        log.clearLog();
        log.write("GUI wird initialisiert.");
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
        DirectoryChooser chooser = new DirectoryChooser();
        if (settings.loadSettingsResult() && settings.settingexists("quellordner"))
        {
            chooser.setInitialDirectory(new File(settings.getSetting("quellordner")));
        }
        chooser.setTitle("Quellordner");
        File quellordner = chooser.showDialog((Stage)this.quellordner.getScene().getWindow());
        if(quellordner == null)
        {
            log.write("Es wurde keine Datei ausgewählt",LogLevel.WARNUNG);
        }
        else
        {
            Einstellungen.ausgangsOrdner.set(quellordner.getAbsolutePath());
            settings.saveSettings();
        }
        
    }
    
    @FXML
    private void zielordnerAction()
    {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Zielordner");
        File zielordner = chooser.showDialog((Stage)this.zielordner.getScene().getWindow());
        Einstellungen.zielOrdner.set(zielordner.getAbsolutePath());
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
}
