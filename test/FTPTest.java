
import autobackup.Data.Einstellungen;
import hilfreich.FTPUtil;
import java.io.IOException;
import java.net.SocketException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ASDFGamer
 */
public class FTPTest {
    
    public static final Logger LOG = Logger.getLogger(FTPTest.class.getName());
    
    /**
     * Der benutzte FTP-Client
     */
    private FTPClient client;
    
    public static void main(String[] args) throws IOException {
        FTPTest ftpt = new FTPTest();
        if (!ftpt.connect()||!ftpt.createFolder())
        {
            LOG.severe("Der Test wurde nicht bestanden.");
            LOG.info(ftpt.client.printWorkingDirectory());
        }
        else
        {
            LOG.info("Der Test wurde bestanden.");
            LOG.info(ftpt.client.printWorkingDirectory());
        }
    }

    private boolean createFolder() {
        return FTPUtil.createFolderFTP(Paths.get("a/b/c/d/e/f/g"), client);
    }
    
    private boolean connect()
    {
        client = new FTPClient();
        try
        {
            client.connect(/*"192.168.2.1"*/new URL("ftp://192.168.2.1/USB_DISK_2.0/partition1/Christoph/Backup/").getHost());
            int reply = client.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) 
            {
                client.disconnect();
                LOG.info("Der FTP Server hat die verbindung abgelehnt.");
                return false;
            }
            client.login("Christoph", "Test1234");      
            client.setFileTransferMode(BINARY_FILE_TYPE);
            client.setFileType(BINARY_FILE_TYPE);
            client.changeWorkingDirectory("USB_DISK_2.0/partition1/Christoph/Backup/");
            LOG.info("connected to " + client.printWorkingDirectory());
        } catch (SocketException ex)
        {
            LOG.warning("not connected");
            return false;
        }
        catch (IOException ex)
        {
            LOG.warning("not connected");
            return false;
        }
        return true;
    }

    private boolean changeFolder() {
        try {
            LOG.info(client.printWorkingDirectory());
            
        } catch (IOException ex) {
            Logger.getLogger(FTPTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        return FTPUtil.changeToDirROOT("mnt/USB_DISK_2.0/partition1/Christoph/Backup/", client);
    }
}
