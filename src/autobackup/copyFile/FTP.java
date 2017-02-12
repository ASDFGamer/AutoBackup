
package autobackup.copyFile;

import autobackup.Data.Einstellungen;
import static hilfreich.FTPUtil.*;
import static hilfreich.FileUtil.*;
import hilfreich.Log;
import static hilfreich.LogLevel.*;
import java.io.IOException;
import java.net.SocketException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;
import static org.apache.commons.net.ftp.FTP.*;
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
    
    private LinkedList<Path> neueDateien = new LinkedList<>();
    
    private Log log = new Log(this.getClass().getSimpleName());
    
    private FTPClient client;
    
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
        client = new FTPClient();
        FTPClientConfig config = new FTPClientConfig();//TODO ADD config
        client.configure(config);
        
    }
    
    @Override
    public boolean backupFiles()
    {
        boolean result = true;
        int quellNameCount = quellordnerPath.getNameCount();
        Path zielpfad;
        try
        {
            if (!connect())
            {
                log.write("Es konnte keine Verbindung zum FTP-Server hergestellt werden",FEHLER);
                return false;
            }
            client.changeWorkingDirectory(this.zielordner.getPath());
            for (Path datei : neueDateien)
            {
                log.write(datei.toString());
                zielpfad = datei.subpath(quellNameCount, datei.getNameCount());
                if (isFolder(datei))
                {
                    log.write(datei.toString() + " ist ein Ordner");
                    if (!isFolderFTP(zielpfad,client,false))
                    {
                        createFolderFTP(zielpfad,client,false);
                    }
                }
                else if (isFile(zielpfad)) //Die Datei hat sich geändert (oder es gab noch keinen Dateibaum)
                {
                    log.write("Die Datei " + datei.toString() + " existiert schon, hat sich aber geändert.");
                    versionierungFTP(zielpfad,1,this.versions,client);
                    boolean result_temp = copyFileFTP(datei, zielpfad,client);
                    if(!result_temp)
                    {
                        result = false;
                    }
                }
                else //Es ist eine Datei die noch nicht kopiert wurde
                {
                    log.write("Die Datei " + datei.toString() + " wird gesichert.");
                    boolean result_temp = copyFileFTP(datei, zielpfad,client);
                    if(!result_temp)
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
        if (datei == null)
        {
            log.write("Es wird mit einer leeren Datei verglichen",FEHLER);
            return false;
        }
        Path kurzpfad = datei.subpath(this.quellordnerPath.getNameCount(), datei.getNameCount());
        if (isFileFTP(kurzpfad,client,false) || isFolderFTP(kurzpfad,client,false))
        {
            Date ftptime = getModificationDate(kurzpfad, client);
            if (ftptime == null)
            {
                ftptime = new Date(0L);
            }
            Date lokaltime = new Date(datei.toFile().lastModified());
            if (ftptime.compareTo(lokaltime)<0)
            {
                this.neueDateien.add(datei);
            }
        }
        return true;
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
    
    private boolean connect()
    {
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
            client.setFileTransferMode(BINARY_FILE_TYPE);
            client.setFileType(BINARY_FILE_TYPE);
        } catch (SocketException ex)
        {
            return false;
        }
        catch (IOException ex)
        {
            return false;
        }
        return true;
    }
}
