package pl.vrp.exception;

public class SliderValueException extends Exception{

	private static final long serialVersionUID = -8109605898188536820L;

	private static String message = "Wprowadzona wartosc prawdopodobienstwa musi byc wieksza od 0. Uzupelnij dane w polu dla ' ";
	
	public SliderValueException(String sliderName){
		super(message + sliderName + " '.");
	}
	
}
