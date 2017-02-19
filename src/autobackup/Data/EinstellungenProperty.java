
package autobackup.Data;

import hilfreich.Convertable;
import hilfreich.Log;
import hilfreich.Utils;
import javafx.beans.property.SimpleStringProperty;

/**
 * Dies ist das Objekt in dem Einstellungen gespeichert werden.
 * @author ASDFGamer
 */
public class EinstellungenProperty extends SimpleStringProperty{
    
    /**
     * Dies ist der Standardwert der Einstellung als String.
     */
    private final String STANDARDWERT;
    
    /**
     * Dies gibt an ob schon eine Einstellung geändert wurde.
     */
    private static boolean einstellunggeaendert = false;
    
    /**
     * Dies gibt den Wert der Einstellung als boolean an, falls dieser existiert.
     */
    private Boolean wertBoolean = null;
    
    /**
     * Dies gibt den Wert der Einstellung als int an, falls dieser exitiert.
     */
    private Integer wertInteger = null;
    
    /**
     * Mein Log
     */
    private final Log log = new Log(this.getClass().getSimpleName());
    
    /**
     * Diesem Konstruktor wurde kein Wert übergeben, alles wird mit Standardwerten aufgefüllt.
     */
    public EinstellungenProperty()
    {
        super();
        this.STANDARDWERT = null;
    }
    
    /**
     * Diesem Konstruktor wurde nur der Standardwert übergeben, alles andere wird mit Standardwerten aufgefüllt.
     * @param STANDARDWERT Der Wert der Einstellung am Anfang.
     */
    public EinstellungenProperty(String STANDARDWERT)
    {
        super(STANDARDWERT);
        this.STANDARDWERT = STANDARDWERT;
    }
    
    /**
     * Diesem Konstruktor wurde der Standardwert und der Initialwert übergeben, alles andere wird mit Standardwerten aufgefüllt.
     * @param initialValue Der Wert der Einstellung am Anfang.
     * @param STANDARDWERT Der Standardmäßige Wert der Einstellung.
     */
    public EinstellungenProperty(String initialValue, String STANDARDWERT)
    {
        super(initialValue);
        this.STANDARDWERT = STANDARDWERT;
    }
    
    /**
     * Diesem Konstruktor wurde der Standardwert, der Name und die Bean übergeben, alles andere wird mit Standardwerten aufgefüllt.
     * @param bean Die benutzte Bean.
     * @param name Der Name des Objekts.
     * @param STANDARDWERT Der Standardwert der Einstellung.
     */
    public EinstellungenProperty(Object bean, String name, String STANDARDWERT)
    {
        super(bean, name);
        this.STANDARDWERT = STANDARDWERT;
    }
    
    /**
     * Diesem Konstruktor wurde der Standardwert, der Name, die Bean und der Anfangswert übergeben.
     * @param bean Die benutzte Bean.
     * @param name Der Name des Objekts.
     * @param STANDARDWERT Der Standardwert der Einstellung.
     */
    public EinstellungenProperty(Object bean, String name, String initialValue, String STANDARDWERT)
    {
        super(bean, name, initialValue);
        this.STANDARDWERT = STANDARDWERT;
    }
    
    /**
     * Dies gibt den Standardwert zurück.
     * @return Der Standardwert.
     */
    public String getStandardwert()
    {
        return STANDARDWERT;
    }
    
    /**
     * Dies gibt zurück ob sich irgendeine Einstellung geändert hat.
     * @return true, falls sich eine Einstellung geändert hat, sonst false.
     */
    public boolean getEinstellunggeaendert()
    {
        return einstellunggeaendert;
    }
    
    /**
     * Hiermit kann festgelegt werden dass sich eine Einstellung geändert hat.
     * Achtung: es ist nicht möglich diesen Wert von true auf false zu ändern.
     * @param einstellunggeaendert true, wenn sich eine Einstellung geändert hat.
     * @return true, falls alles geklappt hat, sonst false.
     */
    public boolean setEinstellunggeaendert(boolean einstellunggeaendert)
    {
        if (einstellunggeaendert)
        {
            EinstellungenProperty.einstellunggeaendert = einstellunggeaendert;
            return true;
        }
        return false;
    }
    
    /**
     * Hiermit wird der neue Wert festgelegt und falls notwendig auch der boolean oder integer Wert abgeändert.
     * @param newValue Der neue Wert der zugewiesen werden soll.
     */
    @Override
    public void set(String newValue) 
    {
        
        if (this.wertBoolean != null)
        {
            if (Convertable.toBoolean(newValue,Const.TRUE_VALUES,Const.FALSE_VALUES))
            {
                this.wertBoolean = Utils.isTrue(newValue, Const.TRUE_VALUES);
            }
            else
            {
                throw new java.lang.RuntimeException("Der Wert " + newValue + " konnte nicht zu boolean umgewandelt werden, obwohl die Einstellung vom Typ boolean ist.");
            }
        }
        
        
        if (this.wertInteger != null)
        {
            if (Convertable.toInt(newValue))
            {
                this.wertInteger = Integer.parseInt(newValue);
            }
            else
            {
                throw new java.lang.RuntimeException("Der Wert " + newValue + " konnte nicht zu Integer umgewandelt werden, obwohl die Einstellung vom Typ Integer ist.");
            }
        }
        
        
        super.set(newValue);
    }
    
    /**
     * Dies setzt den Booleanwert der Einstellung und updatet den Stringwert.
     * @param newValue Der neue Wert.
     * @return true, falls alles gut gegangen ist, sonst false.
     */
    public boolean setBoolean(boolean newValue) 
    {
        if (this.wertInteger != null)
        {
            log.write("Dies ist schon eine Einstellung mit einem Integerwert.");
            return false;
        }
        this.wertBoolean = newValue;
        super.set(String.valueOf(newValue));
        return true;
    }
    
    /**
     * Dies setzt den Integerwert der Einstellung und updatet den Stringwert.
     * @param newValue Der neue Wert.
     * @return true, falls alles gut gegangen ist, sonst false.
     */
    public boolean setInt(int newValue) 
    {
        if (this.wertBoolean != null)
        {
            log.write("Dies ist schon eine Einstellung mit einem Booleanwert.");
            return false;
        }
        this.wertInteger = newValue;
        super.set(String.valueOf(newValue));
        return true;
    }
    
    /**
     * Dies gibt den booleanwert der Einstellung zurück
     * @return Der booleanwert der Einstellung oder null falls sie nicht gesetzt wurde.
     */
    public Boolean getBoolean()
    {
        return this.wertBoolean;
    }
    
    /**
     * Dies gibt den Integerwert der Einstellung zurück
     * @return Der Integerwert der Einstellung oder null falls sie nicht gesetzt wurde.
     */
    public Integer getInteger()
    {
        return this.wertInteger;
    }
    
    /**
     * Dies gibt zurück ob die Einstellung einen booleanwert hat.
     * @return true, falls ein booleanwert existert, sonst false.
     */
    public boolean hasBooleanValue()
    {
        return this.wertBoolean!= null;
    }
    
    /**
     * Dies gibt zurück ob die Einstellung einen Integerwert hat.
     * @return true, falls ein Intergerwert existert, sonst false.
     */
    public boolean hasIntegerValue()
    {
        return this.wertInteger!= null;
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public String get()
    {
        return super.get();
    }
}
