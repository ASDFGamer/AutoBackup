
package hilfreich;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;


/**
 *
 * @author Christoph Wildhagen 
 */
public class FileUtil {
    
    /**
     * Dasselbe wie System.getProperty("file.separator") nur kürzer (und vllt. schneller).
     */
    static public final String SEPERATOR = System.getProperty("file.separator");
    
    /**
     * Diese Methode überprüft, ob ein Pfad zu einder Datei wirklich auf eine Datei zeigt.
     * @param path Der Pfad zur Datei als String.
     * @return true, falls es eine Datei ist, sonnst false.
     */
    public static boolean isFile(String path)
    {
        File file;
        try{
            file = new File(path);
        }
        catch (NullPointerException e)
        {
            return false;
        }
        return isFile(file);
    }
    
    /**
     * Diese Methode überprüft, ob ein Pfad zu einder Datei wirklich auf eine Datei zeigt.
     * @param path Der Pfad zur Datei als File.
     * @return true, falls es eine Datei ist, sonnst false.
     */
    public static boolean isFile(File path)
    {
        return (path.exists() && !path.isDirectory());
    }
 
    /**
     * Diese Methode überprüft, ob ein Pfad zu einder Datei wirklich auf eine Datei zeigt.
     * @param path Der Pfad zur Datei als Path.
     * @return true, falls es eine Datei ist, sonnst false.
     */
    public static boolean isFile(Path path)
    {
        return isFile(path.toFile());
    }
    
    /**
     * Diese Methode überprüft, ob ein Pfad zu eindem Ordner wirklich auf einen Ordner zeigt.
     * @param path Der Pfad zum Ordner als String.
     * @return true, falls es ein Ordner ist, sonnst false.
     */
    public static boolean isFolder(String path)
    {
        File folder;
        try{
            folder = new File(path);
        }
        catch (NullPointerException e)
        {
            return false;
        }
        return isFolder(folder);
    }
    
    /**
     * Diese Methode überprüft, ob ein Pfad zu eindem Ordner wirklich auf einen Ordner zeigt.
     * @param path Der Pfad zum Ordner als File.
     * @return true, falls es ein Ordner ist, sonnst false.
     */
    public static boolean isFolder(File path)
    {
        return (path.exists() && path.isDirectory());
    }
    
    /**
     * Diese Methode überprüft, ob ein Pfad zu eindem Ordner wirklich auf einen Ordner zeigt.
     * @param path Der Pfad zum Ordner als Path.
     * @return true, falls es ein Ordner ist, sonnst false.
     */
    public static boolean isFolder(Path path)
    {
        return isFolder(path.toFile());
    }
    
    /**
     * Dies gibt einen sinnvollen Config Ordner zurück je nach OS.
     * @param name Der Name des Ordners (Programms).
     * @return Den Pfad des Ordners.
     */
    public static String getConfigFolder(String name)
    {
        switch (OSValidator.getOSString())
        {
            case "windows": return getConfigFolderWin(name);
            
            case "linux":   return getConfigFolderLinux(name);
            
            default:        return getConfigFolderNormal(name);  
            
        }
                
    }
    
    /**
     * Dies gibt einen guten Pfad für Windows zurück.
     * @param name Der Name des Ordners (Programms).
     * @return Den Pfad des Ordners.
     */
    private static String getConfigFolderWin(String name)
    {
        String userpath = System.getProperty("user.home");
        userpath += SEPERATOR +"AppData" + SEPERATOR + "Roaming" + SEPERATOR + name + SEPERATOR;
        return userpath;
    }
    
    /**
     * Dies gibt einen guten Pfad für Linux zurück.
     * @param name Der Name des Ordners (Programms).
     * @return Den Pfad des Ordners.
     */
    private static String getConfigFolderLinux(String name)
    {
        //TODO Den Ordner für Linux herausfinden.
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Dies gibt einen ordentlichen Pfad für alle anderen OS zurück.
     * @param name Der Name des Ordners (Programms).
     * @return Den Pfad des Ordners.
     */
    private static String getConfigFolderNormal(String name)
    {
        return System.getProperty("user.home") + SEPERATOR + name + SEPERATOR;
    }
    
    /**
     * Dies gibt eine Configfile zurück.
     * @param progname Der Name des Programms ({@link FileUtil#getConfigFolder(java.lang.String) }).
     * @param filename Der Name der Logfile.
     * @return Den Pfad der Configfile.
     */
    public static String getConfigFile(String progname, String filename)
    {
        String sfile = getConfigFolder(progname)+filename;
        File file = new File(sfile);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
            } catch (IOException ex)
            {
                Log.Write("Die Configfile konnte nicht erstellt werden.", 2);//TODO welche aktion soll dies auslösen? andere Configfile vorschlagen? Standardwerte?
            }
        }
        return sfile;
    }

}
