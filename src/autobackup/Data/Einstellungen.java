
package autobackup.Data;

import autobackup.filesystem.Endung;

/**
 * Hier werden alle wichtigen Einstellungen gespeichert.
 * @author Christoph Wildhagen 
 */
public class Einstellungen {
    
    /**
     * zielOrdner gibt an in welchen Ordner das Backup kopiert werden soll.
     * Dies kann ein einfacher Ordner im Dateisystem sein oder ein externer Ordner im Netzwerk.
     */
    public static String zielOrdner;
    
    /**
     * ausgangsordner gibt an welcher Ordner gesichert werden soll. 
     * Dies sollte ein lokaler Ordner sein, kann aber auch im Netzwerk liegen.
     */
    public static String ausgangsOrdner;
    
    /**
     * Die backuptiefe gibt an wie viele Unterordner mit gesichert werden sollen.
     * Wenn alles gesichert werden soll, dann ist die Backuptiefe -1. 
     */
    public static int backuptiefe;
    
    /**
     * Dies gibt an welche Dateitypen verboten sind. Wenn es leer sind ist alles erlaubt.
     */
    public static Endung[] verboteneTypen;
    
    /**
     * Dies gibt an welche Dateitypen erlaubt sind. Wenn es leer sind ist alles erlaubt.
     * Wenn ein Typ erlaubt und verboten ist, dann ist er wieder erlaubt.
     */
    public static Endung[] erlaubteTypen;
    
    /**
     * writeLog gibt an, ob einLog angelegt werden soll. Dieser entsteht an dem in {@link Einstellungen#logFolder} angegebenne Ordner. 
     */
    public static boolean writeLog;
    
    /**
     * Der Ordner in dem die Logs gespeichert werden sollen.
     */
    public static String logFolder;
    
    /**
     * Dies gitb an wie viele Log maximal abgespeichert werden können.
     */
    public static int maxLogs;
    
    /**
     * Dies ist nur für den internen Gebrauch und zeigt ob Einstellungen geändert wurden 
     */
    public static boolean einstellungenGeaendert = false;

}
