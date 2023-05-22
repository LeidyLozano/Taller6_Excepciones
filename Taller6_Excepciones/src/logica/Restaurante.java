package logica;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import excepciones.IngredienteRepetidoException;
import excepciones.ProductoRepetidoException;

public class Restaurante
{
    private ArrayList<ProductoMenu> menubase; // Tiene sentido que sea de tipo ProductoMenu y no producto
    private ArrayList<Ingrediente> ingredientes;
    private ArrayList<Combo> combos;
    private ArrayList<ProductoMenu> bebidas;
    private Pedido pedidoEnCurso;
    private ArrayList<Pedido> pedidos;
    private int indexPedido;

    public Restaurante()
    {
	this.menubase = new ArrayList<ProductoMenu>();
	this.ingredientes = new ArrayList<Ingrediente>();
	this.combos = new ArrayList<Combo>();
	this.bebidas = new ArrayList<ProductoMenu>();
	this.pedidos = new ArrayList<Pedido>();
	this.indexPedido = 0;

    }

    public void iniciarPedido(String nombreCliente, String direccionCliente)
    {
	Pedido pedido = new Pedido(nombreCliente, direccionCliente);
	pedidos.add(pedido);
	this.pedidoEnCurso = pedido;

    }

    public void cerrarYGuardarPedido()
    {
	File archivo = new File("C:\\Users\\juanc\\Desktop\\Taller 2 - Hamburguesas_esqueleto\\data\\Factura"
		+ pedidoEnCurso.getPedido() + ".txt");
	try
	{
	    boolean res = archivo.createNewFile();
	} catch (IOException e)
	{
	    System.out.println("An error occurred.");
	    e.printStackTrace();
	}
	pedidoEnCurso.guardarFactura(archivo);

	int existe = revisar(pedidoEnCurso);
	if (existe!=0)
	{
	    System.out.println("La persona con el id de factura #" + existe + " pidio lo mismo que usted.");
	} else
	{
	    System.out.println("Fue el unico que pidio todo esto.");
	}

	if (pedidos.size() > indexPedido + 1)
	{
	    this.indexPedido += 1;
	    this.pedidoEnCurso = pedidos.get(indexPedido);
	}

    }

    public Pedido getPedidoEnCurso()
    {
	return this.pedidoEnCurso;
    }

    public ArrayList<Pedido> getPedidos()
    {
	return this.pedidos;
    }

    public ArrayList<ProductoMenu> getMenuBase() // Cambie de Producto a ProductoMenu ya que el tipo de objeto guardado
						 // en la lista es ProductoMenu
    {
	return this.menubase;
    }

    public ArrayList<Ingrediente> getIngredientes()
    {
	return this.ingredientes;
    }

    public ArrayList<Combo> getCombos()
    {
	return this.combos;
    }

    public ArrayList<ProductoMenu> getBebidas()
    {
	return this.bebidas;
    }

    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos)
    {
	try
	{
	    cargarIngredientes(archivoIngredientes);
	} catch (FileNotFoundException e)
	{
	    System.out.println("Archivo de ingredientes no encontrado.");
	    e.printStackTrace();
	}
	try
	{
	    cargarMenu(archivoMenu);
	} catch (FileNotFoundException e)
	{
	    System.out.println("Archivo de menu no encontrado.");
	    e.printStackTrace();
	}
	try
	{
	    cargarCombos(archivoCombos);
	} catch (FileNotFoundException e)
	{
	    System.out.println("Archivo de combos no encontrado.");
	    e.printStackTrace();
	}
    }

    // Cargar archivos con bebidas
    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos,
	    File archivoBebidas)
    {
	try
	{
	    cargarIngredientes(archivoIngredientes);
	} catch (FileNotFoundException e)
	{
	    System.out.println("Archivo de ingredientes no encontrado.");
	    e.printStackTrace();
	}
	try
	{
	    cargarMenu(archivoMenu);
	} catch (FileNotFoundException e)
	{
	    System.out.println("Archivo de menu no encontrado.");
	    e.printStackTrace();
	}
	try
	{
	    cargarCombos(archivoCombos);
	} catch (FileNotFoundException e)
	{
	    System.out.println("Archivo de combos no encontrado.");
	    e.printStackTrace();
	}
	try
	{
	    cargarBebidas(archivoBebidas);
	} catch (FileNotFoundException e)
	{
	    System.out.println("Archivo de bebidas no encontrado.");
	    e.printStackTrace();
	}
    }

    private int revisar(Pedido pedido)
    {
	int res=0;
	if (this.pedidos.size() == 1)
	{
	    return res;
	} 
	else
	{
	    for (Pedido element : this.pedidos)
	    {
		if (element.getPedido() != pedido.getPedido())
		{
		    for (Producto prod : pedido.getElementos())
		    {
			int tam = 1;
			for (Producto prod2 : element.getElementos())
			{
			    if(prod.generarTextoFactura().equals(prod2.generarTextoFactura()))
			    {
				tam+=1;
			    }
			}
			if (tam == pedido.getElementos().size())
			{
			    res=element.getPedido();
			    return res;
			    
			}
		    }

		}
	    }
	}

	return res;
    }

    private void cargarIngredientes(File archivoIngredientes) throws FileNotFoundException{
    try {	
	    // Se lee el archivo
		Scanner reader = new Scanner(archivoIngredientes);
		while (reader.hasNextLine())
		{
	
		    String[] cols = reader.nextLine().split(";");
	
		    // La primera columna cols[0] es el nombre y cols[1] el precio.
		    Ingrediente ingrediente;
		    String nombre = cols[0];
		    int costoAdicional = Integer.parseInt(cols[1]);
		    int cals;
	
		    if (cols.length > 2)
		    {
			cals = Integer.parseInt(cols[2]);
			ingrediente = new Ingrediente(nombre, costoAdicional, cals);
		    } else
		    {
			ingrediente = new Ingrediente(nombre, costoAdicional);
		    }
		
	
		    // Lo agregamos al ArrayList, podría saltar una excepción.
		    
		    validarExistenciaIngrediente(ingrediente);
		
		reader.close();}}//try
    
    catch(IngredienteRepetidoException e) {
    	
    	System.out.println("¡Algo salió mal D: !");
    	System.out.println(e.getMessage());
    	e.printStackTrace();
    }
	


    }
    
    private void validarExistenciaIngrediente(Ingrediente ingrediente) throws IngredienteRepetidoException{
    	
    	boolean existenciaRepetida = false;
    	String nombreIngrediente = ingrediente.getNombre();
    	
    	for (Ingrediente ingredienteLista: ingredientes) {
    		String nombreIngredienteLista = ingredienteLista.getNombre();
    		
    		if (nombreIngrediente.equals(nombreIngredienteLista)) {
    			
    			existenciaRepetida = true;
    			
    			throw new IngredienteRepetidoException("¡Ups, el ingrediente " + nombreIngrediente + " ya existe!");
    		}
    	}
    	
    	if (existenciaRepetida == false) {
    		
    		ingredientes.add(ingrediente); 
    	}
    	}
    
private void validarExistenciaProducto(ProductoMenu producto) throws ProductoRepetidoException{
    	
    	boolean existenciaRepetida = false;
    	String nombreProducto = producto.getNombre();
    	
    	for (ProductoMenu productoLista: menubase) {
    		String nombreProductoLista = productoLista.getNombre();
    		
    		if (nombreProducto.equals(nombreProductoLista)) {
    			
    			existenciaRepetida = true;
    			
    			throw new ProductoRepetidoException("¡Ups, el producto " + nombreProducto + " ya existe!");
    		}
    	}
    	
    	if (existenciaRepetida == false) {
    		
    		menubase.add(producto); 
    	}
    	}
    
    private void cargarMenu(File archivoMenu) throws FileNotFoundException
    {
    	
    	try {
		// Se lee el archivo
		Scanner reader = new Scanner(archivoMenu);
		while (reader.hasNextLine())
		{
		    String[] cols = reader.nextLine().split(";");
	
		    // La primera columna cols[0] es el nombre del item del menu y cols[1] el
		    // precio.
		    String nombre = cols[0];
		    int cals;
		    ProductoMenu producto;
		    int precioBase = Integer.parseInt(cols[1]);
		    if (cols.length > 2)
		    {
			cals = Integer.parseInt(cols[2]);
			producto = new ProductoMenu(nombre, precioBase, cals);
		    } else
		    {
			cals = 0;
			producto = new ProductoMenu(nombre, precioBase);
		    }
	
		    // Lo agregamos al ArrayList
		    
		    validarExistenciaProducto(producto);
		    
			}
			reader.close();}
    	
    	catch(ProductoRepetidoException e) {
    		
    		System.out.println("¡Algo salió mal D: !");
        	System.out.println(e.getMessage());
        	e.printStackTrace();
    		
    	}

    }

    private void cargarCombos(File archivoCombos) throws FileNotFoundException
    {
	// Se lee el archivo
	Scanner reader = new Scanner(archivoCombos);
	while (reader.hasNextLine())
	{
	    String[] cols = reader.nextLine().split(";");

	    // La primera columna cols[0] es el nombre del combo y cols[1] el descuento.
	    String nombre_combo = cols[0];
	    String porcentaje = cols[1];
	    porcentaje = porcentaje.replaceAll("%", "");
	    double descuento = Double.parseDouble(porcentaje);

	    Combo combo = new Combo(nombre_combo, descuento);

	    for (ProductoMenu prod : menubase)
	    {

		if ((prod.getNombre().equals(cols[2])) || (prod.getNombre().equals(cols[3]))
			|| (prod.getNombre().equals(cols[4])))
		{
		    combo.agregarItemACombo(prod);
		}

	    }

	    // Lo agregamos al ArrayList
	    combos.add(combo);

	}
	reader.close();

    }

    private void cargarBebidas(File archivoBebidas) throws FileNotFoundException
    {
	// Se lee el archivo
	Scanner reader = new Scanner(archivoBebidas);
	while (reader.hasNextLine())
	{
	    String[] cols = reader.nextLine().split(";");

	    // La primera columna cols[0] es el nombre y cols[1] el precio.
	    ProductoMenu bebida;
	    String nombre = cols[0];
	    int costo = Integer.parseInt(cols[1]);
	    int cals;

	    if (cols.length > 2)
	    {
		cals = Integer.parseInt(cols[2]);
		bebida = new ProductoMenu(nombre, costo, cals);
	    } else
	    {
		bebida = new ProductoMenu(nombre, costo);
	    }

	    // Lo agregamos al ArrayList
	    bebidas.add(bebida);

	}
	reader.close();
    }

}
