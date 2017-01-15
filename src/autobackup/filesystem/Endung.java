
package autobackup.filesystem;

/**
 * Diese Klasse wird benutzt um Dateiendungen zu speichern.
 * @author Christoph Wildhagen 
 */
public class Endung {
    
    /**
     * Die Endung als String ohne Punkt.
     */
    private final String endung;
    
    /**
     * Hiermit wir eine neue Endung erstellt.
     * @param endung Die Endung die gespeichert werden soll.
     */
    public Endung(String endung)
    {
        if (endung.charAt(0)=='.')
        {
            this.endung = endung.substring(1);
        }
        else
        {
            this.endung = endung;
        }
    }
    
    /**
     * Hiermit wird die Endung ohne Punkt zurückgegeben.
     * @return Die Endung
     */
    public String getEndung()
    {
        return endung;
    }
    
    /**
     * Hiermit wird die Endung mit Punkt zurückgegeben.
     * @return "." + Die Endung
     */
    public String getPunktEndung()
    {
        return "." + endung;
    }
}
