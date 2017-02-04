/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;

import autobackup.Data.Einstellungen;
import hilfreich.Log;
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Quellordner");
        File quellordner = chooser.showDialog((Stage)this.quellordner.getScene().getWindow());
        Einstellungen.ausgangsOrdner.set(quellordner.getAbsolutePath());
    }
    
    @FXML
    private void zielordnerAction()
    {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Zielordner");
        File zielordner = chooser.showDialog((Stage)this.zielordner.getScene().getWindow());
        Einstellungen.zielOrdner.set(zielordner.getAbsolutePath());
    }

    
}
