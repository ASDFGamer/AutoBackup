
package autobackup.Data;

import autobackup.settings.ISettings;
import hilfreich.FileUtil;
import static hilfreich.FileUtil.SEPERATOR;
import hilfreich.Log;
import static hilfreich.LogLevel.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Dies sind alle Einstellungen die existieren.
 * @author ASDFGamer
 */
public enum Einstellungen{
    backuptiefe(-1),
    configFile(FileUtil.getConfigFile(Const.PROGRAMM_NAME, "config.txt")),
    configFolder(FileUtil.getConfigFolder(Const.PROGRAMM_NAME)),
    dateibaumPfad(FileUtil.getConfigFolder(Const.PROGRAMM_NAME)+SEPERATOR+ "Dateibaum.txt"),
    erlaubteTypen(""),
    ftpUser(""),
    ftpPasswort(""),
    logFolder(System.getProperty("user.dir")),
    /**
     * Hergeleitet von Const#LOGEIGENSCHAFTEN
     */
    logType(0), 
    maxLogs(3),
    onlyChange(true),
    quellOrdner(""),
    verboteneTypen(""),
    versionen(2),
    writeLog(true),
    zielOrdner("");
    
    private final Log log = new Log(this.getClass().getSimpleName());
    
    private EinstellungenProperty wert;
    
    private Einstellungen(String wert)
    {
        this.wert = new EinstellungenProperty(wert);
        this.wert.addListener(einstellungenaendern);
    }
    
    private Einstellungen(int wert)
    {
        this.wert = new EinstellungenProperty(String.valueOf(wert));
        this.wert.setInt(wert);
        this.wert.addListener(einstellungenaendern);  
    }
    
    private Einstellungen(boolean wert)
    {
        this.wert = new EinstellungenProperty(String.valueOf(wert));
        this.wert.setBoolean(wert);
        this.wert.addListener(einstellungenaendern);
    }
    
    /**
     * Dies ist der Listender der {@link EinstellungenProperty#einstellunggeaendert} auf true setzt, falls sich Einstellungen geändert haben.
     */
    private final ChangeListener<Object> einstellungenaendern = (ObservableValue<? extends Object> observable, Object oldValue, Object newValue) ->
    {
        if (oldValue != null && newValue != null && !oldValue.equals(newValue))
        {
            wert.setEinstellunggeaendert(true);
        }
    }; 
    
    /**
     * Dies gibt die jeweilige EinstllungenProperty für die Einstellung zurück.
     * @return Die zuzgehörige EinstellungenProperty.
     */
    public EinstellungenProperty getWert()
    {
        return this.wert;
    }
    
    /**
     * Hiermit werden die Werte für alle Einstellungen geladen und es muss deshalb nur einmal aufgerufen werden.
     * @param config Das Objekt mit dem die Einstellungen geladen werden können.
     * @return true, falls es keinen Fehler gab, sonst false.
     */
    public boolean load(ISettings config)
    {
        log.write("Es wird angefangen die Einstellungen zu landen",DEBUG);
        if (config == null)
        {
            log.write("Es wurde ein leeres ISettings Objekt übergeben",FEHLER);
            return false;
        }
        for (Einstellungen einstellung : Einstellungen.values())
        {
            
            if (config.settingexists(einstellung.toString()))
            {
                log.write("Die Einstellung " + einstellung.toString() + " wird geladen.",DEBUG);
                einstellung.getWert().removeListener(einstellungenaendern);
                einstellung.getWert().set(config.getSetting(einstellung.toString()));
                einstellung.getWert().addListener(einstellungenaendern);
            }
            else
            {
                log.write("Die Einstellung " + einstellung.toString() + " existiert nicht.",WARNUNG);
            }
        }
        return true;
    }
} 