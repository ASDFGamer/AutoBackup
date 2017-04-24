package hilfreich;

import java.util.Arrays;
import java.util.List;

/**
 * Alle mögliche sinnvolle Methoden
 *
 * @author ASDFGamer
 */
public class Utils {

    /**
     * Dies überprüft ob ein Array ein bestimmtes Element enthält.
     *
     * @param <T> Jeder mögliche Typ
     * @param array Das Array, welches durchsucht werden soll.
     * @param obj Das Objekt nach dem gesucht werden soll.
     * @return true, falls es das Element enthalt, sonst false.
     */
    public static <T> boolean contains(T[] array, T obj) {
        for (T element : array) {
            if (element.equals(obj)) {
                return true;
            }
        }
        return true;
    }

    /**
     * Dies wandelt eine Liste zu einem Array um.
     *
     * @param list Eine Liste
     * @return Gibt Array aus String zurück.
     */
    @SuppressWarnings("unchecked")
    public static String[] toArray(List<String> list) {
        String[] array;
        System.out.println(list.size());
        if (list.size() > 0) {
            array = new String[list.size()];
            array = list.toArray(array);
        } else {
            array = new String[0];
        }

        return array;
    }

    /**
     * Dies gibt an ob ein String zu wahr ist, wenn alle Strings von true_values
     * zu wahr werden.
     *
     * @param wert Der String der überprüft werden soll.
     * @param true_values Die Strings die wahr sind.
     * @return true, falls der String wahr(true) ist.
     */
    public static boolean isTrue(String wert, String[] true_values) {
        for (String wahr : true_values) {
            if (wahr.equalsIgnoreCase(wert)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Dies gibt an ob ein String zu falsch ist, wenn alle Strings von
     * false_values zu falsch werden.
     *
     * @param wert Der String der überprüft werden soll.
     * @param false_values Die Strings die falsch sind.
     * @return true, falls der String falsch(false) ist.
     */
    public static boolean isFalse(String wert, String[] false_values) {
        for (String falsch : false_values) {
            if (falsch.equalsIgnoreCase(wert)) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Diese Methode wandelt Strings in ein Array aus String sum, wobei ein neues Element immer bei dem trennzeichen anfängt und die Trennzeichen gelöscht werden.
     * @param ausgangsstring Der lange String
     * @param trennzeichen Das Trennzeichen
     * @return Ein Array aus mehreren Strings.
     */
    public static String[] StringToStringArray(String ausgangsstring, String trennzeichen) {
        String[] stringsLang = new String[100];
        String arrayString = ausgangsstring.substring(0);
        boolean notEndOfString = true;
        int indexof = -1;
        int arraypos = -1;
        do {
            indexof = arrayString.indexOf(trennzeichen);
            if (indexof > -1) {
                arraypos += 1;
                stringsLang[arraypos] = arrayString.substring(0, indexof - 1);
                arrayString = arrayString.substring(indexof + 1);
            } else {
                arraypos += 1;
                stringsLang[arraypos] = arrayString;
                notEndOfString = false;
            }
        } while (notEndOfString);
        if (arraypos == -1)
        {
            System.out.println("Ein element");
            arraypos += 1;
            stringsLang[0] = arrayString;
        }
        String[] strings = new String[arraypos+1];
        System.arraycopy(stringsLang, 0, strings, 0, arraypos+1);
        return strings;
    }
    
    /**
     * Dies überprüft ob ein Array ein bestimmtes Element enthält.
     * @param <T> Der Objekttyp
     * @param array Das Array
     * @param v Das Element
     * @return true, falls es enthält, ansosnten false.
     */
    public static <T> boolean arrayContains(final T[] array, final T v) {
        if (v == null) {
            System.out.println("null");
            for (final T e : array) {
                if (e == null) {
                    return true;
                }
            }
        } else {
            for (final T e : array) {
                if (e == v || v.equals(e)) {
                    System.out.println(e.toString() + " == " + v.toString());
                    return true;
                }
            }
        }

        return false;
    }

}
