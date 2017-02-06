/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilfreich;

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
     * @param Level Das Logginglevel ({@link LogLevel})
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean write(String text, LogLevel Level);
    
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
    public boolean setFileausgabe(boolean an);
    
    /**
     * Dies gitb an ob es Ausgabe in der Datei für alle Objekte gibt.
     * @param an true, falls an, sonst aus.
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean setStdFileausgabe(boolean an);
    
    /**
     * Dies gibt zurück, ob die Ausgabe in Dateien angeschaltet ist für dieses Objekt.
     * @return true, falls angeschaltet, sonst false.
     */
    public boolean getFileausgabe();
    
    /**
     * Dies gibt zurück, ob die Ausgabe in Dateien angeschaltet ist für alle Objekte.
     * @return true, falls angeschaltet, sonst false.
     */
    public boolean getStdFileausgabe();
    
    /**
     * Diese Methode setzt den Pfad der Logdatei. Dies ist für alle Objekte.
     * @param path Der Pfad zu der Logdatei
     * @return true, falls alles gut ging, sonst false
     */
    public boolean setStdFilePath(String path);
    
    /**
     * Dies gibt den Pfad zur Logdatei zurück.
     * @return Den Pfad als String.
     */
    public String getStdFilePath();
    
    /**
     * Dies stellt den Uhrzeit vor einem Logeintrag an oder aus.
     * Hiervon gibt es nur die Version für alle Objekte.
     * @param an Den Timestamp an- oder ausschalten.
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean setStdTimeStamp(boolean an);
    
    /**
     * Dies gibt zurück, ob der Timestamp an oder ausgaeschaltet ist.
     * @return Ob der Timestamp an.
     */
    public boolean getStdTimeStamp();
    
    /**
     * Dies legt das minimale Logginglevel fest ab welchem Einträge verarbeitet werden für dieses Objekt.
     * @param level Das minimale Level
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean setMinLoglevel(int level);
    
    /**
     * Dies gibt das minimale Logginglevel welches bei diesem Objekt berücksichtigt wird zurück.
     * @return Das minimale Logginglevel.
     */
    public int getMinLoglevel();
    
    /**
     * Dies legt das standardmäßige minimale Logginglevel fest ab welchem Einträge verarbeitet werden.
     * @param level Das minimale Level
     * @return true, falls alles gut ging, sonst false.
     */
    public boolean setStdMinLoglevel(int level);
    
    /**
     * Dies gibt das minimale Logginglevel welches standardmäßig berücksichtigt wird zurück.
     * @return Das minimale Logginglevel.
     */
    public int getStdMinLoglevel();
    
}
