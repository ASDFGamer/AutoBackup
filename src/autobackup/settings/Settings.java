
package autobackup.settings;

import autobackup.Data.Const;
import autobackup.Data.Einstellungen;
import static hilfreich.FileUtil.*;
import hilfreich.Log;
import static hilfreich.LogLevel.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;


/**
 * Dies erzeugt einfache Configdateien mit Hilfe von Properties.load / Properties.getProperty
 * TODO Netzwerkfähigkeiten hinzufügen, Verschachtelte Einstellungsdateien -> am besten alles neu schreiben und weiter Modularisieren.
 * @author ASDFGamer
 */
public class Settings implements ISettings{
    
    /**
     * Dies sind die einzelnen Einstellungen die aus der Datei geladen wurden.
     */
    private Properties einstellungen = new Properties();
    
    /**
     * Dies ist der Log der verwendet wird.
     */
    private Log log = new Log(super.getClass().getSimpleName());
    
    /**
     * Dies gibt den Schlüssel an mit dem gezeigt wird, das die geladene Datei von einer anderen Datei abhänig ist/geerbt hat.
     * Der Dateiname der anderen Datei muss in diesesen Schlüssel geschrieben werden.
     */
    private final static String einstellungen_super = "EinstellungenPfad";
    
    /**
     * Dies gitb die maximale Anzahl der übergeordneten Configfiles an.
     */
    private int maxConfigFiles = 64;
    
    /**
     * Hier werden alle Einstellungsdateien die benutzt wurden gespeichert, damit es im zusammenhang mit {@link Settings#einstellungen_super} nicht zu endlosschleifen durch kreisverweise kommt.
     * Desweiterem ist dies der verweis auf die gerade aktuelle Configfile.
     */
    private File[] configfiles = new File[maxConfigFiles];
    
    /**
     * Dies gibt die gerade aktive Configfile an.
     */
    private int activeconfigfile = 0;
    
    /**
     * Dies gibt an ob die Einstellungen schon geladen werden konnten.
     */
    private Boolean loadSettingsResult = null;
    
    /**
     * Hiermit können Einstellungen aus einer Datei geladen werden
     * @param configfile Die Datei aus der die Einstellungen geldaden werden sollen.
     * @throws IllegalArgumentException Falls die Datei nicht existert.
     */
    public Settings(String configfile) throws IllegalArgumentException
    {
        try
        {
            this.configfiles[this.activeconfigfile] = new File(configfile); 
        }
        catch (NullPointerException e)
        {
            throw new IllegalArgumentException("Der übergebene String ist leer.");
        }
        log.write(this.configfiles[this.activeconfigfile].toString());
        if (!isFile(this.configfiles[this.activeconfigfile]))
        {
            throw new IllegalArgumentException("Der Pfad zeigt nicht auf eine Datei.");
        }
        
        loadSettingsResult = loadSettings();
    }
    
    /**
     * Dies lädt die Einstellungen.
     * @return true, falls es geklappt hat, sonst false.
     */
    private boolean loadSettings()
    {
        String supersettings = null;
        try
        {
            FileInputStream in = new FileInputStream(configfiles[this.activeconfigfile]);
            this.einstellungen.load(in);
            /*if (this.einstellungen.containsKey(einstellungen_super) && this.einstellungen.getProperty(einstellungen_super) != null)
            {
                supersettings = this.einstellungen.getProperty(einstellungen_super);
            }*/
            in.close();
        } 
        catch (FileNotFoundException e)
        {
            log.write("Die Datei existert nicht",FEHLER);
            return false;
        } 
        catch (IOException e)
        {
            log.write("Es gab ein Problem beim einlesen der Datei.", FEHLER);
            return false;
        }
        /*if (supersettings != null)
        {
            if ( ++this.activeconfigfile >= this.maxConfigFiles )
            {
                log.write("Es wurde die maximalanzahl an übergeordneten Configfiles erreicht.", WARNUNG);
            }
            else if (isFile(supersettings))
            {
                log.write("Der Pfad für die übergeordnete Configfile verweist nicht auf eine Datei.",WARNUNG);
            }
            else if (contains(this.configfiles,supersettings))
            {
                log.write("Der angegebene Pfad würde zu einer schon gelesenen Datei führen und somit zu einem Loop", WARNUNG);
            }
            else
            {
                this.configfiles[this.activeconfigfile]=new File(supersettings);
                log.write("Die Einstellungen aus der übergeordneten Datei " + this.configfiles[this.activeconfigfile] + " werden geladen");
                loadSettings();
            }
            
        }*/
        log.write("Die Einstellungen aus " + this.configfiles[this.activeconfigfile].toString() + " wurden geladen.");
        return true;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean setSetting(String key, String value)
    {
        einstellungen.setProperty(key, value);
        return true;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean settingexists(String key)
    {
        if (this.einstellungen.containsKey(key))
        {
            log.write("Die Einstellung " + key + " existiert.");
            return true;
        }
        else
        {
            log.write("Die Einstellung " + key + " existiert nicht.");
            return false;
        }
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public String getSetting(String key)
    {
        return this.einstellungen.getProperty(key);
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean saveSettings()
    {
        if (Einstellungen.backuptiefe.getWert().getEinstellunggeaendert())
        {
            for(Einstellungen einstellung : Einstellungen.values())
            {
                einstellungen.put(einstellung.toString(), String.valueOf(einstellung.getWert().get()));
            }
            
            try
            {
                FileWriter file = new FileWriter(configfiles[this.activeconfigfile]);
                BufferedWriter writer = new BufferedWriter(file);
                this.einstellungen.store(writer, "Dies ist die Standardeinstellungsdatei für " + Const.PROGRAMM_NAME + " version " + Const.VERSION+ ".");
            }
            catch (IOException e)
            {
                log.write("Es gab ein Problem beim Speichern der Einstellungen", FEHLER);
                return false;
            }
            log.write("Die Einstellungen wurden gespeichert.");
        }
        else
        {
            log.write("Es wurden keine Einstellungen geändert.");
        }
        return true; 
    }
   
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean setSettingsPath(String path)
    {
        if (isFile(path))
        {
            this.configfiles[this.activeconfigfile] = new File(path);
            return true;
        }
        return false;
        
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean loadSettingsResult()
    {
        if (this.loadSettingsResult == null)
        {
            this.loadSettingsResult=loadSettings();
        }
        return this.loadSettingsResult;
    }



}
