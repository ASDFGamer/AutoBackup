package autobackup.copyFile;

import java.io.File;

/**
 * Dieses Interface soll benutzt werden um das Backup zu steuern
 * @author Christoph Wildhagen
 */
public interface IBackup
{
    /**
     * Mit dieser Funktion wird das Backup gestartet mit den jetzigen Einstellungen.
     * @return true, falls das Backup hingehauen hat, sonst false.
     */
    public abstract boolean backup();
    
    /**
     * Hiermit wird der Quellordner festgelegt/geändert.
     * @param path Der Pfad zum Quellordner.
     * @return true, falls alles geklappt hat, sonst false.
     */
    public abstract boolean setSourceFolder(String path);
    
    /**
     * Hiermit wird der Zielordner festgelegt/geändert.
     * @param path Der Pfad zum Zielordner.
     * @return true, falls alles geklappt hat, sonst false.
     */
    public abstract boolean setDestinationFolder(String path);
    
    /**
     * Hiermit wird festgelegt ob die Dateien die gesichert werden die alten Versionen überschreiben oder als neue Version angelegt werden.
     * @param overwrite true, falls es keine Versionen geben soll.
     * @return true, falls alles geklappt hat, sonst false
     */
    public abstract boolean setOverwrite(boolean overwrite);
    
    /**
     * Hiermit wird die maximale Anzahl der Versionen festgelegt.
     * (Falls overwrite ausgeschaltet ist)
     * @param versions Die Anzahl der Versionen
     * @return true, falls alles geklappt hat, sonst false
     */
    public abstract boolean setVersions(int versions);
    
    /**
     * Hiermit wird festgelegt, dass nicht immer alles gesichert wird, sondern nur die geänderten Dateien.
     * @param onlyChange true, wenn nur die geänderten Dateien gesichert werden sollen, sonst false.
     * @return true, falls alles geklappt hat, sonst false.
     */
    public abstract boolean setOnlyChange(boolean onlyChange);
    
    /**
     * Dies legt den Pfad fest in dem der Dateibaum gespeichert wird. Standardmäßig wird das Logverzeichnis verwendet.
     * @param pfad Der Pfad zu der Dateibaumdatei
     * @return true, falls alles gut gelaufen ist, sonst fasle.
     */
    public abstract boolean setDateibaumPfad(String pfad);
    
    /**
     * Dies legt den Pfad fest in dem der Dateibaum gespeichert wird. Standardmäßig wird das Logverzeichnis verwendet.
     * @param pfad Der Pfad zu der Dateibaumdatei
     * @return true, falls alles gut gelaufen ist, sonst fasle.
     */
    public abstract boolean setDateibaumPfad(File pfad);
}
