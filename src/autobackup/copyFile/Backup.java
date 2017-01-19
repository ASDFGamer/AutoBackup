
package autobackup.copyFile;

import hilfreich.FileUtil;
import java.io.File;

/**
 *
 * @author Christoph Wildhagen 
 */
public class Backup implements IBackup{
    //----Variablen----
    /**
     * Dies ist der Ordner der gesichert werden soll.
     */
    private String quellordner;
    
    /**
     * Dies ist der Ordner in den gespeichert werden soll.
     */
    private String zielordner;
    
    /**
     * Dies gibt an, ob der Ordner im Netzwerk liegt (für weniger lesezugriffe)
     */
    private boolean remote;
    
    /**
     * Dies ist der Pfad zu der Datei in der der Dateibaum mit letzter änderung staht (für remote)
     */
    private File dateibaumpfad;
    
    /**
     * Dies gibt an ob alte Dateien überschrieben werden sollen, oder ob eine neue Version erzeugt werden soll.
     */
    private boolean overwrite;
    
    /**
     * Dies gibt an wie viele Versionen behalten werden sollen, wenn overwrite ausgeschaltet ist.
     */
    private int versions;
    
    /**
     * Dies legt fest, ob ein vollständiges Backup gemacht werden soll, oder nur die veränderten Dateien gesichert werden sollen.
     */
    private boolean onlyChange = true;
    //----Methoden----
    //---public---
    //--Overwrite--
    @Override
    public boolean backup()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setSourceFolder(String path)
    {
        this.quellordner = path;
        return true;
    }

    @Override
    public boolean setDestinationFolder(String path)
    {
        this.zielordner = path;
        return true;
    }

    @Override
    public boolean setOverwrite(boolean overwrite)
    {
        this.overwrite = overwrite;
        return true;
    }

    @Override
    public boolean setVersions(int versions)
    {
        this.versions = versions;
        return true;
    }

    @Override
    public boolean setOnlyChange(boolean onlyChange)
    {
        this.onlyChange = onlyChange;
        return true;
    }

    @Override
    public boolean setDateibaumPfad(String pfad) {
        if (!FileUtil.isFile(pfad))
        {
            return false;
        }
        this.dateibaumpfad = new File(pfad);
        return true;
    }

    @Override
    public boolean setDateibaumPfad(File pfad) {
        this.dateibaumpfad = pfad;
        return true;
    }

}
