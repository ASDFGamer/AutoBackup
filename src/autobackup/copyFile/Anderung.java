
package autobackup.copyFile;

/**
 * Dies ist eine einfache Klasse die die letzte änderungszeit und den Hashwert speichern kann.
 * @author Christoph Wildhagen 
 */
class Anderung {
    private final String letzteAenderung;
    
    private final String hash;
    
    Anderung(String lastchanged, String hash)
    {
        this.letzteAenderung = lastchanged;
        this.hash = hash;
    }

    public String getLetzteAenderung()
    {
        return letzteAenderung;
    }

    public String getHash()
    {
        return hash;
    }
    
    
}
