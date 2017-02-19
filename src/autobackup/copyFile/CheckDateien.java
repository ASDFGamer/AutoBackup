
package autobackup.copyFile;

import java.io.File;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 * Hiermit wird auf geänderte Dateien geprüft.
 * @author ASDFGamer
 */
public class CheckDateien {
    
    /**
     * Dies überprüft die Dateien im Zielordner, ob sie neuer sind und trägt neue Dateien zum sichern ein
     * @param quellordner Der Pfad zu dem Ordner der überprüft werden soll.
     * @param sichern Das Objekt das zum sichern verwendet wird.
     * @return true, falls alles geklappt hat, sonst false.
     */
    public static boolean checkBackupFiles(Path quellordner, ISichern sichern)
    {

        if (quellordner.toFile().isDirectory())
        {
            File[] dateien = quellordner.toFile().listFiles();
            for (File datei : dateien)
            {
                if (datei.isDirectory())
                {
                    checkBackupFilesR(datei,sichern);
                }
                else
                {
                    sichern.vergleicheDatei(datei.toPath());
                }
            }
        }
        else
        {
            sichern.vergleicheDatei(quellordner);
        }
        return true;
    }
    
     /**
     * Dies überprüft die Dateien im Zielordner, ob sie neuer sind und trägt neue Dateien zum sichern ein
     * @return true, falls alles geklappt hat, sonst false.
     */
    private static boolean checkBackupFilesR(File ordner,ISichern sichern)
    {
        if (ordner.isDirectory())
        {
            File[] dateien = ordner.listFiles();
            for (File datei : dateien)
            {
                if (datei.isDirectory())
                {
                    checkBackupFilesR(datei,sichern);
                }
                else
                {
                    sichern.vergleicheDatei(datei.toPath());
                }
            }
        }
        else
        {
            sichern.vergleicheDatei(ordner.toPath());
        }
        return true;
    }
    
    /**
     * Dies markiert alle Dateien aus dem Quellordner zum sichern.
     * @param quellordner Der Quellordner.
     * @return Eine Liste von allen Dateien
     */
    public static LinkedList<Path> checkAllFiles(Path quellordner)
    {
        LinkedList<Path> neueDateien = new LinkedList<>();
        if (quellordner.toFile().isDirectory())
        {
            File[] dateien = quellordner.toFile().listFiles();
            for (File datei : dateien)
            {
                if (datei.isDirectory())
                {
                    neueDateien.addAll(checkAllFilesR(datei));
                }
                else
                {
                    neueDateien.add(datei.toPath());
                }
            }
        }
        else
        {
            neueDateien.add(quellordner);
        }
        
        return neueDateien;
    }
    
    /**
     * Dies markiert alle Dateien aus dem Quellordner zum sichern.
     * Nur für rekursion
     * @return true, falls es geklappt hat, sonst false.
     */
    private static LinkedList<Path> checkAllFilesR(File ordner)
    {
        LinkedList<Path> neueDateien = new LinkedList<>();
        if (ordner.isDirectory())
        {
            File[] dateien = ordner.listFiles();
            for (File datei : dateien)
            {
                if (datei.isDirectory())
                {
                    checkAllFilesR(datei);
                }
                else
                {
                    neueDateien.add(datei.toPath());
                }
            }
        }
        else
        {
            neueDateien.add(ordner.toPath());
        }
        
        return neueDateien;
    }

}
