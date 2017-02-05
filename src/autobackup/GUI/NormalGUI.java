
package autobackup.GUI;

import hilfreich.Log;
import hilfreich.LogLevel;
import hilfreich.Utils;
import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Christoph Wildhagen 
 */
public class NormalGUI extends Application{

    Log log = new Log(super.getClass().getSimpleName());
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        NormalGUIController controller = new NormalGUIController();
        controller.setArgs(Utils.toArray(getParameters().getRaw()));
        
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
            log.write("Die GUI konnte nicht geöffnet werden.",LogLevel.FATAL_ERROR);
            e.printStackTrace();
            Platform.exit();
        } 
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}
