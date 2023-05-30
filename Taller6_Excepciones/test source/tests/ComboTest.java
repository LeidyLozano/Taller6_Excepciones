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
		
		ArrayList<ProductoMenu> menuProductos = restaurante.getMenuBase();
		ProductoMenu productoCorral = menuProductos.get(0);
		
		comboCorral.agregarItemACombo(productoCorral);
		
		
		ArrayList<Producto> productosCorral = comboCorral.getProductos();
		assertEquals(4,productosCorral.size(),"El combo de la corral ahora debería tener cuatro elementos");	
		
	}
	@Test
	
	void generarTextoFacturaContenidoCorralTest() {
		
		setUpRestauranteModificado();
		
		ArrayList<Combo> opcionesCombos = restaurante.getCombos();
		Combo comboCorral = opcionesCombos.get(0);
		
		ArrayList<ProductoMenu> menuProductos = restaurante.getMenuBase();
		ProductoMenu productoCosteña = menuProductos.get(11);
		
		comboCorral.agregarItemACombo(productoCosteña);
		
		String textoGenerado = comboCorral.generarTextoFactura();
		
		//dada lo delicada que es la textura de la factura, se revisa unicamente
		//que tenga el contenido deseado, no su estructura.
		assertTrue(textoGenerado.contains("combo corral"));
		assertTrue(textoGenerado.contains("costeña"));
		assertTrue(textoGenerado.contains("20000"));
		assertTrue(textoGenerado.contains("840"));
		assertTrue(textoGenerado.contains("10%"));
		
	}
}

