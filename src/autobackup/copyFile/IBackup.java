package autobackup.copyFile;

/**
 * Dieses Interface soll benutzt werden um das Backup zu steuern
 * @author Christoph Wildhagen
 */
public interface IBackup
{
    public abstract boolean backup();
    
    public abstract boolean setSourceFolder(String path);
    
    public abstract boolean setDestinationFolder(String path);
    
    public abstract boolean setOverwrite(boolean overwrite);
    
    public abstract boolean setVersions(int versions);
    
    public abstract boolean setOnlyChange(boolean onlyChange);
}
