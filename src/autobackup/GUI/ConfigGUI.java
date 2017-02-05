/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.GUI;

import hilfreich.Log;
import hilfreich.LogLevel;
import hilfreich.Utils;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

/**
 *
 * @author WILDHACH
 */
public class ConfigGUI extends Application{
    
    private Log log = new Log(super.getClass().getSimpleName());
    
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
            log.write("Die GUI konnte nicht geöffnet werden.",LogLevel.FATAL_ERROR);
            e.printStackTrace();
            Platform.exit();
        }    
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
