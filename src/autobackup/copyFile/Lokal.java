
package autobackup.copyFile;

import hilfreich.Convertable;
import static hilfreich.FileUtil.*;
import hilfreich.Log;
import static hilfreich.LogLevel.*;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.*;
import java.util.LinkedList;
import java.util.Properties;
import java.net.URL;

/**
 *
 * @author Christoph Wildhagen 
 */
public class Lokal implements ISichern{
    
        //----Variablen----
    /**
     * Dies ist der Ordner der gesichert werden soll.
     */
    private URL quellordner;
    
    /**
     * Dies ist der Ordner der gesichert werden soll.
     */
    private Path quellordnerPath;
    
    /**
     * Dies ist der Ordner in den gespeichert werden soll.
     */
    private URL zielordner;
    
    /**
     * Dies ist der Ordner in den gespeichert werden soll.
     */
    private Path zielordnerPath;
    
    /**
     * Dies gibt an wie viele Versionen behalten werden sollen, wenn overwrite ausgeschaltet ist.
     */
    private int versions;
    
    /**
     * Dies sind die Dateien die geändert wurden oder neu sind
     */
    private LinkedList<Path> neueDateien = new LinkedList<>();
    
    /**
     * Der Dateibaum wird geladen als Properties, TODO schauen ob es performanter geht.
     */
    private Properties dateibaum = new Properties();
    
    /**
     * Mein Log
     */
    Log log = new Log(super.getClass().getSimpleName());
    
    CheckDateien checkdateien = new CheckDateien();
    
    public Lokal(URL quellordner,URL zielordner,int versions) throws IllegalArgumentException
    {
        if (!quellordner.getProtocol().equals("file")||!zielordner.getProtocol().equals("file"))//theorethisch nicht nötig, aber für die sicherheit sinnvoll.
        {
            throw new IllegalArgumentException("Es kann kein lokales Backup gemacht werden da der Pfad nicht auf eine Lokale Datei verweist sondern auf " + quellordner.getProtocol()); 
        }
        try
        {
            this.quellordner = quellordner;
            this.quellordnerPath =Paths.get(quellordner.toURI());
            this.zielordner = zielordner;
            this.zielordnerPath =Paths.get(zielordner.toURI());
            this.versions = versions;
        }
        catch (URISyntaxException e)
        {
            throw new IllegalArgumentException("Es kann kein lokales Backup gemacht werden da der Pfad nicht auf eine Lokale Datei verweist"); 
        }
        
    }
    
    /**
     * Diese Funktion sichert die ausgewählten Dateien in den Zielordner und legt falls nötig Versionen an.
     * @return true, falls das Backup geklappt hat, sonst false (für true muss die versionierung nicht geklappt haben).
     */
    @Override
    public boolean backupFiles()
    {
        boolean result = true;
        Path kurzpfad;
        Path zielpfad;
        int quellNameCount = quellordnerPath.getNameCount();
        for (Path datei : neueDateien)
        {
            kurzpfad = datei.subpath(quellNameCount, datei.getNameCount());
            zielpfad = zielordnerPath.resolve(kurzpfad);
            if (isFolder(datei))
            {
                if (!isFolder(zielpfad))
                {
                    createFolder(zielpfad);
                }
            }
            else if (isFile(zielpfad)) //Die Datei hat sich geändert (oder es gab noch keinen Dateibaum)
            {
                log.write("Die Datei " + zielpfad + " existiert schon, hat sich aber geändert.");
                versionierung(zielpfad,1,this.versions);
                boolean result_temp = copyFile(datei, zielpfad, new CopyOption[]{REPLACE_EXISTING, COPY_ATTRIBUTES});
                if(!result_temp)
                {
                    result = false;
                }
            }
            else //Es ist eine Datei die noch nicht kopiert wurde
            {
                log.write("Die Datei " + datei.toString() + " wird gesichert");
                boolean result_temp = copyFile(datei, zielpfad, new CopyOption[]{COPY_ATTRIBUTES});
                if(!result_temp)
                {
                    result = false;
                }
            } 
        }
        return result;
    }
    
    
    
    /**
     * Dies vergleicht eine Datei aus dem Quellordner mit ihrem Equivalent im Zielordner und fügt sie zu den zu sichernden Dateien hinzugefügt.
     * @param datei Die Datei die überprüft werden soll.
     * @return true, falls alles geklappt hat, sonst false.
     */
    @Override
    public boolean vergleicheDatei(Path datei)
    {
        if (datei == null)
        {
            log.write("Es wird mit einer leeren Datei verglichen",FEHLER);
            return false;
        }
        Path zielpfad = this.zielordnerPath.resolve(datei.subpath(this.quellordnerPath.getNameCount(), datei.getNameCount())); //Hergeleitet von backupFiles()
        if (zielpfad.toFile().lastModified()<datei.toFile().lastModified())
        {
            this.neueDateien.add(datei);
        }
        return true;
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
    
    @Override
    public boolean setDateibaum(Properties dateibaum)
    {
        this.dateibaum = dateibaum;
        this.neueDateien = Backup.vergleicheDateien(dateibaum,this.quellordnerPath); //TODO überprüfen ob sinnvoll
        return true;
    }
    
    @Override
    public LinkedList<Path> getNeueDateien()
    {
        return this.neueDateien;
    }

    @Override
    public boolean checkBackupFiles()
    {
        CheckDateien.checkBackupFiles(quellordnerPath, this);
        return true;
    }

    @Override
    public boolean checkAllFiles()
    {
        neueDateien = CheckDateien.checkAllFiles(quellordnerPath);
        return true;
    }
}
