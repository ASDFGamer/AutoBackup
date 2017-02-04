package autobackup.settings;

/**
 * Dieses Interface wird genutzt um Einstellungen zu Laden/Speichern.
 * @author Christoph Wildhagen
 */
public interface ISettings
{ 
    /**
     * Hiermit wird zurückgegeben ob die Einstellungen aus der Einstellungsdatei geladen werden konnten.
     * Falls es noch nicht versucht wurde werden die Einstellungen geladen
     * @return true, falls es geklappt hat, sonst false.
     */
    public abstract boolean loadSettingsResult();
    
    /**
     * Hiermit wird die Einstellung key mit dem wert value gespeichert.
     * @param key Der Name der Einstellung.
     * @param value Der Inhalt der Einstellung.
     * @return true, falls alles geklappt hat, sonst false.
     */
    public abstract boolean setSetting(String key, String value);
    
    /**
     * Hiermit wird überprüft ob die Einstellung key existiert.
     * @param key der Name der Einstellung.
     * @return true, falls die Einstellung existiert, sonst false.
     */
    public abstract boolean settingexists(String key);
    
    /**
     * Hiermit wird der Wert der Einstellung key zurückgegeben, falls es einen gibt.
     * @param key Der Name der Einstellung.
     * @return Der Wert der Einstellung, falls dieser nicht existert, dann null.
     */
    public abstract String getSetting(String key);
    
    /**
     * Hiermit werden die Einstellungen die gesetzt wurden in die Einstellungsdatei gespeichert.
     * @return true, falls es geklappt hat, sonst false.
     */
    public abstract boolean saveSettings();
        
    /**
     * Hiermit wird der Pfad zu der Einstellungsdatei geändert.
     * @param path Der neue Ordnerpfad. 
     * @return true, falls alles geklappt, sonst false.
     */
    public abstract boolean setSettingsPath(String path);
}
