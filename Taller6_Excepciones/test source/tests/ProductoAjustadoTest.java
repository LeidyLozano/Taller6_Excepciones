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
	void AgregarIngredienteCorrPrecioTest() {
		
		setUpRestauranteModificado();
		
		ArrayList<ProductoMenu> menuProductos = restaurante.getMenuBase();
		ProductoMenu productoCorral = menuProductos.get(0);
		
		ProductoAjustado corralAjustado = new ProductoAjustado(productoCorral);
		
		ArrayList<Ingrediente> opcionIngredientes = restaurante.getIngredientes();
		Ingrediente ingredienteLechuga = opcionIngredientes.get(0);
		
		corralAjustado.agregarIngrediente(ingredienteLechuga);
		
		assertEquals(15000, corralAjustado.getPrecio(),"El precio de la corral debería ser 15000");
		
	}
	@Test
	void AgregarIngredienteCorrCaloriasTest() {
		
		setUpRestauranteModificado();
		
		ArrayList<ProductoMenu> menuProductos = restaurante.getMenuBase();
		ProductoMenu productoCorral = menuProductos.get(0);
		
		ProductoAjustado corralAjustado = new ProductoAjustado(productoCorral);
		
		ArrayList<Ingrediente> opcionIngredientes = restaurante.getIngredientes();
		Ingrediente ingredienteLechuga = opcionIngredientes.get(0);
		
		corralAjustado.agregarIngrediente(ingredienteLechuga);
		
		assertEquals(561, corralAjustado.getCalorias(),"La corral tiene ahora 561 calorías.");
		
	}
	@Test
	void EliminarIngredienteCorrPrecioTest() {
		
		setUpRestauranteModificado();
		
		ArrayList<ProductoMenu> menuProductos = restaurante.getMenuBase();
		ProductoMenu productoCorral = menuProductos.get(0);
		
		ProductoAjustado corralAjustado = new ProductoAjustado(productoCorral);
		
		ArrayList<Ingrediente> opcionIngredientes = restaurante.getIngredientes();
		Ingrediente ingredienteHuevo = opcionIngredientes.get(5);
		
		corralAjustado.eliminarIngrediente(ingredienteHuevo);
		
		assertEquals(14000, corralAjustado.getPrecio(),"El precio de la corral no debe cambiar");
}
	@Test
	void EliminarIngredienteCorrCaloriaTest() {
	
		setUpRestauranteModificado();
		
		ArrayList<ProductoMenu> menuProductos = restaurante.getMenuBase();
		ProductoMenu productoCorral = menuProductos.get(0);
		
		ProductoAjustado corralAjustado = new ProductoAjustado(productoCorral);
		
		ArrayList<Ingrediente> opcionIngredientes = restaurante.getIngredientes();
		Ingrediente ingredienteHuevo = opcionIngredientes.get(5);
		
		corralAjustado.eliminarIngrediente(ingredienteHuevo);
		
		assertEquals(456, corralAjustado.getCalorias(),"La corral tiene ahora 456 calorías.");
	}
	@Test
	void getTextoFacturaCorralAjustadoTest() {
		
		setUpRestauranteModificado();
		
		ArrayList<ProductoMenu> menuProductos = restaurante.getMenuBase();
		ProductoMenu productoCorral = menuProductos.get(0);
		
		ProductoAjustado corralAjustado = new ProductoAjustado(productoCorral);
		
		ArrayList<Ingrediente> opcionIngredientes = restaurante.getIngredientes();
		Ingrediente ingredienteLechuga = opcionIngredientes.get(0);
		Ingrediente ingredienteHuevo = opcionIngredientes.get(5);
		
		corralAjustado.agregarIngrediente(ingredienteLechuga);
		corralAjustado.eliminarIngrediente(ingredienteHuevo);
		
		String stringGeneradoCorral = corralAjustado.generarTextoFactura();
		assertTrue(stringGeneradoCorral.contains("\nNombre del producto base del menu: corral, Precio base: 14000,"
				+ " Calorias base: 546, Adiciones: \n"
				+ "Con adicion de: lechuga | Costo adicional: 1000 | Calorias adicionales: 15\n"
				+ "Sin: huevo | Calorias restadas: 90\n"
				+ "Precio total: 15000, Calorias totales: 471\n\n"));
	}

}
