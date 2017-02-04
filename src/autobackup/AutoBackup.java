package autobackup;

import autobackup.Data.*;
import autobackup.copyFile.*;
import autobackup.settings.*;
import hilfreich.Convertable;
import hilfreich.FileUtil;
import hilfreich.Log;
import hilfreich.LogLevel;
import java.io.PrintStream;

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
    ISettings config;
    
    /**
     * @param args the command line arguments
     *  -conf:pfad\zu\einstellungen gibt den Pfad zu den Einstellungen an.
     *  -quiet Schaltet die Konsolenausgabe aus.
     */
    public static void main(String[] args)
    {
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
                    if(FileUtil.isFile(pfad))
                    {
                        this.configfilePath = pfad;
                    }
                    else
                    {
                        log.write("Die angegebene Configfile kann nicht gefunden werden",1);
                    }
                }
                
                //More Arguments
            }
        }
        if (this.configfilePath==null)
        {
            String configFile = FileUtil.getConfigFile(Const.PROGRAMM_NAME,"settings.txt");
            log.write("Es wird die standarmäßige Configfile genommen: ",LogLevel.WARNUNG);
            log.write(configFile,1);
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
            log.write("Es gab ein Problem beim öffnen der Einstellungsdatei, es werden Stdandardeinstellungen benutzt.");
            stdEinstellungen();
            return false;
        }
        
        if (!config.loadSettingsResult())
        {
            log.write("Die Einstellungen konnten nicht geladen werden, es werden Standardeinstellungen benutzt.");
            stdEinstellungen();
            return false;
        }
        
        //Die einzelnen Einstellungen: TODO Eintellungen werden nicht richtig geladen, wird oft zu null
        if (config.settingexists("ausgangsOrdner") && config.getSetting("ausgangsOrdner") != null)
        {
            Einstellungen.ausgangsOrdner.set(config.getSetting("ausgangsOrdner"));
        }
        if (config.settingexists("backuptiefe") && config.getSetting("backuptiefe") != null)
        {
            if (Convertable.toInt(config.getSetting("backuptiefe")))
            {
                Einstellungen.backuptiefe.set(Integer.parseInt(config.getSetting("backuptiefe")));
            }
        }
        //TODO Einstellungen.erlaubteTypen;
        if (config.settingexists("logFolder") && config.getSetting("logFolder") != null)
        {
            Einstellungen.logFolder.set(config.getSetting("logFolder"));
        }
        if (config.settingexists("maxLogs") && config.getSetting("maxLogs") != null)
        {
            if (Convertable.toInt(config.getSetting("maxLogs")))
            {
                Einstellungen.maxLogs.set(Integer.parseInt(config.getSetting("maxLogs")));
            }
        }
        //TODO Einstellungen.verboteneTypen;
        if (config.settingexists("writeLog") && config.getSetting("writeLog") != null)
        {
            Einstellungen.writeLog.set(Boolean.parseBoolean(config.getSetting("writeLog")));//TODO sicherer vor Falscheingaben machen mit abfangen von x=/="true" x->false
        }
        if (config.settingexists("zielOrdner") && config.getSetting("zielOrdner") != null)
        {
            Einstellungen.zielOrdner.set(config.getSetting("zielOrdner"));
        }
        log.write("Die Einstellungen wurden geladen.");
        //TODO überprüfen ob die Einstellungen hinkommen.
        return true;
    }
    
    /**
     * Dies ist die Methode die das Backup wirklich ausführt.
     */
    private void run()
    {
        log.write("Das Backup wird gestartet");
        IBackup backup = new Backup();
        
        //Einstellungen für das Backup festlegenEinstellungen.ausgangsOrdner
        backup.setSourceFolder(Einstellungen.ausgangsOrdner.get());
        backup.setDestinationFolder(Einstellungen.zielOrdner.get());
        //TODO als Einstellung hinzufügen
        backup.setOnlyChange(true);
        backup.setOverwrite(true);
        backup.setVersions(0);
        
        //Backup starten
        if (backup.backup())
        {
            log.write("Das Backup wurde erfolgreich abgeschlossen.");
        }
        else
        {
            log.write("Das Backup wurde nicht erfolgreich abgeschlossen.", LogLevel.FATAL_ERROR);
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

    private void end()
    {
        //TODO was sollte hier hin?
    }
    
    /**
     * Hiermit werden die Standardeinstellungen wie sie in {@link stdEinstellungen} festgelegt wurden geladen.
     */
    private void stdEinstellungen()
    {
        log.write("Es werden die Standardeinstellungen benutzt. Bitte überprüfen ob diese verwendet werden sollen.",1);
    }

    public boolean backup(String[] args)
    {
        log.clearLog();
        try 
        {
            Einstellungen.init();
            this.analyzeArgs(args);
            this.load();
            this.run();
            this.save();
            this.end();
        }
        catch (Exception e)
        {
            log.write("Es gab einen kritischen Fehler der nicht abgefangen wurde", LogLevel.FATAL_ERROR);
            e.printStackTrace();
            try
            {
                e.printStackTrace(new PrintStream(Einstellungen.logFolder.get()));
            }
            catch (Exception ex)
            {
                log.write("Die Logfile wurde auch nicht gefunden :(", LogLevel.FEHLER);
            }
            return false;
        }
        return true;
    }
    
    public boolean backup()
    {
        String[] args = null;
        return backup(args);
    }
}
