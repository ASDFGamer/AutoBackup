package hilfreich;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Dies soll dafür verwendet werden, um Einstellungen in eine Datei zu Speichern.
 * Sollte nur bei Einstellungen verwendet werden und nicht z.B. bei SaveFiles, da es im Klartext mit Erklärung in der Datei ist.
 * Läuft, kann aber noch verbessert werden.
 * Es sollte besser mit dem Javainternen Properties gearbeitet werden.
 * @author wildh
 * @version 0.2 
 * @deprecated 
 */
public class Save_txt{
	
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
	 * Stellt ein, ob standarmäßig Infos zu den Werten angezeigt werden oder nicht (Standardmäßig ist es {@value Save_txt#std_Infos}
	 * @since 0.1
	 */
	private static Boolean std_Infos=true;
	
	/**
	 * Stellt ein, ob lokal Infos zu den Einstellungen angezeigt werden, standarmäßig ist es wie bei {@link Save_txt#std_Infos}.
	 * @since 0.1
	 */
	private  Boolean infos=std_Infos;
	
	/**
	 * Die Datei in der die Einstellungen sind.
	 * @since 0.1
	 */
	private FileWriter einstellungs_file;
	
	/**
	 * Dies ist der Log
	 * @since 0.1
	 */
	Log log = new Log(super.getClass().getName());
	
	
	//  --- Konstruktoren ---
	
	/**
	 * Der Standardkonstruktor.
	 * Von außen nur über {@link Load_Save_txt#load} zu erreichen.
	 * @since 0.1
	 */
	 Save_txt(Load_Save_txt OBJ){
		obj=OBJ;
		init();
	}
	
	/**
	 * Der Standardkonstruktor mit einer Pfadangabe.
	 * Von außen nur über {@link Load_Save_txt#load} zu erreichen.
	 * @since 0.2
	 */
	 Save_txt(Load_Save_txt OBJ,String pfad){
		obj=OBJ;
		file_Path = pfad;
		init();
	}
	
	// --- Getter ---
	
	/**
	 * Gibt den lokalen Speicherpfad zurück.
	 * @return Gibt den lokalen Speicherpfad zurück.
	 * @since 0.1
	 */
	public String get_File_Path(){
		return this.file_Path;
	}
	
	/**
	 * Gibt zurück, ob lokal Erklärungen in der Datei angezeigt werden, oder nicht.
	 * @return Gibt zurück, ob lokal Erklärungen in der Datei angezeigt werden, oder nicht.
	 * @since 0.1
	 */
	public boolean get_Infos(){
		return this.infos;
	}
	
	/**
	 * Gibt den Standrdspeicherpfad zurück.
	 * @return Gibt den Standrdspeicherpfad zurück.
	 * @since 0.1
	 */
	public static String get_std_File_Path(){
		return std_File_Path;
	}
	
	/**
	 * Gibt zurück, ob standarmäßig Erklärungen in der Datei angezeigt werden, oder nicht.
	 * @return Gibt zurück, ob standarmäßig Erklärungen in der Datei angezeigt werden, oder nicht.
	 * @since 0.1
	 */
	public static boolean get_std_Infos(){
		return std_Infos;
	}
	
	// --- Setter ---
	
	/**
	 * Dies setzt den neuen lokalen Speicherort.
	 * @param pfad Der lokale Speicherort.
	 * @return true, falls alles funktioniert hat, sonst false.
	 * @since 0.1
	 */
	public boolean set_File_Path(String pfad){
		this.file_Path=pfad;
		return true;
	}
	
	/**
	 * Dies setzt die neuen lokalen Einstellungen zu den {@link Save_txt#infos}.
	 * @param pfad Der lokale Speicherort.
	 * @return true, falls alles funktioniert hat, sonst false.
	 * @since 0.1
	 */
	public boolean set_Infos(boolean info){
		this.infos=info;
		return true;
	}
	
	/**
	 * Dies setzt den neuen Standardspeicherort.
	 * @param pfad Der lokale Speicherort.
	 * @return true, falls alles funktioniert hat, sonst false.
	 * @since 0.1
	 */
	public static boolean set_std_File_Path(String pfad){
		std_File_Path=pfad;
		return true;
	}
	
	/**
	 * Dies setzt die neuen standard Einstellungen zu den {@link Save_txt#infos}.
	 * @param pfad Der lokale Speicherort.
	 * @return true, falls alles funktioniert hat, sonst false.
	 * @since 0.1
	 */
	public static boolean set_std_Infos(boolean info){
		std_Infos=info;
		return true;
	}
	// --- andere Methoden ---
	
	/**
	 * Diese Methode wird bei der initialisierung aufgerufen.
	 * @return true, falls alles geklappt hat, sonst false.
	 * @since 0.1
	 */
 	private boolean init(){
 		return true;
	}
	
 	/**
 	 * Dies ist die Methode mit der die Einstellungen gespeichert werden sollen.
 	 * @param Name Der Name der Eigenschaft, die gespeichert werden soll.
 	 * @param Wert Der Wert der Eigenschaft, die gespeichert werden soll.
 	 * @return true, falls alles geklappt hat, sonst false.
 	 * @since 0.1
 	 */
 	public boolean save(String Name, boolean Wert){
 		save_all(Name,Boolean.toString(Wert));
 		return true;
 	}
 	
 	/**
 	 * Dies ist die Methode mit der die Einstellungen gespeichert werden sollen.
 	 * @param Name Der Name der Eigenschaft, die gespeichert werden soll.
 	 * @param Wert Der Wert der Eigenschaft, die gespeichert werden soll.
 	 * @return true, falls alles geklappt hat, sonst false.
 	 * @since 0.1
 	 */
 	public boolean save(String Name, String Wert){
 		save_all(Name,Wert);
 		return true;
 	}
 	
 	/**
 	 * Dies ist die Methode mit der die Einstellungen gespeichert werden sollen.
 	 * @param Name Der Name der Eigenschaft, die gespeichert werden soll.
 	 * @param Wert Der Wert der Eigenschaft, die gespeichert werden soll.
 	 * @return true, falls alles geklappt hat, sonst false.
 	 * @since 0.1
 	 */
 	public boolean save(String Name, int Wert){
 		save_all(Name,Integer.toString(Wert));
 		return true;
 	}
 	
 	/*
 	 * Dies ist die Methode die die Einstellungen tatsächlich in eine Datei schreibt.
 	 * @param text Der Text der in der Datei stehen soll.
 	 * @return true, falls alles geklappt hat, sonst false.
 	 * @since 0.1
 	 * @deprecated
 	 */
 	/*private boolean write(String text){
 		try {		
			einstellungs_file.write(text);
			einstellungs_file.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			log.write("Es wurde nicht erfolgreich geschrieben",1);
			return false;
		}
 	}*/
 	
 	/**
 	 * Dies ist die Methode die die Einstellungen tatsächlich in eine Datei schreibt.
 	 * @param save Dies sind die Werte die gespeichert werden sollen als Array of string 
 	 * @return true, falls alles geklappt hat, sonst false.
 	 * @since 0.1
 	 */
 	private boolean write(String[] save,int length){
 		log.write("Einstellungen werden geschrieben",-1);
 		int i=0;
 		try {
 			einstellungs_file= new FileWriter(file_Path);
 			do{
 			einstellungs_file.write(save[i]+System.getProperty("line.separator"));
 			i++;
 			}
 			while((i<=length) && (Arrays.asList(save).get(i)!=null));
 			einstellungs_file.flush();
 		}catch (IOException e) {
			e.printStackTrace();
			log.write("Es wurde nicht erfolgreich geschrieben",1);
			return false;
		}
 		return true;
 	}
 	
 	/**
 	 * Dies schließt die Datei. 
 	 * Immer am Ende aufrufen.
 	 * @return true, falls alles geklappt hat, sonst false.
 	 * @since 0.1
 	 * TODO mehrfacheinträge verhindern
 	 */
  	public boolean close(){
 		try {
			einstellungs_file.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.write("Die einstellungs Datei wurde nicht geschlossen",1);
		} catch (NullPointerException e) {
			log.write("Die einstellungsdatei war wahrscheinlich nicht offen.");
		}
 		return true;
 	}
 	
  	/**
  	 * Dies ist die Methode, die von den Verschiedenen Save Methoden aufgefufen wird und die tatsächlich für das Speichern zuständig ist.
  	 * @param Name Der Name der Eigenschaft
  	 * @param Wert Der Wert der Eigenschaft
  	 * @return true, falls alles gut ging sonst false
  	 */
 	private boolean save_all(String Name, String Wert){
 		boolean changed=false;
 		if(Arrays.asList(obj.names).contains(Name)){
 			log.write(Name + " ist vorhanden",-1);
 			if (!obj.values[Arrays.asList(obj.names).indexOf(Name)].equals(Wert)){
 				changed=true;
 				obj.values[Arrays.asList(obj.names).indexOf(Name)]=Wert;
 				log.write(Name + " wurde auf " + Wert+ " geändert",-1);
 			}	
 		}else{
 			changed=true;
 			if (obj.names[0]!=null){
 				obj.values[Arrays.asList(obj.names).indexOf(null)]=Wert;
 				obj.names[Arrays.asList(obj.names).indexOf(null)]=Name;
 				// iein fehler Arrays.asList(names).add(Name); //TODO mit add(Name,index) position festsetzen
 				//Arrays.asList(values).add(Wert); // siehe oben
 				log.write(Name+":"+Wert + " wurde zum Speicher hinzugefügt");
 			} else {
 				log.write("Die Savefile war leer", 1);
 				obj.names[0]=Name; //TODO mit add(Name,index) position festsetzen
 				obj.values[0]=Wert; // siehe oben
 				log.write(Name+":"+Wert + " wurde zum Speicher hinzugefügt");
 			}
 		}
 		if (changed){
 			String[] save = new String[100];
 			for (int i=0; i<=Arrays.asList(obj.names).indexOf(Name); i++ ){
 				save[i]=obj.names[i]+':'+obj.values[i]; //TODO Hier könnten hilfen hinzugefügt werden.
 			}
 			write(save,Arrays.asList(obj.names).indexOf(Name));
 		}
 		
 		return true;
 	}
}
