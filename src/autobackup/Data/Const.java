
package autobackup.Data;

/**
 * Dies sind Konstanten die mehrmals gebraucht werden.
 * @author ASDFGamer 
 */
public class Const {
    
    /**
     * Der Name des Programms
     */
    static final public String PROGRAMM_NAME = "AutoBackup";
    
    /**
     * Die Version des Programms
     */
    static final public String VERSION = "0.3";
    
    /**
     * Die verschiedenen Eigenschaften die der Log haben kann.
     */
    static public enum LOGEIGENSCHAFTEN{
        alles("Console + Datei"), 
        console("Nur Console"), 
        datei("Nur Datei"), 
        aus("Alles Aus");
        
        private String text;
        
        private LOGEIGENSCHAFTEN(String text)
        {
            this.text = text;
        }
        
        /**
         * Dies gibt den jeweiligen beschreibenen Text zurück.
         * @return Der beschreibene Text dieser Eigenschaft.
         */
        public String getText()
        {
            return this.text;
        }
        
        /**
         * Dies gibt ein Array von allen beschreibenen Texten zurück
         * @return Alle beschreibenen Texte als String-Array
         */
        public static String[] getAllText()
        {
            String[] result = new String[LOGEIGENSCHAFTEN.values().length];
            for (int i = 0; i<LOGEIGENSCHAFTEN.values().length; i++)
            {
                result[i] = LOGEIGENSCHAFTEN.values()[i].getText();
            }
            return result;
        }
    }
    
    /**
     * Die verschiedenen Anzahlen die es für viele Sachen gibt.
     */
    static final public String[] ANZAHLEN = {"UNBEGRENZT", "1", "2","3","4","5","6","7","8","9","10"};
    
    /**
     * Dies sind alle Werte die zu true konvertiert werden sollen.
     */
    static final public String[] TRUE_VALUES = {"true","wahr"};
    
    /**
     * Dies sind alle Werte die zu false konvertiert werden sollen.
     */
    static final public String[] FALSE_VALUES = {"false","falsch"};
}
