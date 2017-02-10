
package hilfreich;

import static hilfreich.LogLevel.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Christoph Wildhagen 
 */
public class FTPUtil {
    
    /**
     * Dies überprüft ob im aktuellen Verzeichnis eine Datei existiert.
     * @param path
     * @param client
     * @return 
     */
    public static boolean isFile(Path path,FTPClient client)
    {
        try
        {
            InputStream inputStream = client.retrieveFileStream(path.toString());
            int returnCode = client.getReplyCode();
            return !(inputStream == null || returnCode == 550);
        }
        catch (IOException e)
        {
            return false;
        }
    }
    
    public static boolean isFolder(Path path, FTPClient client) 
    {
        try
        {
            client.changeWorkingDirectory(path.toString());
            int returnCode = client.getReplyCode();
            return returnCode != 550;
        }
        catch (IOException e)
        {
            return false;
        }
    }
    
    public static boolean copyFileFTP(Path quelldatei, Path zieldatei, CopyOption[] flag,FTPClient client)
    {
        
        /*try
        {
            if (!FileUtil.isFolder(Paths.get(zieldatei.getRoot().toString()+zieldatei.subpath(0, zieldatei.getNameCount()-1).toString())))
            {
                FileUtil.createFolder(Paths.get(zieldatei.getRoot().toString()+zieldatei.subpath(0, zieldatei.getNameCount()-1).toString()));
            }
            Files.copy(quelldatei, zieldatei, flag );
        } catch (IOException ex)
        {
            Log.Write("Die Datei: " + quelldatei.toString() + " konnte nicht kopiert werden", FEHLER);
            return false;
        }
        return true;*/
        return true;
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
            if (FileUtil.isFile(pfad))
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
                    Files.move(pfad, newpfad, REPLACE_EXISTING);
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
