
package autobackup.Data;

import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import hilfreich.Convertable;
import hilfreich.Utils;

/**
 * Dies ist das Objekt in dem Einstellungen gespeichert werden.
 * @author Christoph Wildhagen
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
     * Dies gibt den Wert der Einstellung als double an, falls dieser exitiert.
     */
    private Double wertDouble = null;
    
    /**
     * Dies sind alle Werte die zu 'true' umgewandelt werden können.
     */
    private static final String[] TRUE_VALUES = {"true","wahr"};
    
    /**
     * Dies sind alle Werte die zu 'false' umgewandelt werden können.
     */
    private static final String[] FALSE_VALUES = {"false","falsch"};
    
    /**
     * Der benutzte Logger
     */
    private static final Logger LOG = Logger.getLogger(EinstellungenProperty.class.getName());
    
    /**
     * Dies gibt an was der höchste erlaubte Wert der Einstellung ist. 
     */
    private Double maximalwert = null;
    
    /**
     * Dies gibt an was der niedrigste erlaubte Wert der Einstellung ist.
     */
    private Double minimalwert = null;
    
    /**
     * Diesem Konstruktor wurde kein Wert übergeben, alles wird mit Standardwerten aufgefüllt.
     */
    public EinstellungenProperty()
    {
        super();
        this.STANDARDWERT = null;
    }
  
    /**
     * Diesem Konstruktor wurde der Standardwert und der Initialwert übergeben, alles andere wird mit Standardwerten aufgefüllt.
     * @param initialValue Der Anfangs-/ Standardwert der Einstellung.

     */
    public EinstellungenProperty(String initialValue)
    {
        super(initialValue);
        this.STANDARDWERT = initialValue;
        this.init(initialValue);
    }
    
    /**
     * Diesem Konstruktor wurde der Standardwert, der Name und die Bean übergeben, alles andere wird mit Standardwerten aufgefüllt.
     * @param bean Die benutzte Bean.
     * @param name Der Name des Objekts.
     */
    public EinstellungenProperty(Object bean, String name)
    {
        super(bean, name);
        this.STANDARDWERT = null;
    }
    
    /**
     * Diesem Konstruktor wurde der Standardwert, der Name, die Bean und der Anfangswert übergeben.
     * @param bean Die benutzte Bean.
     * @param name Der Name des Objekts.
     * @param initialValue Der Anfangs-/ Standardwert der Einstellung.
     */
    public EinstellungenProperty(Object bean, String name, String initialValue)
    {
        super(bean, name, initialValue);
        this.STANDARDWERT = initialValue;
        this.init(initialValue);
    }
    
    /**
     * Diese Methode wird vom Konstruktor aufgerufen und überprüft ob der Anfangswert mehr ist als nur ein String.
     * @param initialValue Der Anfangswert der Einstellung
     * @return true, falls es kein Problem gab, ansonsten false.
     */
    private boolean init(String initialValue)
    {
        if (Convertable.toBoolean(initialValue, TRUE_VALUES, FALSE_VALUES))
        {
            this.wertBoolean = Utils.isTrue(initialValue, TRUE_VALUES);
        }
        else if (Convertable.toInt(initialValue))
        {
            this.wertInteger = Integer.parseInt(initialValue);
        }
        else if (Convertable.toDouble(initialValue))
        {
            this.wertDouble = Double.parseDouble(initialValue);
        }
        return true;
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
     * Hiermit wird der neue Wert festgelegt und falls notwendig auch der boolean, integer oder double Wert abgeändert.
     * @param newValue Der neue Wert der zugewiesen werden soll.
     */
    @Override
    public void set(String newValue) throws IllegalArgumentException
    {
        
        if (this.wertBoolean != null)
        {
            if (Convertable.toBoolean(newValue,TRUE_VALUES,FALSE_VALUES))
            {
                this.wertBoolean = Utils.isTrue(newValue, TRUE_VALUES);
            }
            else
            {
                throw new IllegalArgumentException("Der Wert " + newValue + " konnte nicht zu boolean umgewandelt werden, obwohl die Einstellung vom Typ boolean ist.");
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
                throw new IllegalArgumentException("Der Wert " + newValue + " konnte nicht zu Integer umgewandelt werden, obwohl die Einstellung vom Typ Integer ist.");
            }
        }
        
        if (this.wertDouble != null)
        {
            if (Convertable.toDouble(newValue))
            {
                this.wertDouble = Double.valueOf(newValue);
            }
            else
            {
                throw new IllegalArgumentException("Der Wert " + newValue + " konnte nicht zu Double umgewandelt werden, obwohl die Einstellung vom Typ Double ist.");
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
        if (this.wertBoolean != null)
        {
            this.wertBoolean = newValue;
            super.set(String.valueOf(newValue));
            return true;
        }
        else
        {
            LOG.warning("Diese Einstellung hat keinen Booleanwert");
            return false; 
        }
        
    }
    
    /**
     * Dies setzt den Integerwert der Einstellung und updatet den Stringwert.
     * @param newValue Der neue Wert.
     * @return true, falls alles gut gegangen ist, sonst false.
     */
    public boolean setInteger(int newValue) 
    {
        if (this.wertInteger != null)
        {
            if (this.maximalwert != null && this.maximalwert < newValue)
            {
                newValue = this.maximalwert.intValue();
            }
            if (this.minimalwert != null && this.minimalwert < newValue)
            {
                newValue = this.minimalwert.intValue();
            }
            this.wertInteger = newValue;
            super.set(String.valueOf(newValue));
            return true;
        }
        else
        {
            LOG.warning("Diese Einstellung hat keinen Integerwert");
            return false; 
        }
    }
    
    /**
     * Dies setzt den Doublewert der Einstellung und updatet den Stringwert.
     * @param newValue Der neue Wert.
     * @return true, falls alles gut gegangen ist, sonst false.
     */
    public boolean setDouble(double newValue) 
    {
        if (this.wertDouble != null)
        {
            if (this.maximalwert != null && this.maximalwert < newValue)
            {
                newValue = this.maximalwert;
            }
            if (this.minimalwert != null && this.minimalwert < newValue)
            {
                newValue = this.minimalwert;
            }
            this.wertDouble = newValue;
            super.set(String.valueOf(newValue));
            return true;
        }
        else
        {
            LOG.warning("Diese Einstellung hat keinen Doublewert");
            return false; 
        }
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
     * Dies gibt den Doublewert der Einstellung zurück oder falls dieser nicht gesetzt wurde den Integerwert oder falls nichts gesetzt wurde null.
     * @return Der Doublewert der Einstellung oder den Integerwert oder null falls nichts gesetzt wurde.
     */
    public Double getDouble()
    {
        if (this.wertDouble != null)
        {
            return this.wertDouble;
        }
        else
        {
            if(this.wertInteger != null)
            {
                return this.wertInteger.doubleValue();
            }
            else
            {
                return null;
            }
        }        
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
     * Dies gibt zurück ob die Einstellung einen Doublewert hat.
     * @return true, falls ein Doublewert existert, sonst false.
     */
    public boolean hasDoubleValue()
    {
        return this.wertDouble!= null;
    }
    
    /**
     * {@inheritDoc } 
     */
    @Override
    public String get()
    {
        return super.get();
    }
    
    /**
     * Diese Methode setzt einen maximalen Wert für die Einstellung was dafür sorgt, dass jeder Wert der höher ist automatisch zu diesem Wert wird.
     * Falls es sich bei der Einstellung nicht um eine Integer oder Double Einstellung handelt passiert nichts und es wird 'false' zurückgegeben.
     * @param maxWert Der maximale Wert der Einstellung
     * @return true, wenn das setzten des Wertes geklappt hat, false falls nicht oder falls die Einstellunng keinen maximalwert haben kann.
     */
    public boolean setMaxWert(double maxWert)
    {
        if (hasDoubleValue())
        {
            this.maximalwert = maxWert;
        }
        else if (hasIntegerValue())
        {
            this.maximalwert = maxWert;
            LOG.fine("Da es sich bei der Einstellung um eine Integereinstellung handelt wird der Doublewert in ein Integerwert umgewandelt.");
        }
        else
        {
            LOG.fine("Dies ist eine Einstellung die keinen Maximalwert haben kann.");
            return false;
        }
        
        return true;
    }
    
    /**
     * Diese Methode setzt einen maximalen Wert für die Einstellung was dafür sorgt, dass jeder Wert der höher ist automatisch zu diesem Wert wird.
     * Falls es sich bei der Einstellung nicht um eine Integer oder Double Einstellung handelt passiert nichts und es wird 'false' zurückgegeben.
     * @param maxWert Der maximale Wert der Einstellung
     * @return true, wenn das setzten des Wertes geklappt hat, false falls nicht oder falls die Einstellunng keinen maximalwert haben kann.
     */
    public boolean setMaxWert(int maxWert)
    {
        if (hasDoubleValue() || hasIntegerValue())
        {
            this.maximalwert = (double)maxWert;
        }
        else
        {
            LOG.fine("Dies ist eine Einstellung die keinen Maximalwert haben kann.");
            return false;
        }
        return true;
    }
    
    /**
     * Diese Methode setzt einen minimalen Wert für die Einstellung was dafür sorgt, dass jeder Wert der niedriger ist automatisch zu diesem Wert wird.
     * Falls es sich bei der Einstellung nicht um eine Integer oder Double Einstellung handelt passiert nichts und es wird 'false' zurückgegeben.
     * @param minWert Der minimale Wert der Einstellung
     * @return true, wenn das setzten des Wertes geklappt hat, false falls nicht oder falls die Einstellunng keinen minimalwert haben kann.
     */
    public boolean setMinWert(double minWert)
    {
        if (hasDoubleValue())
        {
            this.minimalwert = minWert;
        }
        else if (hasIntegerValue())
        {
            this.minimalwert = minWert;
            LOG.fine("Da es sich bei der Einstellung um eine Integereinstellung handelt wird der Doublewert in ein Integerwert umgewandelt.");
        }
        else
        {
            LOG.fine("Dies ist eine Einstellung die keinen Minimalwert haben kann.");
            return false;
        }
        return true;
    }
    
    /**
     * Diese Methode setzt einen minimalen Wert für die Einstellung was dafür sorgt, dass jeder Wert der niedriger ist automatisch zu diesem Wert wird.
     * Falls es sich bei der Einstellung nicht um eine Integer oder Double Einstellung handelt passiert nichts und es wird 'false' zurückgegeben.
     * @param minWert Der minimale Wert der Einstellung
     * @return true, wenn das setzten des Wertes geklappt hat, false falls nicht oder falls die Einstellunng keinen minimalwert haben kann.
     */
    public boolean setMinWert(int minWert)
    {
        if (hasDoubleValue() || hasIntegerValue())
        {
            this.minimalwert = (double)minWert;
        }
        else
        {
            LOG.fine("Dies ist eine Einstellung die keinen Minimalwert haben kann.");
            return false;
        }
        return true;
    }
    
    /**
     * Dies gibt den Maximalwert der Einstellung zurück falls einer existert. Falls keiner existert gibt es null zurück
     * @return Der maximalwert der Einstellung oder null.
     */
    public Double getMaximalwert() {
        return maximalwert;
    }
    
    /**
     * Dies gibt den Minimalwert der Einstellung zurück falls einer existert. Falls keiner existert gibt es null zurück
     * @return Der Minimalwert der Einstellung oder null.
     */
    public Double getMinimalwert() {
        return minimalwert;
    }
    
    
}
