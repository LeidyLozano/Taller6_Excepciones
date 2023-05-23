package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import logica.Combo;
import logica.Producto;
import logica.Restaurante;

class ComboTest {
	
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
	
	void agregarItemComboCorralTest() {
		
		setUpRestauranteModificado();
		
		ArrayList<Combo> opcionesCombos = restaurante.getCombos();
		Combo comboCorral = opcionesCombos.get(0);
		
		//No sabía de donde sacar los productos, así que los saqué de otro combo.
		
		Combo comboEspecial = opcionesCombos.get(4);
		
		ArrayList<Producto> productosEspecial = comboEspecial.getProductos();
		Producto hamburguesaEspecial = productosEspecial.get(0);
		
		comboCorral.agregarItemACombo(hamburguesaEspecial);
		
		ArrayList<Producto> productosCorral = comboCorral.getProductos();
		assertEquals(4,productosCorral.size(),"El combo de la corral ahora debería tener cuatro elementos");	
		
	}
	@Test
	
	void generarTextoFacturaCorralTest() {
		
		setUpRestauranteModificado();
		
		ArrayList<Combo> opcionesCombos = restaurante.getCombos();
		Combo comboCorral = opcionesCombos.get(0);
		
		String textoGenerado = comboCorral.generarTextoFactura();
		
		//ya que la función usa prodcuto y no producto menu y producto es una interfaz realmente no sé como implementar
		//esta prueba :(
		
		assertTrue(textoGenerado.contains("\n\nNombre del combo: combo corral"));
		
	}
}

