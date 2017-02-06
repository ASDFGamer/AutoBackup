
package autobackup.copyFile;

import java.nio.file.Path;
import java.util.LinkedList;
import org.apache.commons.net.ftp.FTPClient;

/**
 *
 * @author Christoph Wildhagen 
 */
public class FTP implements ISichern {

    @Override
    public boolean backupFiles(Path quellordner, Path zielordner, LinkedList<Path> neueDateien, int versionen)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
