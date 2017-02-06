package autobackup.copyFile;

import java.nio.file.Path;
import java.util.LinkedList;

/**
 *
 * @author Christoph Wildhagen
 */
public interface ISichern
{
    public boolean backupFiles(Path quellordner, Path zielordner, LinkedList<Path> neueDateien, int versionen);
}
