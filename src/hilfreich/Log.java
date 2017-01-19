/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilfreich;

import java.io.File;

/**
 *
 * @author Ursel
 */
public class Log implements ILog{
    
    //----Variablen----
    
    //--Klassenvariablen--
    /**
     * Der Pfad zu dem Log
     */
    private static File path;
    
    /**
     * Dies legt fest ob es standardmäßig eine ausgabe in der Konsole geben soll.
     */
    private static boolean stdConsolenausgabe = true;
    
    /**
     * Dies legt fest ob es standardmäßig eine ausgabe in eine Datei geben soll.
     */
    private static boolean stdFileausgabe  = false;
    
    /**
     * Dies legt fest ob die Uhrzeit in den Logeinträgen angezeigt werden soll.
     */
    private static boolean stdZeitausgabe = true;
    
    //--Objektvariablen--
    /**
     * Dies legt fest ob es für dieses Objekt eine Ausgebe in die Konsole geben soll.
     */
    private boolean consolenausgabe = stdConsolenausgabe;
    
    /**
     * Dies legt fest ob es für dieses Objekt eine ausgabe in eine Datei geben soll.
     */
    private boolean fileausgabe = stdFileausgabe;
    
    /**
     * Dies gibt den Namen der aufrufenden Klasse an (oder einen anderen String zum Identifizieren).
     */
    private String klasse = null;
    
    //--sinnvolle Hilfsvariablen--
    /**
     * Dies kann für den wechsel zwischen static und nicht static Methoden genutzt werden.
     */
    private static Log log = new Log("stat. Aufruf");
    
    
    //----Methoden----
    //---public Methoden---
    //--Konstruktoren--
    /**
     * Dies erstellt einen neuen Log mit den Standardeinstellungen.
     */
    public Log()
    {
        //
    }
    
    /**
     * Dies erstellt einen neuen Log mit den Standardeinstellungen und der Name der Klasse aus der dies aufgerufen wird kann angegeben werden (oder jeder andere String).
     * @param klasse Der Name der Klasse (z.B. über super.getClass().toString()).
     */
    public Log(String klasse)
    {
        this.klasse = klasse;
    }
    
    /**
     * Dies erstellt einen neuen Log bei dem angegeben werden kann,  ob die consolenausgabe oder die Ausgabe in eine Datei an oder ausgeschaltet sein soll.
     * @param console Gibt an ob die Konsolenausgabe an oder aus sein soll.
     * @param file Gibt an ob die Dateiausgabe an oder aus sein soll.
     */
    public Log(boolean console, boolean file)
    {
        this.consolenausgabe = console;
        this.fileausgabe = file;
    }
    
    /**
     * Dies erstellt einen neuen Log bei dem angegeben werden kann,  ob die consolenausgabe oder die Ausgabe in eine Datei an oder ausgeschaltet sein soll.
     * Außerdem kann der Name der Aufrufenden Klasse angegeben werden.
     * @param console Gibt an ob die Konsolenausgabe an oder aus sein soll.
     * @param file Gibt an ob die Dateiausgabe an oder aus sein soll.
     * @param klasse Der Name der Klasse (z.B. über super.getClass().toString()).
     */
    public Log(boolean console, boolean file, String klasse)
    {
        this.consolenausgabe = console;
        this.fileausgabe = file;
        this.klasse = klasse;
    }
    
    //--statische Methoden--
    /**
     * Dies ist dasselbe wie ein normales {@link Log#write(java.lang.String) }, nur ist es nöglich dies von einem statischen Standpunkt auszuführen.
     * @param text Der text der im Log erscheinen soll.
     * @return true, falls alles gut ging, sonst false.
     */
    public static boolean Write(String text)
    {
        return log.write(text);
    }
    
    /**
     * Dies ist dasselbe wie ein normales {@link Log#write(java.lang.String, int) }, nur ist es nöglich dies von einem statischen Standpunkt auszuführen.
     * @param text Der text der im Log erscheinen soll.
     * @param level Dies gibt das Logginglevel an ({@link LogLevel}).
     * @return true, falls alles gut ging, sonst false.
     */
    public static boolean Write(String text, int level)
    {
        return log.write(text, level);
    }
    
    //---ILog Methoden---
    //--normale Benutzung--
    @Override
    public boolean write(String text) {
        //TODO wenn fertig dann Code aus write(text,level) kopieren für performance
        return this.write(text, LogLevel.INFO);
    }

    @Override
    public boolean write(String text, int Level) {
        boolean result = true;
        if (this.consolenausgabe)
        {
            if (!consoleWrite(text,Level))
            {
                result = false;
            }
        }
        if (this.fileausgabe)
        {
            if (!fileWrite(text,Level))
            {
                result = false;
            }
        }
        return result;
    }

    @Override
    public boolean clearLog() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    //--Getter/Setter--
    //-Konsole-
    @Override
    public boolean setConsole(boolean an) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setStdConsole(boolean an) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getConsole() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getStdConsole() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //-Datei-
    @Override
    public boolean setFile(boolean an) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setStdFile(boolean an) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getStdFile() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //-Dateipfad-
    @Override
    public boolean setStdFilePath(String path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setStdFilePath(File path) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getStdFilePath() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //-Zeitanzeige-
    @Override
    public boolean setStdTimeStamp(boolean an)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean getStdTimeStamp()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    //---private Methoden---

    private boolean consoleWrite(String text, int level)
    {
        
        String Logtext = getLogtext(text,level);
        return true;
    }

    private boolean fileWrite(String text, int level)
    {
        String Logtext = getLogtext(text,level);
        return true;
    }



    private String getLogtext(String text, int level)
    {
        String timestamp = getTimestamp();
        String loglevel = getLevelText(level);
        String logtext = null;
        if (stdZeitausgabe) {
            logtext += timestamp;
        }
        logtext += loglevel;
        logtext += text;
        return logtext;
    }
    
    private String getTimestamp()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private String getLevelText(int level)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
}
