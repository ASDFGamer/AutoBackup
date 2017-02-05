
package hilfreich;

/**
 * Dies sind die verschiedenen Logginglevel.
 * TODO zu Enum umbauen
 * @author Christoph Wildhagen 
 */
public enum LogLevel 
{
    VERBOSE(-2), DEBUG(-1), INFO(0), WARNUNG(1),FEHLER(2),FATAL_ERROR(3),ERROR_LEVEL(2);
    
    /**
     * Dies gibt das logginglevel als Zahl an.
     */
    int level;
    
    private LogLevel(int level)
    {
        this.level = level;
    }
    
    public int getLevel()
    {
        return level;
    }
}
