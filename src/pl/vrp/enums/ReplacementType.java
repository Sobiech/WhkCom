package pl.vrp.enums;

public enum ReplacementType {
	
    TYP_GEMERACJI("Typ podstawiania"),  REPLACEMENT("Replacement");
    
    private String name;
    
    private ReplacementType(String name)
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
