
package autobackup.copyFile;

import hilfreich.Convertable;
import static hilfreich.FileUtil.*;
import hilfreich.Log;
import static hilfreich.LogLevel.*;
import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Properties;

/**
 * Dies ist die Klasse dir für das anschieben des Backups zuständig ist.
 * @author ASDFGamer 
 */
public class Backup implements IBackup{
    //----Variablen----
    /**
     * Dies ist der Ordner der gesichert werden soll.
     */
    private URL quellordner;
    
    /**
     * Dies ist der Ordner in den gespeichert werden soll.
     */
    private URL zielordner;
    
    /**
     * Dies ist der Pfad zu der Datei in der der Dateibaum mit letzter änderung staht (für remote)
     */
    private File dateibaumpfad;
    
    /**
     * Dies gibt an wie viele Versionen behalten werden sollen, wenn overwrite ausgeschaltet ist.
     */
    private int versions;
    
    /**
     * Dies legt fest, ob ein vollständiges Backup gemacht werden soll, oder nur die veränderten Dateien gesichert werden sollen.
     */
    private boolean onlyChange = true;
    
    /**
     * Dies ist das Objekt mit dem notwendigen Sicherungsverfahren
     */
    private ISichern sichern;
    
    /**
     * Dies ist zum einlesen/schreiben des Dateibaums.
     */
    private Dateibaum dateibaum;
    
    //--Hilfsvariablen--
    
    /**
     * Dies ist für die Logausgabe.
     */
    Log log = new Log(super.getClass().getSimpleName());
    
    //----Methoden----
    //---public---
    //--Overwrite--
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean backup()
    {
        if ( !einstellungenGesetzt())
        {
            log.write("Es wurden nicht alle notwendigen Einstellungen für das Backup festgelegt.", WARNUNG);
            return false;
        }     
        log.write("Es wird von " + this.quellordner.toString() + " nach " + this.zielordner.toString() + " kopiert.");
        setSichern();
        dateibaum = new Dateibaum(sichern,this.dateibaumpfad);
        if (onlyChange)
        {
            if ( !dateibaum.checkDateibaum())
            {
                log.write("Es konnte kein Dateibaum gelesen werden, es werden alle Dateien manuell überprüft.", FEHLER);
                if ( !sichern.checkBackupFiles())
                {
                    log.write("Der Backupordner enthält kein Backup, entweder dies ist der erste Durchlauf, oder es gab ein großes Problem.", FEHLER);
                    sichern.checkAllFiles();
                }  
            }
        }
        else
        {
            log.write("Es werden auf Wunsch alle Dateien gesichert.");
            sichern.checkAllFiles();
        }
        boolean result = sichern.backupFiles();
        if ( !dateibaum.createDateibaum() )
            {
                log.write("Es konnte kein Dateibaum erstellt werden dies wird für weitere Backups weitere Probleme verursachen.", FEHLER);
            }
        return result;
        
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean setSourceFolder(URL path)
    {
        this.quellordner = path;
        return true;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean setDestinationFolder(URL path)
    {
        this.zielordner=path;
        return true;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean setVersions(int versions)
    {
        this.versions = versions;
        return true;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean setOnlyChange(boolean onlyChange)
    {
        this.onlyChange = onlyChange;
        return true;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean setDateibaumPfad(String pfad) {
        if (!isFile(pfad))
        {
            if (isFolder(pfad))
            {
                log.write(pfad + " verweist auf einen Ordner.");
                return false;
            }
            else if (createFile(pfad))
            {
                log.write("Die Datei für den Dateibaum wurde erstellt.");
            }
            else
            {
                log.write(pfad + " ist keine Datei.");
                return false;
            }
        }
        this.dateibaumpfad = new File(pfad);
        return true;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean setDateibaumPfad(File pfad) {
        this.dateibaumpfad = pfad;
        return true;
    }
    
    //---Private Methoden---
    
    /**
     * Dies überprüft ob alle notwendigen Einstellungen gesetzt wurden.
     * @return true, falls alle Einstellungen gesetzt wurden, sonst false.
     */
    private boolean einstellungenGesetzt()
    {
        boolean result = true;
        if(this.zielordner==null)
        {
            log.write("Es wurde kein Zielordner angageben", FEHLER);
            result = false;
        }
        if(this.quellordner==null)
        {
            log.write("Es wurde kein Quellordner angageben", FEHLER);
            result = false;
        }
        return result;
    }
    
    /**
     * Dies wählt das notwendige Sicherungsverfaahren anhand des Protokols aus.
     * @return true, falls alles hingehauen hat, sonst false.
     */
    private boolean setSichern()
    {
        try
        {
            switch(this.zielordner.getProtocol())
            {
                case "file":    this.sichern=new Lokal(this.quellordner,this.zielordner,this.versions);
                                break;
                case "ftp":     this.sichern = new FTP(this.quellordner,this.zielordner,this.versions);
                                break;
                default:        log.write("Das Protokoll zum sichern ist unbekannt", FEHLER);
                                return false;
            }
        }
        catch(IllegalArgumentException e)
        {
            log.write(e);
            log.write("Es gab Probleme beim zuordnen des Protokolls", FEHLER);
            return false;
        }
        return true;
    }
    
    /**
     * Dies vergleicht die Dateien die in der Einstellungsdatei waren mit den Dateien in Ordner.
     * @param dateibaum Dies sind alle Dateien die im Datiebaum stehen.
     * @param ordnerpath Der übergeordnete Ordner.
     * @return Eine liste mit allen geänderten Dateien.
     */
    static public LinkedList<Path> vergleicheDateien(Properties dateibaum, Path ordnerpath)
    {
        File ordner = ordnerpath.toFile();
        File[] dateien = ordner.listFiles();
        LinkedList<Path> geaendert = new LinkedList<>();
        String path;
        for (File datei : dateien)
        {
            path = datei.getAbsolutePath();
            Log.Write(datei.getAbsolutePath() + " wird überprüft.");
            if (isFolder(path))
            {
                Log.Write(datei.getAbsolutePath() + "ist ein Ordner.");
                geaendert.addAll(vergleicheDateien(dateibaum, datei.toPath()));
            }
            else if ((!dateibaum.containsKey(path)) || !Convertable.toLong(dateibaum.getProperty(path)))
            {
                geaendert.add(Paths.get(datei.getAbsolutePath()));
                Log.Write(datei.getAbsolutePath() + "ist eine neue Datei.");
            }
            else if (datei.lastModified()>Long.valueOf((String)dateibaum.get(path)))
            {
                geaendert.add(Paths.get(datei.getAbsolutePath()));
                Log.Write(datei.getAbsolutePath() + "ist eine geänderte Datei.");
            }
            //TODO über Hash gehen, da es Probleme mit der änderungszeit gben könnte.
        }
        return geaendert;
    }
    

}
