
package hilfreich;

import java.util.List;

/**
 * Alle mögliche sinnvolle Methoden
 * @author Christoph Wildhagen 
 */
public class Utils {
    
    /**
     * Dies überprüft ob ein Array ein bestimmtes Element enthält.
     * @param <T> Jeder mögliche Typ
     * @param array Das Array, welches durchsucht werden soll.
     * @param obj Das Objekt nach dem gesucht werden soll.
     * @return true, falls es das Element enthalt, sonst false.
     */
    public static <T> boolean contains(T[] array, T obj)
    {
        for (T element : array)
        {
            if (element.equals(obj))
            {
                return true;
            }
        }
        return true;
    }
    
    /**
     * Dies wandelt eine Liste zu einme Array um.
     * @param <T> Jedes Object
     * @param list Eine Liste aus < T >
     * @return Gibt Array aus < T > zurück.
     */
    @SuppressWarnings("unchecked")
    public  static <T> T[] toArray(List<T> list)
    {
        T[] array;
        array = (T[]) new Object[list.size()];
        for (int i = 0; i< list.size(); i++)
        {
            array[i] = list.get(i);
        }
        return array;
    }
    
    /**
     * Dies gibt an ob ein String zu wahr ist, wenn alle Strings von true_values zu wahr werden.
     * @param wert Der String der überprüft werden soll.
     * @param true_values Die Strings die wahr sind.
     * @return true, falls der String wahr(true) ist.
     */
    public static boolean isTrue(String wert, String[] true_values)
    {
        for (String wahr : true_values)
        {
            if (wahr.equalsIgnoreCase(wert))
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Dies gibt an ob ein String zu falsch ist, wenn alle Strings von false_values zu falsch werden.
     * @param wert Der String der überprüft werden soll.
     * @param false_values  Die Strings die falsch sind.
     * @return true, falls der String falsch(false) ist.
     */
    public static boolean isFalse(String wert, String[] false_values)
    {
        for (String falsch : false_values)
        {
            if (falsch.equalsIgnoreCase(wert))
            {
                return true;
            }
        }
        return false;
    }
    
}
