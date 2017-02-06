/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;

import hilfreich.Log;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author WILDHACH
 */
public class ConfigStage {
    
    /**
     * Dies ist die ConfigStage
     */
    private Stage stage;
    
    /**
     * Mein Log
     */
    private Log log = new Log(super.getClass().getSimpleName());
    
    /**
     * Erstellt eine ConfigStage in der der gegebenen Stage.
     * @param stage Die Stage die zu einer ConfigStage werden soll.
     * @throws IOException falls auf die fxml nicht zugegriffen werden kann.
     */
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
    
    /**
     * Erstellt eine ConfigStage in der der gegebenen Stage.
     * @param stage Die Stage die zu einer ConfigStage werden soll.
     * @param uebergeordnet Die Stage die dieser übergeordnet ist und somit zum Owner wird und nicht zu benutzten ist solange dieses Fenster offen ist.
     * @throws IOException falls auf die fxml nicht zugegriffen werden kann.
     */
    public ConfigStage (Stage stage, Stage uebergeordnet) throws IOException
    {
        URL path = new File("src\\assets\\FXML\\ConfigGUI.fxml").toURI().toURL();//TODO ändern für .Jar
        Parent root = FXMLLoader.load(path);
        Scene scene = new Scene(root, 600, 400); //Pixel anpassen
        stage.setTitle("AutoBackup Config");
        stage.initOwner(uebergeordnet);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
        this.stage = stage;
    }
    
    /**
     * Dies gitb die Stage zurück
     * @return Die Stage
     */
    public Stage getStage()
    {
        return this.stage;
    }
}
