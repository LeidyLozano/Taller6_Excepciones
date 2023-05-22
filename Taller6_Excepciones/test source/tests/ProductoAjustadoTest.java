package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Restaurante;

class ProductoAjustadoTest {
	
	private Restaurante restaurante;


	@BeforeEach
	
	void setUpRestauranteModificado() {
		
		try
        {
            restaurante = new Restaurante();
            
            String[] pathNamesIng = {"Taller6_Excepciones","data","ingredientesModificado.txt"};
        	File ingredientes = new File(String.join(File.separator, pathNamesIng));
        	
        	
        	String[] pathNamesMenu = {"Taller6_Excepciones","data","menuModificado.txt"};
        	File menu = new File(String.join(File.separator, pathNamesMenu));
        	
        	String[] pathNamesCombo = {"Taller6_Excepciones","data","combos.txt"};
        	File combos = new File(String.join(File.separator, pathNamesCombo));
        	
        	String[] pathNamesBebida = {"Taller6_Excepciones","data","bebidas.txt"};
        	File bebidas = new File(String.join(File.separator, pathNamesBebida));
        	
        	restaurante.cargarInformacionRestaurante(ingredientes, menu, combos, bebidas);
        }
        catch( Exception e )
        {
            fail( "No debería fallar: " + e.getMessage());}
        }
	
	
	
	
	@Test
	void test() {
		fail("Not yet implemented");
	}

}
