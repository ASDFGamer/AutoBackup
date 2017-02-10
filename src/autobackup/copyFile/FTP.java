
package autobackup.copyFile;

import autobackup.Data.Einstellungen;
import static hilfreich.FileUtil.createFolder;
import static hilfreich.FileUtil.isFile;
import static hilfreich.FileUtil.isFolder;
import hilfreich.Log;
import static hilfreich.LogLevel.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Properties;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPReply;

/**
 *
 * @author Christoph Wildhagen 
 */
public class FTP implements ISichern {
    
    private URL quellordner;
    
    private Path quellordnerPath;
    
    private URL zielordner;
    
    private int versions;
    
    private Properties dateibaum;
    
    private LinkedList<Path> neueDateien;
    
    private Log log = new Log(this.getClass().getSimpleName());
    
    public FTP(URL quellordner,URL zielordner,int versions) throws IllegalArgumentException
    {
        if (!quellordner.getProtocol().equals("file")||!zielordner.getProtocol().equals("ftp"))//der quellordner sollte weiterhin lokal bleiben
        {
            throw new IllegalArgumentException("Es kann kein Backup auf ftp gemacht werden da der Pfad nicht auf eine FTP-datei verweist sondern auf " + quellordner.getProtocol()); 
        }
        this.quellordner = quellordner;
        this.zielordner = zielordner;
        this.versions = versions;
        try
        {
            this.quellordnerPath=Paths.get(quellordner.toURI());
        }
        catch (URISyntaxException e)
        {
            throw new IllegalArgumentException("Es kann kein Backup auf ftp gemacht werden da der Pfad nicht auf eine FTP-datei verweist sondern auf " + quellordner.getProtocol());
        }
    }
    
    @Override
    public boolean backupFiles()
    {
        boolean result = true;
        int quellNameCount = quellordnerPath.getNameCount();
        Path kurzpfad;
        Path zielpfad;
        FTPClient client = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();//TODO ADD config
        client.configure(config);
        try
        {
            client.connect(this.zielordner.getHost());
            int reply = client.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) 
            {
                client.disconnect();
                log.write("Der FTP Server hat die verbindung abgelehnt.",FEHLER);
                return false;
            }
            client.login(Einstellungen.ftpUser.get(), Einstellungen.ftpPasswort.get());
            //log.write(client.listDirectories().length + "");
            client.changeWorkingDirectory(this.zielordner.getPath());
            //log.write(client.listDirectories().length + "");
            for (Path datei : neueDateien)
            {
                kurzpfad = datei.subpath(quellNameCount, datei.getNameCount());
                zielpfad = Paths.get(zielordner.getPath()).resolve(kurzpfad);
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
                    //versionierungFTP(zielpfad,1,this.versions);
                    //boolean result_temp = copyFileFTP(datei, zielpfad, new CopyOption[]{REPLACE_EXISTING, COPY_ATTRIBUTES},client);
                    //if(!result_temp)
                    {
                        result = false;
                    }
                }
                else //Es ist eine Datei die noch nicht kopiert wurde
                {
                    log.write("Die Datei " + datei.toString() + " wird gesichert");
                    //boolean result_temp = copyFileFTP(datei, zielpfad, new CopyOption[]{COPY_ATTRIBUTES},client);
                    //if(!result_temp)
                    {
                        result = false;
                    }
                }
            }
            
            client.logout();
            
        } 
        catch (IOException ex)
        {
            log.write("Es kann keine Verbindung zum FTP-Server hergestellt werden.",FEHLER);
            result = false;
        }
        finally
        {
            if(client.isConnected())
            {
                try
                {
                    client.disconnect();
                }
                catch (IOException e)
                {
                    log.write("Der FTP Client konnte die Verbindung nicht beenden.",DEBUG);
                }
            }
        }
        return result;
    }

    @Override
    public boolean setDateibaum(Properties dateibaum) {
        this.dateibaum = dateibaum;
        this.neueDateien = Backup.vergleicheDateien(dateibaum,this.quellordnerPath); //TODO überprüfen ob sinnvoll
        return true;
    }
    
    public LinkedList<Path> getNeueDateien()
    {
        return this.neueDateien;
    }

    @Override
    public boolean vergleicheDatei(Path datei)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
