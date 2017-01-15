package hilfreich;

/**
 * Dies soll als Überklasse für die Load und Save Klassen sein.
 * Dise Klasse wird benutzt, um auf Objekte von save_txt und load_txt zuzugreifen.
 * Hier sollen Sachen gespeichert werden, die für beide Klassen wichtig sind.
 * @author wildh
 * @version 0.5
 */
public class Load_Save_txt {
	
	//Variablen
	protected boolean file_is_loaded=false;
	/**
	 * Dies ist der Speicher in der alle Einstellugen gespeichert werden, wenn sie ausgelesen wurden.
	 * @since 0.1
	 */
	protected String[] speicher = new String[100];
	
	/**
	 * Hier stehen die Namen der Variablen, die gespeichert wurden.
	 * @since 0.1
	 */
	protected String[] names = new String[100];
	
	/**
	 * Hier stehen die Werte der Variablen, der Platz stimmt mit {@link Load_Save_txt#names} überein.
	 * @since 0.1
	 */
	protected String[] values = new String[100];
	
	/**
	 * Dies ist das Load_txt Objekt.
	 * @since 0.5
	 */
	public Load_txt load;
	
	/**
	 * Dies ist das Save_txt Objekt.
	 * @since 0.5
	 */
	public Save_txt save;
	
	// Konstruktoren
	/**
	 * Dies ist der Standardkonstruktor
	 * @since 0.5
	 */
	public Load_Save_txt(){
		save = new Save_txt(this);
		load = new Load_txt(this);
		
	}
	
	/**
	 * Dies ist der Standardkonstruktor mit Pfadangabe.
	 * @param pfad Der Pfad der Einstellungsdatei.
	 * @since 0.5
	 */
	public Load_Save_txt(String pfad){
		save = new Save_txt(this,pfad);
		load = new Load_txt(this,pfad);
		
	}

}
