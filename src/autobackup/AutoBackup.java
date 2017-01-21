package autobackup;

import autobackup.Data.*;
import autobackup.copyFile.*;
import autobackup.settings.*;
import hilfreich.FileUtil;
import hilfreich.Log;
import hilfreich.LogLevel;
import java.io.FileNotFoundException;
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
    private final Log log = new Log(super.getClass().toString());
    
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
        try 
        {
            AutoBackup autoBackup = new AutoBackup();
            autoBackup.analyzeArgs(args);
            autoBackup.load();
            autoBackup.run();
            autoBackup.save();
            autoBackup.end();
        }
        catch (Exception e)
        {
            Log.Write("Es gab einen kritischen Fehler der nicht abgefangen wurde", LogLevel.FATAL_ERROR);
            e.printStackTrace();
            try
            {
                e.printStackTrace(new PrintStream(Einstellungen.logFolder));
            }
            catch (Exception ex)
            {
                Log.Write("Die Logfile wurde auch nicht gefunden :(", LogLevel.FEHLER);
            }
        }
        
    }
    
    /**
     * Mit dieser Methode werden die übergebennen Argumente analysiert und voreinstellungen festgelegt.
     * @param args Die Argumente die dem Programm beim Start übergeben wurden.
     */
    private void analyzeArgs(String[] args)
    {
        if (args.length>0)
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
            log.write("Es wird die standarmäßige Configfile genommen: ",1);
            log.write(FileUtil.getConfigFile(Const.PROGRAMM_NAME,"settings.txt"),1);
            this.configfilePath = FileUtil.getConfigFile(Const.PROGRAMM_NAME,"settings");
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
        
        if (!config.loadSettings())
        {
            log.write("Die Einstellungen konnten nicht geladen werden, es werden Standardeinstellungen benutzt.");
            stdEinstellungen();
            return false;
        }
        
        //Die einzelnen Einstellungen:
        Einstellungen.ausgangsOrdner =  config.getSetting("ausgangsOrdner");
        Einstellungen.backuptiefe =     Integer.parseInt(config.getSetting("backuptiefe"));
        //TODO Einstellungen.erlaubteTypen;
        Einstellungen.logFolder =       config.getSetting("logFolder");
        Einstellungen.maxLogs =         Integer.parseInt(config.getSetting("maxLogs"));
        //TODO Einstellungen.verboteneTypen;
        Einstellungen.writeLog =        Boolean.parseBoolean(config.getSetting("writeLog"));
        Einstellungen.zielOrdner =      config.getSetting("zielOrdner");
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
        
        //Einstellungen für das Backup festlegen
        backup.setSourceFolder(Einstellungen.ausgangsOrdner);
        backup.setDestinationFolder(Einstellungen.zielOrdner);
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
            log.write("Das Backup wurde nicht werfolgreich abgeschlossen.", 2);
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
        //Die einzelnen Einstellungen:
        Einstellungen.ausgangsOrdner =  stdEinstellungen.ausgangsOrdner;
        Einstellungen.backuptiefe =     stdEinstellungen.backuptiefe;
        Einstellungen.erlaubteTypen =   stdEinstellungen.erlaubteTypen;
        Einstellungen.logFolder =       stdEinstellungen.logFolder;
        Einstellungen.maxLogs =         stdEinstellungen.maxLogs;
        Einstellungen.verboteneTypen =  stdEinstellungen.verboteneTypen;
        Einstellungen.writeLog =        stdEinstellungen.writeLog;
        Einstellungen.zielOrdner =      stdEinstellungen.zielOrdner;
        log.write("Die Standardeinstellungen wurden geladen.");
    }


    
}
