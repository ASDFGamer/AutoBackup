
package hilfreich;

/**
 * @author Christoph Wildhagen 
 */
public class Convertable {
    
    /**
     * Dies zeigt, ob ein String zu Byte konvertiert werden kann.
     * @param zahl Der String
     * @return true, wenn möglich, sonst false.
     */
    static public boolean toByte(String zahl)
    {
        try
        {
            Byte.parseByte(zahl);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Dies zeigt, ob ein String zu Short konvertiert werden kann.
     * @param zahl Der String
     * @return true, wenn möglich, sonst false.
     */
    static public boolean toShort(String zahl)
    {
        try
        {
            Short.parseShort(zahl);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Dies zeigt, ob ein String zu Int konvertiert werden kann.
     * @param zahl Der String
     * @return true, wenn möglich, sonst false.
     */
    static public boolean toInt(String zahl)
    {
        try
        {
            Integer.parseInt(zahl);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Dies zeigt, ob ein String zu Long konvertiert werden kann.
     * @param zahl Der String
     * @return true, wenn möglich, sonst false.
     */
    static public boolean toLong(String zahl)
    {
        try
        {
            Long.parseLong(zahl);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Dies zeigt, ob ein String zu Float konvertiert werden kann.
     * @param zahl Der String
     * @return true, wenn möglich, sonst false.
     */
    static public boolean toFloat(String zahl)
    {
        try
        {
            Float.parseFloat(zahl);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }
    
    /**
     * Dies zeigt, ob ein String zu Double konvertiert werden kann.
     * @param zahl Der String
     * @return true, wenn möglich, sonst false.
     */
    static public boolean toDouble(String zahl)
    {
        try
        {
            Double.parseDouble(zahl);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }
    
    
    
    
}
