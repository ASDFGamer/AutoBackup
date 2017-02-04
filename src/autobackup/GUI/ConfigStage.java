/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;

import hilfreich.Log;
import hilfreich.LogLevel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author WILDHACH
 */
public class ConfigStage {
    
    @FXML
    private ChoiceBox backuptiefe;
    
    @FXML
    private ComboBox loganzahl;
    
    private Stage stage;
    
    private Log log = new Log(super.getClass().getSimpleName());
    
    public ConfigStage (Stage stage) throws IOException
    {
        URL path = new File("src\\assets\\FXML\\ConfigGUI.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(path);
        Scene scene = new Scene(root, 600, 400); //Pixel anpassen
        stage.setTitle("AutoBackup Config");
        stage.setScene(scene);
        
        stage.show();
        this.stage = stage;
    }
    
    public ConfigStage (Stage stage, Stage uebergeordnet) throws IOException
    {
        URL path = new File("src\\assets\\FXML\\ConfigGUI.fxml").toURI().toURL();
        Parent root = FXMLLoader.load(path);
        Scene scene = new Scene(root, 600, 400); //Pixel anpassen
        stage.setTitle("AutoBackup Config");
        stage.initOwner(uebergeordnet);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
    }
    
    public Stage getStage()
    {
        return this.stage;
    }
}
