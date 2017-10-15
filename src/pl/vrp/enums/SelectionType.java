package pl.vrp.enums;

public enum SelectionType {
    TYP_SELEKCJI("Typ selekcji"),  TOURNAMENT("Selekcja turniejowa");
    
    private String name;
    
    private SelectionType(String name)
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
