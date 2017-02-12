
package hilfreich;

import static hilfreich.LogLevel.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

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
        String dirnow = "";
        try
        {
            dirnow = client.printWorkingDirectory();
        }
        catch (IOException e)
        {
            Log.Write("Das aktuelle Verzeichnis konnte nicht ausgelesen werden.");
        }
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
                changeToDirROOT(dirnow, client);
            }
        }
        catch (IOException e)
        {
            result = false;
        }
        return result;
    }
    
    public static boolean isFolderFTP(String path, FTPClient client,boolean stay)
    {
        return isFolderFTP(Paths.get(path),client,stay);
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
        String dirnow = "";
        try
        {
            dirnow = client.printWorkingDirectory();
        }
        catch (IOException e)
        {
            Log.Write("Das aktuelle Verzeichnis konnte nicht ausgelesen werden.");
        }
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
                changeToDirROOT(dirnow, client);
            }
        }
        catch (IOException e)
        {
            result = false;
        }
        return result;
    }
    
    public static boolean createFolderFTP(Path path,FTPClient client,boolean ersteraufruf)
    {
        String dirnow = "";
        try
        {
            dirnow = client.printWorkingDirectory();
        }
        catch (IOException e)
        {
            Log.Write("Das aktuelle Verzeichnis konnte nicht ausgelesen werden.");
        }
        
        if(isFolderFTP(path,client,false))
        {
            Log.Write("Der Ordner " + path + " existiert schon auf dem Client " + client.getRemoteAddress().getHostAddress());
        }
        if(path.getNameCount()>1)
        {
            Log.Write(path.toString() + " zu " + path.subpath(0, path.getNameCount()-1).toString());
            if (!createFolderFTP(path.subpath(0, path.getNameCount()-1),client,false))
            {
                return false;
            }
        }
        try
        {
            client.makeDirectory(path.toString());
            if (ersteraufruf)
            {
                changeToDirROOT(dirnow, client);
                Log.Write("Working Directory: " + client.printWorkingDirectory() + "create Dir");
            }
            
        } catch (IOException ex)
        {
            Log.Write("Das Verzeichnis " + path + " konnte nicht auf " + client.getRemoteAddress().getHostAddress() + " erstellt werden.",WARNUNG);
            return false;
        }
        
        return true;
    }
    
    public static boolean copyFileFTP(Path quelldatei, Path zieldatei, FTPClient client)
    {
        boolean result = true;
        String dirnow = "";
        try
        {
            dirnow = client.printWorkingDirectory();
        }
        catch (IOException e)
        {
            Log.Write("Das aktuelle Verzeichnis konnte nicht ausgelesen werden.");
        }
        try
        {
            if(zieldatei.getNameCount()>1&&!isFolderFTP(zieldatei.subpath(0, zieldatei.getNameCount()),client,false))
            {
                Log.Write("Create Folder: " + zieldatei.subpath(0, zieldatei.getNameCount()-1));
                createFolderFTP(zieldatei.subpath(0, zieldatei.getNameCount()-1),client,true);
                client.changeWorkingDirectory(zieldatei.subpath(0, zieldatei.getNameCount()).toString());
            }
            FileInputStream fis = new FileInputStream(quelldatei.toFile());
            client.storeFile(zieldatei.toString(), fis );
            fis.close();
            int reply = client.getReplyCode(); //geht nicht in eine Zeile da es sich dann aufhängt
            if (!FTPReply.isPositiveCompletion(reply))
            {
                Log.Write(client.getReplyString());
                result =  false;
            }
            changeToDirROOT(dirnow, client);
            Log.Write("Working Directory: " + client.printWorkingDirectory() + "copyFile");
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
        return result;
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
    
    public static boolean changeToDir(String directory,FTPClient client) //noch nicht getestet
    {
        
        boolean noError = true;
        boolean result = false;
        int reply;
        String tempdir = "";
        try
        {
            String dirnow = client.printWorkingDirectory();
            while (noError)
            {
                client.changeToParentDirectory();
                reply = client.getReplyCode();
                noError = FTPReply.isPositiveCompletion(reply) && !tempdir.equals(client.printWorkingDirectory());
                if (isFolderFTP(directory,client,false))
                {
                    client.changeWorkingDirectory(directory.toString());
                    result = true;
                }
                tempdir = client.printWorkingDirectory();
                
            }
            if(!result)
            {
                client.changeWorkingDirectory(dirnow);
                return false;
            }
            else
            {
                return true;
            }
        }
        catch (IOException e)
        {
            return false;
        }  
    }
    
    private static boolean changeToDirROOT(String directory,FTPClient client) //public machen wegen performance? (keine lange rekursion über isFolderFTP
    {
        
        boolean noError = true;
        boolean result = false;
        int reply;
        String tempdir = "";
        try
        {
            String dirnow = client.printWorkingDirectory();
            while (noError)
            {
                client.changeToParentDirectory();
                
                reply = client.getReplyCode();
                noError = FTPReply.isPositiveCompletion(reply) && !tempdir.equals(client.printWorkingDirectory());
                tempdir = client.printWorkingDirectory();
            }
            client.changeWorkingDirectory(directory.toString());
            if (directory.equals(client.printWorkingDirectory()))
            {
                result = true;
            }
            if(!result)
            {
                client.changeWorkingDirectory(dirnow);
                return false;
            }
            else
            {
                return true;
            }
        }
        catch (IOException e)
        {
            return false;
        }  
    }
    
    
}
