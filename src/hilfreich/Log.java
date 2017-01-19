/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilfreich;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;

/**
 * Dies ist eine einfache und relativ Performante Logausgabe.
 * TODO Möglichkeit für Versionierung und mehreren Logs
 * @author Christoph
 */
public class Log implements ILog{
    
    //----Variablen----
    
    //--Klassenvariablen--
    /**
     * Der Pfad zu dem Log oder dem Ordner
     */
    private static String path;
    
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
    
    /**
     * Dies gibt das minimale Logginglevel an.
     */
    private static int stdMinLoglevel = 0;
    
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
    
    /**
     * Dies gibt das minimale logginglevel für dieses Objekt an.
     */
    private int minLoglevel = stdMinLoglevel;
    
    //--sinnvolle Hilfsvariablen--
    /**
     * Dies kann für den wechsel zwischen static und nicht static Methoden genutzt werden.
     */
    private static Log log = new Log("stat. Aufruf");
    
    /**
     * Dies ist ein Kalender, für die Zeitabfrage, für Ressourcen als globale Variable.
     */
    private Calendar rightNow = Calendar.getInstance();
    
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
    public boolean write(String text, int level) {
        boolean result = true;
        if (level >= this.minLoglevel)
        {
            if (this.consolenausgabe)
            {
                if (!consoleWrite(text,level))
                {
                    result = false;
                }
            }
            if (this.fileausgabe)
            {
                if (!fileWrite(text,level))
                {
                    result = false;
                }
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
        this.consolenausgabe = an;
        return true;
    }

    @Override
    public boolean setStdConsole(boolean an) {
        Log.stdConsolenausgabe = an;
        return true;
    }

    @Override
    public boolean getConsole() {
        return this.consolenausgabe;
    }

    @Override
    public boolean getStdConsole() {
        return Log.stdConsolenausgabe;
    }
    
    //-Datei-
    @Override
    public boolean setFileausgabe(boolean an) {
        this.fileausgabe = an;
        return true;
    }

    @Override
    public boolean setStdFileausgabe(boolean an) {
        Log.stdFileausgabe = an;
        return true;
    }

    @Override
    public boolean getFileausgabe() {
        return this.fileausgabe;
    }

    @Override
    public boolean getStdFileausgabe() {
        return Log.stdFileausgabe;
    }
    
    //-Dateipfad-
    @Override
    public boolean setStdFilePath(String path) {
        Log.path = path;
        return true;
    }

    @Override
    public String getStdFilePath() {
        return Log.path;
    }
    
    //-Zeitanzeige-
    @Override
    public boolean setStdTimeStamp(boolean an)
    {
        Log.stdZeitausgabe = an;
        return true;
    }

    @Override
    public boolean getStdTimeStamp()
    {
        return Log.stdZeitausgabe;
    }
    
    //-Min. Loglevel-
    @Override
    public boolean setMinLoglevel(int level)
    {
        this.minLoglevel = level;
        return true;
    }

    @Override
    public int getMinLoglevel()
    {
        return this.minLoglevel;
    }

    @Override
    public boolean setStdMinLoglevel(int level)
    {
        Log.stdMinLoglevel = level;
        return true;
    }

    @Override
    public int getStdMinLoglevel()
    {
        return Log.stdMinLoglevel;
    }
    
    //---private Methoden---
    
    /**
     * Dies schreibt den Text dem level entsprechend in die Console
     * @param text Der Text
     * @param level Das Logginglevel
     * @return true, falls alles gut ging, sonst false.
     */
    private boolean consoleWrite(String text, int level)
    {
        
        String logtext = getLogtext(text,level);
        if (level < LogLevel.ERROR_LEVEL)
        {
            System.out.println(logtext);
        }
        else
        {
            System.err.println(logtext);
        }
        return true;
    }
    
    /**
     * Dies schreibt den Text dem level entsprechend in die angegebene Datei
     * @param text Der Text
     * @param level Das Logginglevel
     * @return true, falls alles gut ging, sonst false.
     */
    private boolean fileWrite(String text, int level)
    {
        String logtext = getLogtext(text,level);
        try {
            FileWriter datei_schreiben = new FileWriter(path,true);
            datei_schreiben.write(logtext+System.getProperty("line.separator"));
            datei_schreiben.flush();
            datei_schreiben.close();
        } 
        catch (IOException e)
        {
            return false;
        }
        return true;
    }

    /**
     * Dies gibt den Text aus der geloggt werden soll.
     * @param text Der text der gelogt werden soll.
     * @param level Das logginglevel
     * @return Den gesamten Text der gelogt werden soll (mit Zeit, logginlevel)
     */
    private String getLogtext(String text, int level)
    {
        String loglevel = getLevelText(level);
        String logtext = "";
        if (stdZeitausgabe) {
            logtext += getTimestamp();
        }
        logtext += loglevel;
        logtext += text;
        return logtext;
    }
    
    /**
     * Dies gibt die aktuelle Zeit als String zurück
     * @return Die aktuelle Zeit als String
     */
    private String getTimestamp()
    {
        rightNow = Calendar.getInstance(); //Geht vllt. effizienter
        return  "[" +rightNow.get(Calendar.HOUR_OF_DAY)+ ":" +rightNow.get(Calendar.MINUTE)+":"+rightNow.get(Calendar.SECOND) +"] ";

    }
    
    /**
     * Dies gibt das Lgginglevel als Text zurück.
     * @param level Das Logginglevel als int.
     * @return Das Logginglevel als String.
     */
    private String getLevelText(int level)
    {
        switch (level)
        {
            case LogLevel.VERBOSE:      return "[Verbose]    ";
            
            case LogLevel.DEBUG:        return "[Debug]      ";
            
            case LogLevel.INFO:         return "[Info]       ";
            
            case LogLevel.WARNUNG:      return "[Warnung]    ";
            
            case LogLevel.FEHLER:       return "[Fehler]     ";
            
            case LogLevel.FATAL_ERROR:  return "[Fatal Error]";
            
            default:                    return "[Info]       ";
        }
    }

    


    
}
