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
    public boolean backupFiles();
    
    public boolean setDateibaum(Properties dateibaum);
    
    public LinkedList<Path> getNeueDateien();
    
    public boolean vergleicheDatei(Path datei);
    
    public boolean checkBackupFiles();
    
    public boolean checkAllFiles();
}
