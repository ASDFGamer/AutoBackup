package hilfreich.deprecated;

import java.util.Calendar;
import java.io.*;

/**
 * Dies soll dafür sein, einfach Logeinträge, auch in Dateien anzulegen.
 * Meiner Meinung läuft alles aber es gibt noch mehere Stellen die verbessert werden könnten.
 * @author wildh
 * @deprecated Besser etwas das ILog implementiert benutzen.
 * @version 0.8
 */
public class LogAlt {
    private static String pfad="Log.txt";
    static private boolean std_console=true;
    static private boolean std_datei=true;
    static private boolean std_zeitangabe=true;
    static private boolean std_statusangabe=true;
    /**
     * Dies gibt das Logginglevel an ab welchem mit System.err ausgageben wird anstatt System.out
     */
    static private int std_errorstatus = 2;

    static private LogAlt slog = new LogAlt("Static aufruf");

    /**
     * Dies ist die normale {@link Log#write(java.lang.String, int) } Methode, nur static.
     * Wenn möglich nicht benutzen, sondern ein Objekt erstellen wenn sinvoll.
     * @param text Der Text der geschreiben werden soll.
     * @param i Das Logginglevel (-1:Debug,0:Info,1:Warnung,2:Fehler).
     */
    static void Write(String text, int i)
    {
        slog.write(text,i);
    }
    
    /**
     * Dies ist die normale {@link Log#write(java.lang.String) } Methode, nur static.
     * Wenn möglich nicht benutzen, sondern ein Objekt erstellen wenn sinvoll.
     * @param text Der Text der geschreiben werden soll.
     */
    static void Write(String text)
    {
        slog.write(text);
    }
    private boolean console=std_console;
    private boolean datei=std_datei;
    private boolean zeitangabe=std_zeitangabe;
    private boolean statusangabe=std_statusangabe;
    private int errorstatus = std_errorstatus;
    /**
     * Dies wird benutzt um zu sehen ob das Log Objekt das erste initialisierte Objekt des Programms ist.
     */
    private static boolean erster_Log=true;

    /**
     * Die Klasse des übergeordneten Objekts.
     */
    private String klasse="";

    /**
     * Das Logginglevel, noch nicht von außerhalb zu ändern.
     */
    static private int std_level=-1;//TODO auf 0 �ndern, wenn fertig
    private int level=std_level;

    Calendar rightNow = Calendar.getInstance();

    // --- Konstruktoren ---
    /**
     * Hiermit wird ein neuer Log angelegt, bei dem mindestens angegeben werden muss ob über Konsole und/oder Datei ausgegeben wird. 
     * @since 0.1
     * @see Log#Log(boolean, boolean)
     * @see Log#Log(boolean, boolean, String)
     */
    public LogAlt(){
            init();
    }

    /**
     * Hiermit wird ein neuer Log angelegt, bei dem mindestens angegeben werden muss ob über Konsole und/oder Datei ausgegeben wird. 
     * @param c Ausgabe über Konsole an/aus.
     * @param d Ausgabe über Datei an/aus.
     * @since 0.1
     * @see Log#Log()
     * @see Log#Log(boolean, boolean, String)
     */
    public LogAlt(boolean c,boolean d) {
            console=c;
            datei=d;
            init();
    }

    /**
     * Hiermit wird ein neuer Log angelegt, bei dem mindestens angegeben werden muss ob über Konsole und/oder Datei ausgegeben wird. 
     * @param c Ausgabe über Konsole an/aus.
     * @param d Ausgabe über Datei an/aus.
     * @param p Pfadangabe für die Datei.
     * @since 0.1
     * @see Log#Log()
     * @see Log#Log(boolean, boolean)
     */
    public LogAlt(boolean c, boolean d, String p){
            console=c;
            datei=d;
            pfad=p;
            init();
    }

    /**
     * Hiermit wird ein neuer Log angelegt, bei dem mindestens angegeben werden muss ob über Konsole und/oder Datei ausgegeben wird. 
     * @param c Ausgabe über Konsole an/aus.
     * @param d Ausgabe über Datei an/aus.
     * @param p Pfadangabe für die Datei.
     * @param Klasse Die Klasse des Übergeordneten Objekts (z.B. super.getClass().toString())
     * @since 0.9
     */
    public LogAlt(boolean c, boolean d, String p,String Klasse){
            console=c;
            datei=d;
            pfad=p;
            klasse=Klasse;
            init();
    }

    /**
     * Hiermit wird ein neuer Log angelegt, bei dem der Name der aufrufenden Klasse angegeben werden sollte.
     * @param Klasse Der Name des Übergeordneten Objekts (z.B. super.getClass().toString()).
     */
    public LogAlt(String Klasse){
        klasse=Klasse;
        init();
    }
    // --- Get Methoden ---

    /**
     * Hiermit wird der Pfad in dem die Logdatei liegt angegeben.
     * @return Dateipfad der Logdatei
     * @since 0.1
     */
    @SuppressWarnings("static-access")
    public String getPfad(){
            return this.pfad;
    }

    /**
     * Hiermit wird der Status der Konsolenausgabe ausgegeben (bei 'true' ist sie an und bei 'false' ausgeschaltet).
     * @return Status der Konsolenausgabe
     * @since 0.1
     */
    public boolean getConsole(){
            return this.console;
    }

    /**
     * Hiermit wird der Status der Dateiausgabe ausgegeben (bei 'true' ist sie an und bei 'false' ausgeschaltet).
     * @return Status der Dateiausgabe
     * @since 0.1
     */
    public boolean getDatei(){
            return this.datei;
    }

    /**
     * Hiermit wird der Status der Zeitangabe ausgegeben (bei 'true' ist sie an und bei 'false' ausgeschaltet). 
     * @return Status der Zeitausgabe
     * @since 0.5
     */
    public boolean getZeitangabe(){
            return this.zeitangabe;
    }

    /**
     * Hiermit wird der Status der Statusangabe ausgegeben (bei 'true' ist sie an und bei 'false' ausgeschaltet). 
     * @return Status der Statusangabe
     * @since 0.5
     */
    public boolean getStatusangabe(){
            return this.statusangabe;
    }

    // --- Set Methoden ---

    /**
     * Hiermit wird der Dateipfad ge�ndert.
     * @param p Neuer Dateipfad
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.1
     */
    public boolean setPfad(String p){
            pfad = p;
            return true;
    }

    /**
     * Hiermit wird der Status der Konsolenausgabe geändert.
     * @param c Bei 'true' wird sie eingeschaltet, bei 'false' ausgeschaltet.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.1
     */
    public boolean setConsole(boolean c){
            this.console=c;
            return true;
    }

    /**
     * Hiermit wird der Status der Dateiausgabe geändert.
     * @param c Bei 'true' wird sie eingeschaltet, bei 'false' ausgeschaltet.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.1
     */
    public boolean setDatei(boolean d){
            this.datei=d;
            return true;
    }

    /**
     * Hiermit wird der Status der Zeitangabe geändert.
     * @param z Bei 'true' wird sie eingeschaltet, bei 'false' ausgeschaltet.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.5
     */
    public boolean setZeitangabe(boolean z){
            this.zeitangabe=z;
            return true;
    }

    /**
     * Hiermit wird der Standard des Status der Statusangabe geändert.
     * @param s Bei 'true' wird sie eingeschaltet, bei 'false' ausgeschaltet.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.5
     */
    public boolean setStatusangabe(boolean s){
            this.statusangabe=s;
            return true;
    }

    /**
     * Hiermit wird der Status des Logginglevels geändert.
     * Achtung: bestehende Objekte werden nicht angepasst!
     * @param s Bei 'true' wird sie eingeschaltet, bei 'false' ausgeschaltet.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.9
     */
    public boolean setlevel(int lev){
            std_level=lev;
            return true;
    }

    /**
     * Hiermit wird der Standard des Status der Konsolenausgabe geändert.
      * Achtung: bestehende Objekte werden nicht angepasst!
     * @param c Bei 'true' wird sie eingeschaltet, bei 'false' ausgeschaltet.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.9
     */
    public static boolean std_setConsole(boolean c){
            std_console=c;
            return true;
    }

    /**
     * Hiermit wird der Standard des Status der Dateiausgabe geändert.
      * Achtung: bestehende Objekte werden nicht angepasst!
     * @param c Bei 'true' wird sie eingeschaltet, bei 'false' ausgeschaltet.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.9
     */
    public static boolean std_setDatei(boolean d){
            std_datei=d;
            return true;
    }

    /**
     * Hiermit wird der Standard des Status der Zeitangabe geändert.
      * Achtung: bestehende Objekte werden nicht angepasst!
     * @param z Bei 'true' wird sie eingeschaltet, bei 'false' ausgeschaltet.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.9
     */
    public static boolean std_setZeitangabe(boolean z){
            std_zeitangabe=z;
            return true;
    }

    /**
     * Hiermit wird der Standard des Status der Statusangabe geändert.
     * Achtung: bestehende Objekte werden nicht angepasst!
     * @param s Bei 'true' wird sie eingeschaltet, bei 'false' ausgeschaltet.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.9
     */
    public static boolean std_setStatusangabe(boolean s){
            std_statusangabe=s;
            return true;
    }

    /**
     * Hiermit wird der Standard des Status des Logginglevels geändert.
     * Achtung: bestehende Objekte werden nicht angepasst!
     * @param s Bei 'true' wird sie eingeschaltet, bei 'false' ausgeschaltet.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.9
     */
    public static boolean std_setlevel(int lev){
            std_level=lev;
            return true;
    }

    // --- Andere Methoden ---

    /**
     * Dies ist die Standardmethode um etwas im Log zu schreiben.
     * @param l Das was geschrieben werden soll.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.1
     * @see Log#write(String, int)
     */
    public boolean write(String l){
            if (console){
                    this.consoleWrite(l,0);
            }
            if (datei){
                    this.dateiWrite(l,0);
            }
            return true;
    }

    /**
     * Dies ist die Standardmethode um etwas im Log zu schreiben.
     * @param l Das was geschrieben werden soll.
     * @param Status hier wird der Status der Meldung angegeben (-1=Debug, 0=Info, 1=Warnung, 2=Fehler) 
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.5
     * @see Log#write(String)
     */
    public boolean write(String l,int Status){
            if (console){
                    this.consoleWrite(l,Status);
            }
            if (datei){
                    this.dateiWrite(l,Status);
            }
            return true;
    }

    /**
     * Dies ist die Methode um etwas in der Konsole zu schreiben. 
     * Eigentlich sollte aber nur {@link Log#write(String)} verwendet werden, da dort auch Konsole ausgew�hlt werden kann
     * @param l Das was geschrieben werden soll.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.1
     * @see Log#write(String)
     */
    public boolean consoleWrite(String l, int status){
            if (Level_ok(status)){
                    String text= Logtext(l,status);
                    if (status < errorstatus)
                    {
                        System.out.println(text );
                    }
                    else
                    {
                        System.err.println(text);
                    }
                    return true;
            }else{
                    return false;
            }

    }

    /**
     * Dies ist die Methode um etwas in eine Datei zu schreiben. 
     * Eigentlich sollte aber nur {@link Log#write(String)} verwendet werden, da dort auch Datei ausgew�hlt werden kann
     * @param l Das was geschrieben werden soll.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.1
     * @see Log#write(String)
     */
    public boolean dateiWrite(String l, int status){
            if (Level_ok(status)){
                    String text= Logtext(l,status);
                    File Datei = new File(pfad);
                    try {
                            FileWriter datei_schreiben = new FileWriter(Datei,true);
                            datei_schreiben.write(text+System.getProperty("line.separator"));
                            datei_schreiben.flush();
                            datei_schreiben.close();
                            return true;
                    } catch (IOException e) {
                            e.printStackTrace();
                            return false;
                    }
            } else {
                    return false;
            }


    }

    /**
     * Dies �berpr�ft, ob die Lognachricht unter dem eingetellten Logginglivel liegt und gibt dann false zur�ck, sonst true.
     * @param status Das Loglevel der Nachricht.
     * @return True, wenn status>= Loggonglevel, sonst false.
     */
    public boolean Level_ok(int status){
            if (level<=status){
                    return true;
            } else {
                    return false;
            }

    }

    /**
     * Dies schreibt den Text f�r die Datei und die Konsole.
     * Am besten nur von dateiWrite und consoleWrite aufrufen.
     * @param info Der Text, der �bertragen wurde
     * @param status Der Status als int
     * @return Der Text der ausgegeben werden soll
     * TODO Anzeige der Klasse �ndern?
     */
    private String Logtext(String info, int status){
            String text= Statusabfrage(status)+Zeitabfrage(zeitangabe)+"[ "+klasse+" ] "+ info;
            return text;
    }

    /**
     * Leert die Datei. Hierbei wird die Datei nicht wirklich geleert, sondern eher gelöscht und neu erstellt. 
     * Deshalb kann diese Methode auch zum erstellen von Dateien benutzt werden.
     * @return 'true' wenn alles gut ging, sonst 'false'
     * @since 0.1
     */
    public boolean Dateileeren(){
            File datei = new File(pfad);
            try {
                    if (datei.exists()){
                            datei.delete();
                    }
                    datei.createNewFile();
            } catch (IOException e) {
                    e.printStackTrace();
                    return false;
            }
            return true;
    }

    /**
     * Gibt die aktuelle Zeit zurück, falls es über 'Zeitangabe' eingeschaltet wurde. Sonst wird ein leerer String zurückgegeben.
     * @param Zeitangabe Falls 'true' dann wird sie Zeit zur�ckgegeben, sonst nicht.
     * @return Die Zeit, oder einen leeren String
     */
    private String Zeitabfrage( boolean zeitangabe){
            //rightNow.get(rightNow.MINUTE)
            if (zeitangabe){
                    rightNow = Calendar.getInstance(); //Geht vllt. effizienter
                    return  "[" +rightNow.get(Calendar.HOUR_OF_DAY)+ ":" +rightNow.get(Calendar.MINUTE)+":"+rightNow.get(Calendar.SECOND) +"] ";
            } else {
                    return  "";
            }
    }

    /**
     * Gibt ein Prefix zurück, dass den Status eines Logeintrags wiedergibt.
     *		       -1 : Debuginfo
     * 			0 : Info
     * 			1 : Warnung
     * 			2 : __FEHLER__
     * @param Status Hiermit wird der Status als Integer codiert übergeben.

     * @return Der Status als String.
     */
    private String Statusabfrage(int Status){
            if (statusangabe){
                    String Stat;
                    switch (Status){
                    case -1: Stat = "Debug";
                                    break;
                    case 0: Stat = "Info";
                                    break;
                    case 1: Stat = "Warnung";
                                    break;
                    case 2: Stat = "__FEHLER__";
                                    break;
                    default : Stat = "Info";
                                    break;
                    }
                    return "[" + Stat + "] ";
            } else {
                    return "";
            }
    }

    /**
     * Statische alternative zu {@link Log#consoleWrite(String, int)}, funktioniert, indem lokal eine neue Instanz von Log erzeugt wird.
     * @param text
     * @return 
     */
    public static boolean consolewrite_static(String text){
        LogAlt log = new LogAlt();
        log.write(text);
        return true;
    }

    /**
     * Dies wird aufgerufen, wenn der Log initialisiert wird.
     * @return
     */
    private boolean init(){
            if (erster_Log){
                    Dateileeren();
                    erster_Log=false;
            };
            return true;
    }



}
