
package hilfreich;

import java.util.List;

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
    
    public static String[] toArray(List<String> list)
    {
        String[] array = new String[list.size()];
        for (int i = 0; i< list.size(); i++)
        {
            array[i] = list.get(i);
        }
        return array;
    }
    
}
