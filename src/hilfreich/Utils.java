
package hilfreich;

/**
 *
 * @author Christoph Wildhagen 
 */
public class Utils {
    
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
    
}
