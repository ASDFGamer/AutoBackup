
package autobackup.copyFile;

import hilfreich.Convertable;
import hilfreich.FileUtil;
import hilfreich.Log;
import hilfreich.LogLevel;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Properties;
import static java.nio.file.StandardCopyOption.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *TODO File wenn notwendig durch Path austauschen
 * @author Christoph Wildhagen 
 */
public class Backup implements IBackup{
    //----Variablen----
    /**
     * Dies ist der Ordner der gesichert werden soll.
     */
    private Path quellordner;
    
    /**
     * Dies ist der Ordner in den gespeichert werden soll.
     */
    private Path zielordner;
    
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
    
    /**
     * Der Dateibaum wird geladen als Properties, TODO schauen ob es performanter geht.
     */
    private Properties dateibaum;
    
    /**
     * Dies sind die Dateien die geändert wurden oder neu sind
     */
    private LinkedList<Path> neueDateien;
    
    //--Hilfsvariablen--
    
    /**
     * Dies ist für die Logausgabe.
     */
    Log log = new Log(super.getClass().toString());
    
    //----Methoden----
    //---public---
    //--Overwrite--
    @Override
    public boolean backup()
    {
        if ( !checkDateibaum())
        {
            log.write("Es konnte kein Dateibaum gelesen werden, es werden alle Dateien gesichert.", LogLevel.FEHLER);
            if ( !checkBackupFiles(this.quellordner.toFile()))
            {
                log.write("Der Backupordner enthält kein Backup, entweder dies ist der erste Durchlauf, oder es gab ein großes Problem.", LogLevel.FEHLER);
                checkAllFiles(this.quellordner.toFile());
            }
            
        }
        boolean result = backupFiles();
        if ( !createDateibaum() )
            {
                log.write("Es konnte kein Dateibaum erstellt werden dies wird für weitere Backups weitere Probleme verursachen.", LogLevel.FEHLER);
            }
        return result;
        
    }

    @Override
    public boolean setSourceFolder(String path)
    {
        if (!FileUtil.isFolder(path))
        {
            return false;
        }
        this.quellordner = Paths.get(path);
        return true;
    }

    @Override
    public boolean setDestinationFolder(String path)
    {
        if (!FileUtil.isFolder(path))
        {
            return false;
        }
        this.zielordner = Paths.get(path);
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
    
    //---Private Methoden---
    /**
     * Überprüft den Dateibaum aus der Datei auf änderungen in der jetzigen Dateistruktur. 
     * Die Änderungen werden in {@link Backup#neueDateien gespeichert.
     * @return true, wenn die überprüfung geklappt hat, sonst false.
     */
    private boolean checkDateibaum()
    {
        //Laden des Dateibaums
        try 
        {
            FileInputStream in = new FileInputStream(this.dateibaumpfad);
            this.dateibaum.load(in);
            in.close();
        }
        catch (IOException e)
        {
            return false;
        }
        
        //Vergleichen der Dateien
        neueDateien = vergleicheDateien(this.quellordner.toFile());
        
        return true;
    }
    
    /**
     * Dies überprüft die Dateien im Zielordner, ob sie neuer sind und trägt neue Dateien zum sichern ein
     * @return true, falls alles geklappt hat, sonst false.
     */
    private boolean checkBackupFiles(File ordner)
    {
        if (ordner.isDirectory())
        {
            File[] dateien = ordner.listFiles();
            for (File datei : dateien)
            {
                if (datei.isDirectory())
                {
                    checkBackupFiles(datei);
                }
                else
                {
                    vergleicheDatei(datei.toPath());
                }
            }
        }
        else
        {
            vergleicheDatei(ordner.toPath());
        }
        return true;
    }
    
    /**
     * Dies vergleicht eine Datei aus dem Quellordner mit ihrem Equivalent im Zielordner und fügt sie zu den zu sichernden Dateien hinzugefügt.
     * @param datei Die Datei die überprüft werden soll.
     * @return true, falls alles geklappt hat, sonst false.
     */
    private boolean vergleicheDatei(Path datei)
    {
        Path zielpfad = this.zielordner.resolve(datei.subpath(this.quellordner.getNameCount(), datei.getNameCount())); //Hergeleitet von backupFiles()
        if (zielpfad.toFile().lastModified()<datei.toFile().lastModified())
        {
            this.neueDateien.add(datei);
        }
        return true;
    }
    
    /**
     * Dies markiert alle Dateien aus dem Quellordner zum sichern.
     * @return true, falls es geklappt hat, sonst false.
     */
    private boolean checkAllFiles(File ordner)
    {
        
        if (ordner.isDirectory())
        {
            File[] dateien = ordner.listFiles();
            for (File datei : dateien)
            {
                if (datei.isDirectory())
                {
                    checkAllFiles(datei);
                }
                else
                {
                    this.neueDateien.add(datei.toPath());
                }
            }
        }
        else
        {
            this.neueDateien.add(ordner.toPath());
        }
        
        return true;
    }
    
    /**
     * Dies erstellt den Dateibaum für den Ordner
     * @return true, falls alles geklappt hat, sonst false.
     */
    private boolean createDateibaum()
    {
        if (this.dateibaum.isEmpty())
        {
            log.write("Es exitiert noch kein Dateibaum, ein neuer wird angelegt.");
        }
        else
        {
            log.write("Es werden die Element die sich geneuert haben im Dateibaum geändert.");
            for (Path neueDatei : this.neueDateien)
            {
                if (this.dateibaum.contains(neueDatei))
                {
                    this.dateibaum.replace(neueDatei.toString(), neueDatei.toFile().lastModified());
                }
                else
                {
                    this.dateibaum.put(neueDatei.toString(), neueDatei.toFile().lastModified());
                }
            }
        }
        return true;
    }
    
    /**
     * Diese Funktion sichert die ausgewählten Dateien in den Zielordner und legt falls nötig Versionen an.
     * @return true, falls das Backup geklappt hat, sonst false (für true muss die versionierung nicht geklappt haben).
     */
    private boolean backupFiles()
    {
        boolean result = true;
        Path kurzpfad;
        Path zielpfad;
        int quellNameCount = this.quellordner.getNameCount();
        for (Path datei : this.neueDateien)
        {
            kurzpfad = datei.subpath(quellNameCount, datei.getNameCount());
            zielpfad = this.zielordner.resolve(kurzpfad);
            versionierung(zielpfad,1);
            try
            {
                Files.copy(datei, zielpfad, COPY_ATTRIBUTES);
            } catch (IOException ex)
            {
                log.write("Die Datei: " + datei.toString() + " konnte nicht kopiert werden", LogLevel.FEHLER);
                result = false;
            }
        }
        return result;
    }
    
    /**
     * Dies listet alle Ordner/Dateien in einem Ordner auf
     * @param ordner der übergeordnete Ordner
     * @return Die Dateistruktur als List
     */
    private LinkedList<String> dateistrukturEinlesen(File ordner)
    {
        LinkedList<String> dateistruktur = new LinkedList<>();
        File[] dateien = ordner.listFiles();
        for (File datei : dateien)
        {
            dateistruktur.add(datei.getAbsolutePath());
            if (datei.isDirectory())
            {
                dateistruktur.addAll(dateistrukturEinlesen(datei));
            }
        }
        return dateistruktur;
    }
    
    /**
     * Dies vergleicht die Dateien die in der Einstellungsdatei waren mit
     * @param ordner Der übergeordnete Ordner
     * @return Eine liste mit allen geänderten Dateien.
     */
    private LinkedList<Path> vergleicheDateien(File ordner)
    {
        File[] dateien = ordner.listFiles();
        LinkedList<Path> geaendert = new LinkedList<>();
        String path;
        for (File datei : dateien)
        {
            path = datei.getAbsolutePath();//Könnte Probleme wegen File/Path umwandlung geben.
            if ((!this.dateibaum.contains(path)) || !Convertable.toLong(this.dateibaum.getProperty(path)))
            {
                geaendert.add(Paths.get(datei.getAbsolutePath()));
            }
            else if (datei.lastModified()>(long)this.dateibaum.get(path))
            {
                geaendert.add(Paths.get(datei.getAbsolutePath()));
            }
            //TODO über Hash gehen, da es Probleme mit der änderungszeit gben könnte.
            if (datei.isDirectory())
            {
                geaendert.addAll(vergleicheDateien(datei));
            }
        }
        return geaendert;
    }
    
    /**
     * Dies erstellt alte versionen von einer Datei, falls möglich, wobei maximal {@link Backup#versions} Versionen erstellt werden.
     * @param pfad Der Pfad zu der Datei von der eine weitere Version angelegt werden soll
     * @param version Die Versionsnummer von der Version die jetzt angelegt werden soll.
     * @return true, falls es geklappt hat, sonst false.
     */
    private boolean versionierung(Path pfad, int version)
    {
        boolean result = true;
        if (this.overwrite && this.versions >= version)
        {
            if (version == 1)
            {
                log.write("Es werden die alten Version von "+pfad.toString() +" verschoben/gelöscht");
            }
            if (FileUtil.isFile(pfad))
            {
                Path newpfad;
                if (version > 1)
                {
                    newpfad = Paths.get(pfad.toString().substring(0, pfad.toString().length()-2)+version);
                }
                else
                {
                    newpfad = Paths.get(pfad.toString()+".1");
                }
                result = versionierung(newpfad, version+1);
                try
                {
                    Files.move(pfad, newpfad, REPLACE_EXISTING);
                } 
                catch (IOException ex)
                {
                    log.write("Es konnte " + pfad.toString() + " nicht umbenannt werden",LogLevel.WARNUNG);
                    return false;
                }
            }
            return result;
        }
        else
        {
            log.write("Es werden keine verschiedenen Versionen gespeichert oder es ist das versionslimit erreicht.",LogLevel.DEBUG); //Aussage ist sehr verweicht, aber eine bessere Aussage würde zu viel Leistung kosten.
            return true;
        }
    }

}
