
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
import org.apache.commons.net.ftp.FTPClient;
import static org.apache.commons.net.ftp.FTP.*;
import org.apache.commons.net.ftp.FTPReply;

/**
 * Hiermit werden die Dateien gesichert falls der Zeilordner auf einem FTP-Laufwerk liegt.
 * @author ASDFGamer
 */
public class FTP implements ISichern {
    
    /**
     * Der Pfad zum Quellordner
     */
    private Path quellordnerPath;
    
    /**
     * Dies speichert alle Ordner ab, die schon mal abgefragt wurden, damit nicht nochmal geprüft werden muss.
     */
    private LinkedList<Path> bekannteFTPOrdner = new LinkedList<>();
    
    /**
     * Die URL des Zielordners
     */
    private URL zielordner;
    
    /**
     * Die Anzahl der Versionen die gespeichert werden sollen.
     */
    private int versions;
    
    /**
     * Die Dateien mit Datum die aus dem Dateibaum geldaen wurden.
     */
    private Properties dateibaum;
    
    /**
     * Dies ist eine Liste mit allen Dateien die gesichert werden sollen.
     */
    private LinkedList<Path> neueDateien = new LinkedList<>();
    
    /**
     * Mein Log
     */
    private Log log = new Log(this.getClass().getSimpleName());
    
    /**
     * Der benutzte FTP-Client
     */
    private FTPClient client;
    
    /**
     * Dies erstellt ein Objekt welches über FTP sichern kann.
     * @param quellordner Der Quellordner
     * @param zielordner Der Zielordner
     * @param versions Die Anzahl der Versionen
     * @throws IllegalArgumentException falls der Quellordner nicht lokal ist oder der Zielordner nicht per FTP zu erreichen ist.
     */
    public FTP(URL quellordner,URL zielordner,int versions) throws IllegalArgumentException
    {
        if (!quellordner.getProtocol().equals("file")||!zielordner.getProtocol().equals("ftp"))//der quellordner sollte weiterhin lokal bleiben
        {
            throw new IllegalArgumentException("Es kann kein Backup auf ftp gemacht werden da der Pfad nicht auf eine FTP-datei verweist sondern auf " + quellordner.getProtocol()); 
        }
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
        //FTPClientConfig config = new FTPClientConfig();
        //client.configure(config);
        
    }
    
    /**
     * {@inheritDoc }
     */
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
                        createFolderFTP(zielpfad,client);
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
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean setDateibaum(Properties dateibaum) {
        this.dateibaum = dateibaum;
        this.neueDateien = Backup.vergleicheDateien(this.dateibaum,this.quellordnerPath);
        return true;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public LinkedList<Path> getNeueDateien()
    {
        return this.neueDateien;
    }
    
    /**
     * {@inheritDoc }
     */
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
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean checkBackupFiles()
    {
        CheckDateien.checkBackupFiles(quellordnerPath, this);
        return true;
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public boolean checkAllFiles()
    {
        neueDateien = CheckDateien.checkAllFiles(quellordnerPath);
        return true;
    }
    
    /**
     * Dies Verbindet den Client mit dem FTP-Server und nimmt alle anfangseinstellungen vor.
     * @return true, falls alles gut ging, sonst false.
     */
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
            client.login(Einstellungen.ftpUser.getWert().get(), Einstellungen.ftpPasswort.getWert().get());      
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
