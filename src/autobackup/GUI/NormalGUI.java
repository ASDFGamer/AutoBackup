
package autobackup.GUI;

import autobackup.AutoBackup;
import hilfreich.Log;
import static hilfreich.LogLevel.*;
import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Dies startet das Normale Gui der Anwendung
 * @author Christoph Wildhagen 
 */
public class NormalGUI extends Application{
    
    /**
     * Mein Log
     */
    private Log log = new Log(super.getClass().getSimpleName());
    
    /**
     * Dies wird aufgerufen um die Anwendung zu starten.
     * @param primaryStage Die Stage.
     * @throws Exception falls ein Fehler auftritt der nicht abgefangen wird.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        NormalGUIController controller = new NormalGUIController();
        controller.setArgs(new String[0]);//Utils.toArray(getParameters().getRaw()));//TODO kann zu fehlern führen
        
        try
        {
            URL path = new File("src\\assets\\FXML\\NormalGUI.fxml").toURI().toURL();
            Parent root = FXMLLoader.load(path);
            Scene scene = new Scene(root, 600, 400); //Pixel anpassen
            primaryStage.setTitle("AutoBackup");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (Exception e)
        {
            log.write("Die GUI konnte nicht geöffnet werden.",FATAL_ERROR);
            e.printStackTrace();
            Platform.exit();
        } 
    }
    
    /**
     * Der Start ins Programm
     * @param args Dieselben wie bei {@link AutoBackup#main(java.lang.String[]) 
     */
    public static void main(String[] args) {
        launch(args);
    }

}
