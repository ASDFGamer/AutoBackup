package autobackup;

import autobackup.Data.*;
import autobackup.copyFile.*;
import autobackup.settings.*;
import static hilfreich.FileUtil.*;
import hilfreich.Log;
import static hilfreich.LogLevel.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Dieses Programm soll als automatisches Backup Programm benutzt werden können.
 * Hierbei soll so viel wie möglich konfiguierbar sein und trotzdem einfach zu benutzten.
 * Es ist automatisch, da wenn es gestartet wird sofort das Backup startet und das Programm geschlossen wird sobald das Backup fertig ist.
 * @author Christoph Wildhagen
 */
public class AutoBackup
{
    /**
     * Dies ist der Pfad zu der Einstellungsdatei.
     */
    private String configfilePath = null;
    
    /**
     * Dies ist der Log der verwendet wird.
     */
    private final Log log = new Log(super.getClass().getSimpleName());
    
    /**
     * Dieses Objekt ist für das Laden und Speichern der EInstellungen zuständig.
     */
    private ISettings config;
    
    /**
     * @param args the command line arguments
     *  -conf:pfad\zu\einstellungen gibt den Pfad zu den Einstellungen an.
     *  -quiet Schaltet die Konsolenausgabe aus.
     */
    public static void main(String[] args)
    {
        Log log = new Log();
        log.setStdFilePath(Einstellungen.logFolder.get());
        log.clearLog();
        AutoBackup backup = new AutoBackup();
        backup.backup(args);
        
    }
    
    /**
     * Mit dieser Methode werden die übergebennen Argumente analysiert und voreinstellungen festgelegt.
     * @param args Die Argumente die dem Programm beim Start übergeben wurden.
     */
    private void analyzeArgs(String[] args)
    {
        if (args != null && args.length>0)
        {
            for (String arg : args)
            {
                //Check for Arguments
                if (arg.contains("-quiet"))
                {
                    log.setStdConsole(false);
                }
                
                if (arg.contains("-conf:") && arg.length()>5)
                {
                    String pfad = arg.substring(6);
                    if(isFile(pfad))
                    {
                        this.configfilePath = pfad;
                    }
                    else
                    {
                        log.write("Die angegebene Configfile kann nicht gefunden werden",WARNUNG);
                    }
                }
                
                //Mehr Argumente
            }
        }
        if (this.configfilePath==null)
        {
            String configFile = Einstellungen.configFile.get();
            log.write("Es wird die standarmäßige Configfile genommen: ",WARNUNG);
            log.write(configFile,INFO);
            this.configfilePath = configFile;
        }
        
    }
    /**
     * Dies lädt die Einstellungen ins Programm
     */
    private boolean load()
    {
        log.write("Die Einstellungen werden geladen.");
        try
        {
            config = new Settings(this.configfilePath);
        }
        catch(IllegalArgumentException e)
        {
            log.write("Es gab ein Problem beim öffnen der Einstellungsdatei, es werden Stdandardeinstellungen benutzt.",WARNUNG);
            return false;
        }
        
        return Einstellungen.load(config);
    }
    
    /**
     * Dies ist die Methode die das Backup wirklich ausführt.
     */
    private void run()
    {
        log.write("Das Backup wird gestartet");
        Backup backup = new Backup();
        
        //Einstellungen für das Backup festlegenEinstellungen.ausgangsOrdner
        try
        {
            backup.setSourceFolder(new URL(Einstellungen.quellOrdner.get()));
            backup.setDestinationFolder(new URL(Einstellungen.zielOrdner.get()));
        }
        catch (MalformedURLException e)
        {
            log.write("Die Quell- und Zielordner wurden im falschen Format angegeben.",FATAL_ERROR);
            return;
        }

        backup.setOnlyChange(Einstellungen.onlyChange.get());
        backup.setVersions(Einstellungen.versionen.get());
        backup.setDateibaumPfad(Einstellungen.dateibaumPfad.get());
        //Backup starten
        if (backup.backup())
        {
            log.write("Das Backup wurde erfolgreich abgeschlossen.");
        }
        else
        {
            log.write("Das Backup wurde nicht erfolgreich abgeschlossen.", FATAL_ERROR);
        }
    }
    
    /**
     * Hiermit werden die Einstellungen gespeichert, falls diese sich geändert haben
     */
    private void save()
    {
        if (Einstellungen.einstellungenGeaendert)
        {
            log.write("Die Einstellungen  haben sich geändert und werden deshalb gespeichert.");
            if (config.saveSettings())
            {
                log.write("Die Einstellungen wurden gespeichert.");
            }
            else
            {
                log.write("Es gab ein Problem beim speichern der Eintellungen.");
            }
        }
    }
    
    /**
     * Hiermit wird der Backupablauf in Gang gebracht.
     * @param args Die Args von Main.
     * @return true falls alles hingehauen hat.
     */
    public boolean backup(String[] args)
    {
        
        try 
        {
            this.analyzeArgs(args);
            this.load();
            this.run();
            this.save();
        }
        catch (Exception e)
        {
            log.write(e);
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    /**
     * Hiermit wird der Backupablauf in Gang gebracht.
     * @return true falls alles hingehauen hat.
     */
    public boolean backup()
    {
        String[] args = null;
        return backup(args);
    }
}
