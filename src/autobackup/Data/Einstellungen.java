
package autobackup.Data;

import autobackup.filesystem.Endung;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Hier werden alle wichtigen Einstellungen gespeichert.
 * @author Christoph Wildhagen 
 */
public class Einstellungen {
    
    private static ChangeListener<Object> einstellungenaendern = new ChangeListener<Object>() {
        @Override
        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
            Einstellungen.einstellungenGeaendert=true;
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    
    /**
     * zielOrdner gibt an in welchen Ordner das Backup kopiert werden soll.
     * Dies kann ein einfacher Ordner im Dateisystem sein oder ein externer Ordner im Netzwerk.
     */
    public static SimpleStringProperty zielOrdner = new SimpleStringProperty(stdEinstellungen.zielOrdner);
    
    /**
     * ausgangsordner gibt an welcher Ordner gesichert werden soll. 
     * Dies sollte ein lokaler Ordner sein, kann aber auch im Netzwerk liegen.
     */
    public static SimpleStringProperty ausgangsOrdner = new SimpleStringProperty(stdEinstellungen.ausgangsOrdner);
    
    /**
     * Die backuptiefe gibt an wie viele Unterordner mit gesichert werden sollen.
     * Wenn alles gesichert werden soll, dann ist die Backuptiefe -1. 
     */
    public static SimpleIntegerProperty backuptiefe = new SimpleIntegerProperty(stdEinstellungen.backuptiefe);
    
    /**
     * Dies gibt an welche Dateitypen verboten sind. Wenn es leer sind ist alles erlaubt.
     */
    public static Endung[] verboteneTypen = stdEinstellungen.verboteneTypen;
    
    /**
     * Dies gibt an welche Dateitypen erlaubt sind. Wenn es leer sind ist alles erlaubt.
     * Wenn ein Typ erlaubt und verboten ist, dann ist er wieder erlaubt.
     */
    public static Endung[] erlaubteTypen = stdEinstellungen.erlaubteTypen;
    
    /**
     * writeLog gibt an, ob einLog angelegt werden soll. Dieser entsteht an dem in {@link Einstellungen#logFolder} angegebenne Ordner. 
     */
    public static SimpleBooleanProperty writeLog = new SimpleBooleanProperty(stdEinstellungen.writeLog);
    
    /**
     * Der Ordner in dem die Logs gespeichert werden sollen.
     */
    public static SimpleStringProperty logFolder = new SimpleStringProperty(stdEinstellungen.logFolder);
    
    /**
     * Dies gibt an wie viele Log maximal abgespeichert werden können.
     */
    public static SimpleIntegerProperty maxLogs = new SimpleIntegerProperty(stdEinstellungen.maxLogs);
    
    /**
     * Dies gibt die Einstellungsdatei an.
     */
    public static SimpleStringProperty configFile = new SimpleStringProperty(stdEinstellungen.configFile);
    
    /**
     * Dies ist nur für den internen Gebrauch und zeigt ob Einstellungen geändert wurden 
     */
    public static boolean einstellungenGeaendert = false;
    
    public static void init()
    {
        Einstellungen.ausgangsOrdner.addListener(einstellungenaendern);
        Einstellungen.backuptiefe.addListener(einstellungenaendern);
        Einstellungen.configFile.addListener(einstellungenaendern);
        Einstellungen.logFolder.addListener(einstellungenaendern);
        Einstellungen.maxLogs.addListener(einstellungenaendern);
        Einstellungen.writeLog.addListener(einstellungenaendern);
        Einstellungen.zielOrdner.addListener(einstellungenaendern);
    }

}
