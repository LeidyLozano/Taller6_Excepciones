package tests;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import excepciones.PedidoExcedidoException;
import logica.Pedido;
import logica.Producto;
import logica.ProductoMenu;
import logica.Restaurante;

class PedidoTest {

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
	
	void agregarItemPedidoTest() throws PedidoExcedidoException {
		
		try {
		setUpRestauranteModificado();
		
		Pedido pedidoTest = new Pedido("Alice","123 Washington");
		
		ArrayList<ProductoMenu> menuProductos = restaurante.getMenuBase();
		ProductoMenu productoCorral = menuProductos.get(0);
		
		pedidoTest.agregarProducto(productoCorral);
	
		ArrayList<Producto> productosAlice = pedidoTest.getElementos();
		assertEquals(1,productosAlice.size(),"El pedido tiene apenas un elemento.");	
		}
		catch (PedidoExcedidoException e) {
			
			fail( "No debería fallar: " + e.getMessage());}}
	
	
	@Test
	
	void agregarItemPedidoExcedidoTest() {
		
		try {
		setUpRestauranteModificado();
		
		Pedido pedidoTest = new Pedido("Bob","123 Texas");
		ArrayList<ProductoMenu> menuProductos = restaurante.getMenuBase();
		
		ProductoMenu productoTerreno = menuProductos.get(5);
		pedidoTest.agregarProducto(productoTerreno);
		pedidoTest.agregarProducto(productoTerreno);
		pedidoTest.agregarProducto(productoTerreno);
		pedidoTest.agregarProducto(productoTerreno);
		pedidoTest.agregarProducto(productoTerreno);
		pedidoTest.agregarProducto(productoTerreno); //aquí el pedido alcanzó los 150.000
		pedidoTest.agregarProducto(productoTerreno); //aquí se debería lanzar la excepción
		fail( "El tope del pedido fue excedido." );
	}
		catch(PedidoExcedidoException e) {
			assertTrue(true,"Se esperaba esta excepción ");
		}
		
	}
}


