
package autobackup.GUI;

import autobackup.AutoBackup;
import hilfreich.Log;
import static hilfreich.LogLevel.*;
import hilfreich.Utils;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Dies startet das Normale Gui der Anwendung
 * @author ASDFGamer
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
        Utils.toArray(getParameters().getRaw());
        log.write("sdf");
        controller.setArgs(Utils.toArray(getParameters().getRaw()));
        
        try
        {
            URL path = ConfigStage.class.getClassLoader().getResource("assets/FXML/NormalGUI.fxml");
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
     * @param args Dieselben wie bei {@link AutoBackup#main(java.lang.String[])} 
     */
    public static void main(String[] args) {
        launch(args);
    }

}
