package tests;


import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.ProductoMenu;
import logica.Restaurante;


class ProductoMenuTest {
	
	private Restaurante restaurante;

	@BeforeEach
	
	//Carga el restaurante con toda la información modificada (Calorías)
	
	
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
            fail( "No debería fallar: " + e.getMessage( ) );
        }
		
		
	
		
		
		
	}

	@Test
	
	void getTextoFacturaCorralTest() {
		
		setUpRestauranteModificado();
		
		ArrayList<ProductoMenu> menuProductos = restaurante.getMenuBase();
		ProductoMenu productoCorral = menuProductos.get(0);
		String stringGeneradoCorral = productoCorral.generarTextoFactura();
		
		assertTrue(stringGeneradoCorral.contains("\nNombre del producto base del menu: corral, Precio base: 14000, Calorias base: 546"));
	}
	
	void getTextoFacturaEspecialTest() {
		
		setUpRestauranteModificado();
		
		ArrayList<ProductoMenu> menuProductos = restaurante.getMenuBase();
		ProductoMenu productoEspecial = menuProductos.get(6);
		String stringGeneradoEspecial = productoEspecial.generarTextoFactura();
		
		assertTrue(stringGeneradoEspecial.contains("\nNombre del producto base del menu: especial, Precio base: 24000, Calorias base: 681"));
	}
	

}
