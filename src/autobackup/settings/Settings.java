
package autobackup.settings;

import hilfreich.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;


/**
 * Dies erzeugt einfache Configdateien mit Hilfe von Properties.load / Properties.getProperty
 * @author Christoph Wildhagen 
 */
public class Settings implements ISettings{
    
    private Properties einstellungen = new Properties();
    
    private File configfile;
    
    private Log log = new Log(super.getClass().toString());
    
    public Settings(String configfile) throws IllegalArgumentException
    {
        try
        {
            this.configfile = new File(configfile); 
        }
        catch (NullPointerException e)
        {
            throw new IllegalArgumentException("Der übergebene String ist leer.");
        }
        if (!FileUtil.isFile(this.configfile))
        {
            throw new IllegalArgumentException("Der Pfad zeigt nicht auf eine Datei.");
        }
        
        loadSettings();
    }
    
    @Override
    public boolean loadSettings()
    {
        try
        {
            FileInputStream in = new FileInputStream(configfile);
            this.einstellungen.load(in);
            in.close();
        } 
        catch (FileNotFoundException e)
        {
            log.write("Die Datei existert nicht",3);
            return false;
        } 
        catch (IOException e)
        {
            log.write("Es gab ein Problem beim einlesen der Datei.", 3);
            return false;
        }
        log.write("Die Einstellungen aus " + this.configfile.toString() + " wurden geladen.");
        return true;
    }

    @Override
    public boolean setSetting(String key, String value)
    {
        einstellungen.setProperty(key, value);
        return true;
    }

    @Override
    public boolean settingexists(String key)
    {
        return this.einstellungen.containsKey(key);
    }

    @Override
    public String getSetting(String key)
    {
        return this.einstellungen.getProperty(key);
    }

    @Override
    public boolean saveSettings()
    {
        try
        {
            FileWriter file = new FileWriter(configfile);
            BufferedWriter writer = new BufferedWriter(file);
            this.einstellungen.store(writer, "Dies ist die Standardeinstellungsdatei für AutoBackup.");
        }
        catch (IOException e)
        {
            log.write("Es gab ein Problem beim Speichern der Einstellungen", 3);
            return false;
        }
        log.write("Die Einstellungen wurden gespeichert.");
        return true;
    }

    @Override
    public boolean setSettingsPath(String path)
    {
        if (FileUtil.isFile(path))
        {
            this.configfile = new File(path);
            return true;
        }
        return false;
        
    }
    


}
