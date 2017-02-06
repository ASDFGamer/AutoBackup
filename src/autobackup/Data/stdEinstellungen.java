
package autobackup.Data;

import autobackup.filesystem.Endung;
import hilfreich.FileUtil;

/**
 * Hier werden die Standardwerte für die Einstellungen gespeichert.
 * Diese sollten angepasst werden, auf jeden Fall die Ordnerpfade.
 * @author Christoph Wildhagen 
 */
public class stdEinstellungen {
    
    /**
     * zielOrdner gibt an in welchen Ordner das Backup kopiert werden soll.
     * Dies kann ein einfacher Ordner im Dateisystem sein oder ein externer Ordner im Netzwerk.
     */
    public final static String zielOrdner = System.getProperty("User.dir") + FileUtil.SEPERATOR + "backup";
    
    /**
     * ausgangsordner gibt an welcher Ordner gesichert werden soll. 
     * Dies sollte ein lokaler Ordner sein, kann aber auch im Netzwerk liegen.
     */
    public final static String quellOrdner = System.getProperty("User.home");
    
    /**
     * Die backuptiefe gibt an wie viele Unterordner mit gesichert werden sollen.
     * Wenn alles gesichert werden soll, dann ist die Backuptiefe -1. 
     */
    public final static int backuptiefe = -1;
    
    /**
     * Dies gibt an welche Dateitypen verboten sind. Wenn es leer sind ist alles erlaubt.
     */
    public final static Endung[] verboteneTypen = null;
    
    /**
     * Dies gibt an welche Dateitypen erlaubt sind. Wenn es leer sind ist alles erlaubt.
     * Wenn ein Typ erlaubt und verboten ist, dann ist er wieder erlaubt.
     */
    public final static Endung[] erlaubteTypen = null;
    
    /**
     * writeLog gibt an, ob einLog angelegt werden soll. Dieser entsteht an dem in {@link Einstellungen#logFolder} angegebenne Ordner. 
     */
    public final static boolean writeLog = true;
    
    /**
     * Der Ordner in dem die Logs gespeichert werden sollen.
     */
    public final static String logFolder = System.getProperty("user.dir");
    
    /**
     * Dies gibt an wie viele Log maximal abgespeichert werden können.
     */
    public final static int maxLogs = 3;
    
    /**
     * Dies gibt die Einstellungsdatei an.
     */
    public final static String configFolder = FileUtil.getConfigFolder(Const.PROGRAMM_NAME);
    
    /**
     * Dies gibt den Ordner für die Einstellungsdateien an.
     */
    public final static String configFile = FileUtil.getConfigFile(Const.PROGRAMM_NAME, "config.txt");
    
    
}
