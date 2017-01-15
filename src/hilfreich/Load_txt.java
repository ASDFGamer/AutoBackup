package hilfreich;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Dies ist die Standardmethode, um Einstellungsdateien zu laden, die mit {@link Save_txt} erstellt wurden.
 * 
 * @author wildh
 * @version 0.2
 */
public class Load_txt{
	
	/**
	 * Der Verweis auf Load_Save_txt
	 * @since 0.2
	 */
	private Load_Save_txt obj;
	
	/**
	 * Dies ist der Standardspeicherort.
	 * @since 0.1
	 */
	private static String std_File_Path="save.txt";
	
	/**
	 * Dies ist der lokale Speicherort, Standardmäßig wird der Standardspeicherort ({@link Save_txt#std_File_Path}).
	 * @since 0.1
	 */
	private String file_Path=std_File_Path;
	
	/**
	 * Dies ist die Datei in der die Einstellungen sind.
	 */
	//private BufferedReader einstellungen=null;
	
	/**
	 * Dies zeigt, wie viele Zeilen die Datei hat, die eingelesen wurde.
	 */
	private int speicher_zeilen;
	
	private Log log=new Log(super.getClass().getName());
	
	
	// ---Konstruktoren---
	
	/**
	 * Dies ist der Standardkonstruktor.
	 * Von außen nur über {@link Load_Save_txt#load} zu erreichen.
	 */
 	 Load_txt(Load_Save_txt OBJ){
 		obj=OBJ;
		init();
	}
	
	/**
	 * Mit diesem Konstruktor kann der Ort der Einstellungsdatei übergeben werden.
	 * Von außen nur über {@link Load_Save_txt#load} zu erreichen.
	 * @param Path Ort der Einstellungsdatei.
	 */
	 Load_txt(Load_Save_txt OBJ,String Path){
		obj=OBJ;
		file_Path=Path;
		init();
	}
	
	// ---Getter---
	
	/**
	 * Dies gibt den lokalen Speicherort der geladen wird zurück.
	 * @return der lokalen Speicherort der geladen wird.
	 */
	public String get_File_Path(){
		return file_Path;
	}
	
	/**
	 * Dies gibt den Standardspeicherort der geladen wird zurück.
	 * @return der Standardspeicherort der geladen wird.
	 */
	public static String get_std_File_Path(){
		return std_File_Path;
	}
	// ---Setter---
	
	/**
	 * Dies legt den Pfad fest an dem nach der Speicherdatei gesucht wird.
	 * @param Path Der Pfad zu Datei
	 * @return true, falls alles gut gin, sonst false.
	 */
	public boolean set_File_Path(String Path){
		file_Path=Path;
		return true;
	}
	
	/**
	 * Dies legt den Pfad fest an dem standardmäßig nach der Speicherdatei gesucht wird.
	 * @param Path Der Pfad zu Datei
	 * @return true, falls alles gut gin, sonst false.
	 */
	public static boolean set_std_File_Path(String Path){
		std_File_Path=Path;
		return true;
	}
	// ---andere Methoden---
	
	/**
	 * Dies ist die Methode die immer am Anfang geladen wird.
	 * @return true, falls alles gut ging, sonst false.
	 */
	private boolean init(){
		log.write("Versucht die Eintellungen zu laden");
		//Öffnet die Datei
		try (
			InputStream stream = new FileInputStream(file_Path);
			InputStreamReader stream_reader = new InputStreamReader(stream);
			BufferedReader einstellungen = new BufferedReader(stream_reader);
		) {	
			Integer i=0;
			String zeile;
			while((zeile = einstellungen.readLine()) != null){
				log.write(zeile);
				obj.speicher[i]=zeile;
				i=i+1;
			}
			speicher_zeilen=i;
			
			//Schließt die Datei
			einstellungen.close();
		}
		catch ( IOException e) {
			//e.printStackTrace();
			log.write("Die Einstellungsdatei kann nicht geöffnet oder geschlossen werden.",2);
			return false;
		}
		
		//TODO Schließt die Datei. (geht nicht da der BufferedReader in try initialisiert werden muss und deshalb außerhalb des geltungbereiches ist.
		/*try {
			einstellungen.;
		} catch (IOException | NullPointerException e) {
			//e.printStackTrace();
			log.write("Die Datei konnte nicht geschlossen werden."+ e.getClass().getName(),2);
			//return false;
		}*/
		var_init();
		return true;
	}
	
	private boolean var_init(){
		for (int i=0;i<speicher_zeilen;i++){
			obj.names[i]=obj.speicher[i].substring(0,obj.speicher[i].indexOf(":"));
			obj.values[i]=obj.speicher[i].substring(obj.speicher[i].indexOf(":")+1);
			//log.write(values[i]);
		}
		return true;
	}
	
	/**
	 * Dies gibt den Wert zu der angegebenen Eigenschaft als String aus.
	 * @param name Der Name der Eigenschft die geladen werden soll.
	 * @return Der Wert der Eigenschaft als String und wenn die Eigenschaft nicht geladen werden kann null.
	 */
	public String load_str(String name){
		if (Arrays.asList(obj.names).contains(name)){
			return obj.values[Arrays.asList(obj.names).indexOf(name)];
		} else {
			log.write(name + " existiert nicht", 1);
			return null;
		}
	}
	
	/**
	 * Dies gibt den Wert zu der angegebenen Eigenschaft als Integer aus.
	 * @param name Der Name der Eigenschft die geladen werden soll.
	 * @return Der Wert der Eigenschaft als Int und wenn die Eigenschaft nicht geladen oder umgewandelt werden kann Integer.MIN_VALUE.
	 */
	public int load_int(String name){
		if (Arrays.asList(obj.names).contains(name)){
			try {
				return Integer.parseInt(obj.values[Arrays.asList(obj.names).indexOf(name)]);
			} catch (NullPointerException e) {
				log.write(name + " ist kein Int", -1);
				return Integer.MIN_VALUE;
				
			}
			
		} else {
			log.write(name + " existiert nicht", -1);
			return Integer.MIN_VALUE;
		}
	}
	
	/**
	 * Dies gibt den Wert zu der angegebenen Eigenschaft als String aus.
	 * @param name Der Name der Eigenschft die geladen werden soll.
	 * @return Der Wert der Eigenschaft als String und wenn die Eigenschaft nicht geladen werden kann false.
	 * TODO/achtung: False kann heißen, das der WErt geladen wurde und 'false' ist, oder dass es nicht hingehauen hat.
	 */
	public boolean load_bool(String name){
		if (Arrays.asList(obj.names).contains(name)){
			return Boolean.parseBoolean(obj.values[Arrays.asList(obj.names).indexOf(name)]);
		} else {
			log.write(name + " existiert nicht", 1);
			return false;
		}
	}
}
