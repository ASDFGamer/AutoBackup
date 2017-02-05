
package autobackup.Data;

import autobackup.filesystem.Endung;
import autobackup.settings.ISettings;
import hilfreich.Convertable;
import hilfreich.Log;
import hilfreich.LogLevel;
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
    
    public static enum namen {
        backuptiefe,configFile,erlaubteTypen,logFolder,maxLogs,quellOrdner,verboteneTypen,writeLog,zielOrdner;
    }
    
    private static ChangeListener<Object> einstellungenaendern = new ChangeListener<Object>() {
        @Override
        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
            Einstellungen.einstellungenGeaendert=true;
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
    public static SimpleStringProperty quellOrdner = new SimpleStringProperty(stdEinstellungen.quellOrdner);
    
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
        Einstellungen.backuptiefe.addListener(einstellungenaendern);
        Einstellungen.configFile.addListener(einstellungenaendern);
        Einstellungen.logFolder.addListener(einstellungenaendern);
        Einstellungen.maxLogs.addListener(einstellungenaendern);
        Einstellungen.quellOrdner.addListener(einstellungenaendern);
        Einstellungen.writeLog.addListener(einstellungenaendern);
        Einstellungen.zielOrdner.addListener(einstellungenaendern);
    }
    
    public static boolean load(ISettings config)
    {
        Log log = new Log(Class.class.getSimpleName());
        if (!config.loadSettingsResult())
        {
            log.write("Die Einstellungen konnten nicht geladen werden, es werden Standardeinstellungen benutzt.",LogLevel.WARNUNG);
            return false;
        }
        
        //Die einzelnen Einstellungen: TODO Eintellungen werden nicht richtig geladen, wird oft zu null
        if (config.settingexists(Einstellungen.namen.quellOrdner.toString()) && config.getSetting(Einstellungen.namen.quellOrdner.toString()) != null)
        {
            Einstellungen.quellOrdner.set(config.getSetting(Einstellungen.namen.quellOrdner.toString()));
        }
        
        if (config.settingexists(Einstellungen.namen.backuptiefe.toString()) && config.getSetting(Einstellungen.namen.backuptiefe.toString()) != null)
        {
            if (Convertable.toInt(config.getSetting(Einstellungen.namen.backuptiefe.toString())))
            {
                Einstellungen.backuptiefe.set(Integer.parseInt(config.getSetting(Einstellungen.namen.backuptiefe.toString())));
            }
        }
        
        //TODO Einstellungen.erlaubteTypen;
        
        if (config.settingexists(Einstellungen.namen.logFolder.toString()) && config.getSetting(Einstellungen.namen.logFolder.toString()) != null)
        {
            Einstellungen.logFolder.set(config.getSetting(Einstellungen.namen.logFolder.toString()));
        }
        
        if (config.settingexists(Einstellungen.namen.maxLogs.toString()) && config.getSetting(Einstellungen.namen.maxLogs.toString()) != null)
        {
            if (Convertable.toInt(config.getSetting(Einstellungen.namen.maxLogs.toString())))
            {
                Einstellungen.maxLogs.set(Integer.parseInt(config.getSetting(Einstellungen.namen.maxLogs.toString())));
            }
        }
        
        //TODO Einstellungen.verboteneTypen;
        
        if (config.settingexists(Einstellungen.namen.writeLog.toString()) && config.getSetting(Einstellungen.namen.writeLog.toString()) != null)
        {
            Einstellungen.writeLog.set(Boolean.parseBoolean(config.getSetting(Einstellungen.namen.writeLog.toString())));//TODO sicherer vor Falscheingaben machen mit abfangen von x=/="true" x->false
        }
        
        if (config.settingexists(Einstellungen.namen.zielOrdner.toString()) && config.getSetting(Einstellungen.namen.zielOrdner.toString()) != null)
        {
            Einstellungen.zielOrdner.set(config.getSetting(Einstellungen.namen.zielOrdner.toString()));
        }
        
        log.write("Die Einstellungen wurden geladen.");
        //TODO überprüfen ob die Einstellungen hinkommen.
        return true;
    }

}
