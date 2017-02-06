
package autobackup.copyFile;

import static hilfreich.FileUtil.*;
import hilfreich.Log;
import static hilfreich.LogLevel.*;
import java.nio.file.CopyOption;
import java.nio.file.Path;
import static java.nio.file.StandardCopyOption.*;
import java.util.LinkedList;

/**
 *
 * @author Christoph Wildhagen 
 */
public class Lokal implements ISichern{
    
    Log log = new Log(super.getClass().getSimpleName());
    
    /**
     * Diese Funktion sichert die ausgewählten Dateien in den Zielordner und legt falls nötig Versionen an.
     * @return true, falls das Backup geklappt hat, sonst false (für true muss die versionierung nicht geklappt haben).
     */
    @Override
    public boolean backupFiles(Path quellordner, Path zielordner, LinkedList<Path> neueDateien, int versionen)
    {
        boolean result = true;
        Path kurzpfad;
        Path zielpfad;
        int quellNameCount = quellordner.getNameCount();
        for (Path datei : neueDateien)
        {
            kurzpfad = datei.subpath(quellNameCount, datei.getNameCount());
            zielpfad = zielordner.resolve(kurzpfad);
            if (isFolder(datei))
            {
                if (!isFolder(zielpfad))
                {
                    createFolder(zielpfad);
                }
            }
            else if (isFile(zielpfad)) //Die Datei hat sich geändert (oder es gab noch keinen Dateibaum)
            {
                log.write("Die Datei " + zielpfad + " existiert schon.",DEBUG);
                versionierung(zielpfad,1,versionen);
                boolean result_temp = copyFile(datei, zielpfad, new CopyOption[]{REPLACE_EXISTING, COPY_ATTRIBUTES});
                if(!result_temp)
                {
                    result = false;
                }
            }
            else //Es ist eine Datei die noch nicht kopiert wurde
            {
                boolean result_temp = copyFile(datei, zielpfad, new CopyOption[]{COPY_ATTRIBUTES});
                if(!result_temp)
                {
                    result = false;
                }
            }
            
        }
        return result;
    }

}
