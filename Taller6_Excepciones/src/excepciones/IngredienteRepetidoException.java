package excepciones;

import logica.Ingrediente;

@SuppressWarnings("serial")

public class IngredienteRepetidoException extends HamburguesaException  {
	
	Ingrediente ingredienteRepetido;

	public IngredienteRepetidoException(String message) {
		
		super(message);
	}

	
}
