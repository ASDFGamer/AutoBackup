/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilfreich;

import java.io.File;

/**
 * Dies ist ein Interface für einen Log
 * Sinnvoll wäre es, wenn noch static Methoden z.B. für write hinzugefügt werden.
 * @author Christoph
 */
public interface ILog {
    
    /**
     * Hiermit wird der Text im Std. Logginglevel geschrieben.
     * @param text Der Logtext
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean write(String text);
    
    /**
     * Hiermit wird der Text im angegbenen Logginglevel geschrieben.
     * @param text Der Logtext
     * @param Level Das Logginglevel
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean write(String text, int Level);
    
    /**
     * Hiermit wird die Datei geleert und in der Console wird es quasi geleert.
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean clearLog();
    
    /**
     * Dies gitb an ob es ausgabe in der Console für dieses Objekt gibt.
     * @param an true, falls an, sonst aus.
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean setConsole(boolean an);
    
    /**
     * Dies gitb an ob es Ausgabe in der Console für alle Objekte gibt.
     * @param an true, falls an, sonst aus.
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean setStdConsole(boolean an);
    
    /**
     * Dies gibt zurück ob es Consolenausgabe für dieses Objekt gibt.
     * @return true, wenn die Ausgabe an ist, sonst false.
     */
    public boolean getConsole();
    
    /**
     * Dies gibt zurück, ob es Consolenausgabe für alle Objekte gibt. 
     * @return true, wenn die Ausgabe an ist, sonst false.
     */
    public boolean getStdConsole();
    
    /**
     * Dies gitb an ob es Ausgabe in der Datei für dieses Objekt gibt.
     * @param an true, falls an, sonst aus.
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean setFile(boolean an);
    
    /**
     * Dies gitb an ob es Ausgabe in der Datei für alle Objekte gibt.
     * @param an true, falls an, sonst aus.
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean setStdFile(boolean an);
    
    /**
     * Dies gibt zurück, ob die Ausgabe in Dateien angeschaltet ist für dieses Objekt.
     * @return true, falls angeschaltet, sonst false.
     */
    public boolean getFile();
    
    /**
     * Dies gibt zurück, ob die Ausgabe in Dateien angeschaltet ist für alle Objekte.
     * @return true, falls angeschaltet, sonst false.
     */
    public boolean getStdFile();
    
    public boolean setStdFilePath(String path);
    
    public boolean setStdFilePath(File path);
    
    public String getStdFilePath();
    
}