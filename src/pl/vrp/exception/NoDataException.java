package pl.vrp.exception;

public class NoDataException extends Exception {
	
    private static final long serialVersionUID = -4072712497283614744L;
    
    public NoDataException()
    {
        super("Wprowadzone dane nie są poprawne, uzupełnij wszystkie pola i uruchom aplikacje");
    }
    
    public NoDataException(String str)
    {
        super(str);
    }
    
    public NoDataException(String str, boolean bool)
    {
        super("Wprowadzone dane nie są poprawne, wybierz z listy ( " + str + " ) i zatwierdź zmianę.");
    }
}
