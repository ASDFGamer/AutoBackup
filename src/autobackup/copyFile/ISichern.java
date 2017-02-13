package autobackup.copyFile;

import java.util.Properties;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 *
 * @author Christoph Wildhagen
 */
public interface ISichern
{
    /**
     * Dies sichert alle Dateien die gesichert werden müssen
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean backupFiles();
    
    /**
     * Legt den Dateibaum fest
     * @param dateibaum Der Dateibaum
     * @return true falls alles gut ging, sonst false.
     */
    public boolean setDateibaum(Properties dateibaum);
    
    /**
     * Dies gibt alle geänderten Dateien zurück.
     * @return Alle geänderten Dateien.
     */
    public LinkedList<Path> getNeueDateien();
    
    /**
     * Dies vergleicht eine Datei mit der Datei im Sicherungsordner. Das Ergebnis wird gleich in dem Objekt abgespeichert.
     * @param datei Die Datei die verglichen werden soll.
     * @return false, falls es einen Fehler gab, sonst true.
     */
    public boolean vergleicheDatei(Path datei);
    
    /**
     * Dies legt fest welche Dateien gebackupt werden sollen durch einen Vergelich der Dateien.
     * @return true, falls es keinen Fehler gab.
     */
    public boolean checkBackupFiles();
    
    /**
     * Dies markiert alle Dateien zum sichern
     * @return true, falls es keinen Fehler gab, sonst false.
     */
    public boolean checkAllFiles();
}
