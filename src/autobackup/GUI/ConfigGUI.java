/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;

import hilfreich.Log;
import static hilfreich.LogLevel.*;
import hilfreich.Utils;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 * Dies öffnet die Configoberfläche für Autobackup.
 * @author WILDHACH
 */
public class ConfigGUI extends Application{
    
    /**
     * Mein Log
     */
    private Log log = new Log(super.getClass().getSimpleName());
    
    /**
     * Dies ist der Start in die Anwendung
     * @param primaryStage Die Stage die am Anfang erstellt wurde.
     * @throws Exception falls ein Fehler auftritt.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        NormalGUIController controller = new NormalGUIController();
        controller.setArgs(Utils.toArray(getParameters().getRaw()));
        try
        {
            ConfigStage confStage = new ConfigStage(primaryStage);
        }
        catch(IOException e)
        {
            log.write("Die GUI konnte nicht geöffnet werden.",FATAL_ERROR);
            e.printStackTrace();
            Platform.exit();
        }    
    }
    
    /**
     * Start ins Programm.
     * @param args Hier können keine Argumente übergeben werden.
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
