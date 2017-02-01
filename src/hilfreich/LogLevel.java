
package hilfreich;

/**
 * Dies sind die verschiedenen Logginglevel.
 * TODO zu Enum umbauen
 * @author Christoph Wildhagen 
 */
public class LogLevel {
    
    /**
     * Dies sind Einträge die noch unwichtiger als Debugeinträge sind, z.b. für schleifen die 1000 mal durchgelaufen werden. 
     */
    static final public int VERBOSE = -2;
    
    /**
     * Für Debugausgabe, besser nicht in Realeasecode
     */
    static final public int DEBUG = -1;
    
    /**
     * Dies sind normale Logeinträge, weder positiv noch negativ
     */
    static final public int INFO = 0;
    
    /**
     * Dies sind einfache Warnungen, welche den Programmablauf nicht groß beinträchtigen
     */
    static final public int WARNUNG = 1;
    
    /**
     * Dies sind Fehler, die den Programmablauf beinträchtigen, diesen aber nicht unbedingt abbrechen.
     */
    static final public int FEHLER = 2;
    
    /**
     * Dies sind Fehler die zum abbruch des Programms führen.
     */
    static final public int FATAL_ERROR = 3;
    
    /**
     * Dies gibt an Ab welchem Level System.err benutzt wird anstatt von System.out
     */
    static final public int ERROR_LEVEL = 2;
}
