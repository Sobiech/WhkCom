package pl.vrp.enums;

public enum FileType
{
    TYP_PLIKU("Brak pliku", false, true),  
    POSITION_FILE("Pozycja", true, true),  
    DISTANCE_FILE("Dystans", false, true),  
    POSITION_DIR("Pozycja - Folder", true, true),  
    DISTANCE_DIR("Dystans - Folder", false, true),  
    RANDOMIZE("Losowo", true, false);
    
    private String name;
    private boolean measureByPosition;
    private boolean fileNeeded;
    
    private FileType(String name, boolean bool, boolean fileNeeded)
    {
        this.name = name;
        this.measureByPosition = bool;
        this.fileNeeded = fileNeeded;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public boolean measureByPosition()
    {
        return this.measureByPosition;
    }
    
    public boolean isFileNeeded()
    {
        return this.fileNeeded;
    }
    
}
