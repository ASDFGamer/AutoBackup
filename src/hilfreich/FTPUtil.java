
package hilfreich;

import static hilfreich.LogLevel.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Christoph Wildhagen 
 */
public class FTPUtil {
    
    /**
     * Dies überprüft ob eine Datei existiert.
     * @param path Der Pfad zu der Datei von dem aktuellen Ordner des Clients aus.
     * @param client Der Client bei dem der Pfad überprüft werden soll
     * @param stay legt fest ob der client in dem zuletzt verwendeten Verzeichnis bleiben soll oder ob er zurück zum Anfang soll.
     * @return true, falls die Datei existiert, sonst false.
     */
    public static boolean isFileFTP(Path path,FTPClient client,boolean stay)
    {
        int i = -1;
        boolean result = true;
        if (path.getNameCount()>1)
        {
            for (i = 0; i<path.getNameCount()-1;i++)
            {
                if (!isFolderFTP(path.getName(i),client,true))
                {
                    return false;
                }
            }
        }
        try
        {
            InputStream inputStream = client.retrieveFileStream(path.getName(path.getNameCount()-1).toString());
            int returnCode = client.getReplyCode();
            result = !(inputStream == null || returnCode == 550);
  
            if (!stay)
            {
                while (i<-1)
                {
                    client.changeToParentDirectory();
                    i--;
                }
            }
        }
        catch (IOException e)
        {
            result = false;
        }
        return result;
    }
    
    /**
     * Dies überprüft ob ein bestimmtes Verzeichnis existiert.
     * @param path Der Pfad zum Verzeichnis vom aktuellen Pfad des Clients aus.
     * @param client Der FTP-Client der überprüft werden soll.
     * @param stay legt fest ob der client in dem zuletzt verwendeten Verzeichnis bleiben soll oder ob er zurück zum Anfang soll.
     * @return true, falls der Ordner existiert, sonst false.
     */
    public static boolean isFolderFTP(Path path, FTPClient client,boolean stay) 
    {
        int i = -1;
        boolean result = true;
        if (path.getNameCount()>1)
        {
            for (i = 0; i<path.getNameCount()-1;i++)
            {
                if (!isFolderFTP(path.getName(i),client,true))
                {
                    return false;
                }
            }
        }
        try
        {
            client.changeWorkingDirectory(path.toString());
            int returnCode = client.getReplyCode();
            result = returnCode != 550;
            
            if (!stay)
            {
                while (i<-1)
                {
                    client.changeToParentDirectory();
                    i--;
                }
            }
        }
        catch (IOException e)
        {
            result = false;
        }
        return result;
    }
    
    public static boolean createFolderFTP(Path path,FTPClient client)
    {
        if(isFolderFTP(path,client,false))
        {
            Log.Write("Der Ordner " + path + " existiert schon auf dem Client " + client.getRemoteAddress().getHostAddress(),DEBUG);
        }
        if(path.getNameCount()>1)
        {
            if (!createFolderFTP(path.subpath(0, path.getNameCount()-2),client))
            {
                return false;
            }
        }
        try
        {
            client.makeDirectory(path.toString());
        } catch (IOException ex)
        {
            Log.Write("Das Verzeichnis " + path + " konnte nicht auf " + client.getRemoteAddress().getHostAddress() + " erstellt werden.",WARNUNG);
            return false;
        }
        return false;
    }
    
    public static boolean copyFileFTP(Path quelldatei, Path zieldatei, FTPClient client)
    {
        try
        {
            if(!isFolderFTP(Paths.get(zieldatei.getRoot().toString()+zieldatei.subpath(0, zieldatei.getNameCount()-1).toString()),client,false))
            {
                createFolderFTP(Paths.get(zieldatei.getRoot().toString()+zieldatei.subpath(0, zieldatei.getNameCount()-1).toString()),client);
            }
            client.storeFile(zieldatei.toString(), new FileInputStream(quelldatei.toFile()));
        } 
        catch (FileNotFoundException e)
        {
            Log.Write("Die Datei die kopiert werden soll konte nicht gefunden werden.",FEHLER);
            return false;
        }
        catch (IOException e)
        {
            Log.Write("Es gab ein Problem beim kopieren der Datei",FEHLER);
            return false;
        }
        return true;
    }
    
    /**
     * 
     * @param datei
     * @param client
     * @return Das Datum wann die Datei dasletzte mal verändert wurde oder null falls es ein problem gab.
     */
    public static Date getModificationDate(Path datei,FTPClient client)
    {
        String servertime;
        try
        {
           servertime =client.getModificationTime(datei.toString()); 
        }
        catch (IOException e)
        {
            Log.Write("Das letzte änderungsdatum konnte nicht ausgelesen werden.");
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            String timePart = servertime.split(" ")[1];
            Date modificationTime = dateFormat.parse(timePart);
            System.out.println("File modification time: " + modificationTime);
            return modificationTime;
        } 
        catch (ParseException ex) 
        {
            Log.Write("Das Datum konnte nicht gephrast werden.");
            return null;
        }  
    }
    
    /**
     * Dies erstellt alte versionen von einer Datei, falls möglich, wobei maximal {@link Backup#versions} Versionen erstellt werden.
     * @param pfad Der Pfad zu der Datei von der eine weitere Version angelegt werden soll
     * @param version Die Versionsnummer von der Version die jetzt angelegt werden soll.
     * @return true, falls es geklappt hat, sonst false.
     */
    public static boolean versionierungFTP(Path pfad, int version, int max_version,FTPClient client)
    {
        boolean result = true;
        if (max_version >= version)
        {
            if (version == 1)
            {
                Log.Write("Es werden die alten Version von "+pfad.toString() +" verschoben/gelöscht",DEBUG);
            }
            if (isFileFTP(pfad,client,false))
            {
                Path newpfad;
                if (version > 1)
                {
                    newpfad = Paths.get(pfad.toString().substring(0, pfad.toString().length()-1)+version);
                }
                else
                {
                    newpfad = Paths.get(pfad.toString()+".1");
                }
                result = versionierungFTP(newpfad, version+1,max_version,client);
                try
                {
                    client.rename(pfad.toString(), newpfad.toString());//Problem falls Pfad und nicht nur Dateiname?
                } 
                catch (IOException ex)
                {
                    Log.Write("Es konnte " + pfad.toString() + " nicht umbenannt werden",WARNUNG);
                    return false;
                }
            }
            return result;
        }
        else
        {
            Log.Write("Es werden keine verschiedenen Versionen gespeichert oder es ist das versionslimit erreicht.",DEBUG); 
            return true;
        }
    }
    
    
}
