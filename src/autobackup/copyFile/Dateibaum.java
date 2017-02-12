/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autobackup.copyFile;

import hilfreich.Log;
import static hilfreich.LogLevel.WARNUNG;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedList;
import java.io.File;
import java.util.Properties;

/**
 *
 * @author WILDHACH
 */
public class Dateibaum {
    
    private final ISichern sichern;
    
    private Properties dateibaum = new Properties();
    
    private final File dateibaumpfad;
    
    private final Log log = new Log(this.getClass().getSimpleName());
    
    public Dateibaum(ISichern sichern, File dateibaumpfad)
    {
        this.sichern=sichern;
        this.dateibaumpfad =dateibaumpfad;
    }
    
    /**
     * Überprüft den Dateibaum aus der Datei auf änderungen in der jetzigen Dateistruktur. 
     * Die Änderungen werden in {@link Backup#neueDateien gespeichert.
     * @return true, wenn die überprüfung geklappt hat, sonst false.
     */
    public boolean checkDateibaum()
    {
        //Laden des Dateibaums
        try 
        {
            FileInputStream in = new FileInputStream(this.dateibaumpfad);
            this.dateibaum.load(in);
            in.close();
        }
        catch (IOException e)
        {
            log.write("Die Datei mit dem Dateibaum konnte nicht geöffnet werden.");
            return false;
        }
        catch (Exception e)
        {
            return false;
        }
        this.sichern.setDateibaum(dateibaum);
        //Vergleichen der Dateien
        
        return true;
    }
    
    /**
     * Dies erstellt den Dateibaum für den Ordner
     * @return true, falls alles geklappt hat, sonst false.
     */
    public boolean createDateibaum() //TODO Dateibaum leeren,TODO UTF-8
    {
        log.write("Der Pfad zum Dateibaum: " + this.dateibaumpfad);
        LinkedList<Path> neueDateien = this.sichern.getNeueDateien();
        if (this.dateibaum.isEmpty())
        {
            log.write("Es existiert noch kein Dateibaum, ein neuer wird angelegt.");
            LinkedList<Path> dateien = (LinkedList<Path>)neueDateien.clone();
            Path element;
            while (!dateien.isEmpty())
            {
                element = dateien.remove();
                dateibaum.put(element.toString(), String.valueOf(element.toFile().lastModified()));
            }
        }
        else
        {
            log.write("Es werden die Element die sich geneuert haben im Dateibaum geändert.");
            for (Path neueDatei : neueDateien)
            {
                if (this.dateibaum.contains(neueDatei))
                {
                    this.dateibaum.replace(neueDatei.toString(), neueDatei.toFile().lastModified());
                }
                else
                {
                    this.dateibaum.put(neueDatei.toString(), String.valueOf(neueDatei.toFile().lastModified()));
                }
            }
        }
        try 
        {
            dateibaum.store(new BufferedWriter(new FileWriter(this.dateibaumpfad)),"");
        } catch (IOException ex) {
            log.write("Der Dateibaum konnte nicht gesichert werden.",WARNUNG);
        }
        return true;
    }
}
