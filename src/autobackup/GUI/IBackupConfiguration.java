package autobackup.GUI;

/**
 * Klassen die dies Implementieren müssen eine GUI zum ändern von EInstellungen bereitstellen.
 * @author Christoph Wildhagen
 */
public interface IBackupConfiguration
{
    /**
     * Die legt den Ordner fest in den die Dateien gesichert werden sollen.
     */
    public void setZielordner();
    
    /**
     * Dies legt den Ordner fest aus dem die Dateien kommen
     */
    public void setQuellordner();
    
}
