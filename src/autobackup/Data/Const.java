
package autobackup.Data;

/**
 * Dies sind Konstanten die Mehrmals gebraucht werden.
 * @author Christoph Wildhagen 
 */
public class Const {
    
    /**
     * Der Name des Programms
     */
    static final public String PROGRAMM_NAME = "AutoBackup";
    
    /**
     * Die Version des Programms
     */
    static final public String VERSION = "0.1";
    
    /**
     * Die Verschiedenen Eigenschaften die der Log haben kann. TODO Enum
     */
    static final public String[] LOGEIGENSCHAFTEN = {"Console + Datei", "Nur Console", "Nur Datei", "Alles Aus"}; //TODO convert to enum
    
    /**
     * Die verschiedenen Anzahlen die es für viele Sachen gibt.
     */
    static final public String[] ANZAHLEN = {"UNBEGRENZT", "1", "2","3","4","5","6","7","8","9","10"};
}
