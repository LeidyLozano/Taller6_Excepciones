package consola;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import logica.Combo;
import logica.Ingrediente;
import logica.Pedido;
import logica.Producto;
import logica.ProductoAjustado;
import logica.ProductoMenu;
import logica.Restaurante;

public class AplicacionModificada
{
    private Restaurante restaurante;

    public void mostrarMenu()
    {
	ArrayList<ProductoMenu> menubase = restaurante.getMenuBase();
	int i = 1;
	for (ProductoMenu producto : menubase)
	{
	    System.out.println(i + ". " + producto.getNombre() + ", Precio: " + producto.getPrecio() + ", Calorias: " + producto.getCalorias());
	    i++;
	}
    }

    public void mostrarCombos()
    {
	ArrayList<Combo> combos = restaurante.getCombos();
	int i = 1;
	for (Combo combo : combos)
	{
	    System.out.println("\n" + i + ". " + combo.getNombre() + ", Precio: " + combo.getPrecio() + ", Elementos:");

	    ArrayList<Producto> items = combo.getProductos();
	    for (Producto item : items)
	    {
		System.out.println("Item: " + item.getNombre() + ", Precio: " + item.getPrecio());
	    }

	    i++;
	}
    }
    
    public void mostrarBebidas()
    {
	ArrayList<ProductoMenu> bebidas = restaurante.getBebidas();
	int i = 1;
	for (ProductoMenu bebida : bebidas)
	{
	    System.out.println("\n" + i + ". " + bebida.getNombre() + ", Precio: " + bebida.getPrecio());
	    i++;
	}
    }

    // Mostar opciones que se pueden agregar al pedido
    public void mostrarAgregarElementos()
    {
	System.out.println("1. Producto del menu");
	System.out.println("2. Combo");
	System.out.println("3. Bebida");
	System.out.println("4. Producto modificado");
	
    }

    // Mostrar menu de opciones
    public void mostrarOpciones()
    {
	System.out.println("0. Cargar archivos");
	System.out.println("1. Mostrar menu");
	System.out.println("2. Iniciar un nuevo pedido");
	System.out.println("3. Agregar un elemento a un pedido");
	System.out.println("4. Cerrar un pedido y guardad la factura");
	System.out.println("5. Consultar la informacion de un pedido dado su id");
	System.out.println("6. Salir del app");
    }

    public void ejecutarOpcion(int opcionSeleccionada)
    {
	switch (opcionSeleccionada)
	{
	case 0:
	    cargarArchivos();
	    break;
	case 1:
	    mostrarMenu();
	    break;
	case 2:
	    ejecutarIniciarPedido();
	    break;
	case 3:
	    ejecutarAgregarElemento();
	    break;
	case 4:
	    ejecutarCerrarPedido();
	    break;
	case 5:
	    ejecutarConsultarInformacionPedido();
	    break;
	}
    }

    // Carga de archivos
    private void cargarArchivos()
    {
	this.restaurante = new Restaurante();
	
	
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

    // Se agrego funcion para la opcion del menu
    public void ejecutarIniciarPedido()
    {
	// Lee nombre y direccion del cliente
	Scanner scanner = new Scanner(System.in);
	System.out.println("Ingrese su nombre:");
	String nombreCliente = scanner.nextLine();

	System.out.println("Ingrese su direccion:");
	String direccionCliente = scanner.nextLine();

	// IniciarPedido
	Pedido.setNumeroPedidos(Pedido.getNumeroPedidos() + 1);
	restaurante.iniciarPedido(nombreCliente, direccionCliente);

    }

    public void ejecutarAgregarElemento()
    {
	Pedido pedidoEnCurso = restaurante.getPedidoEnCurso();
	System.out.println("¿Que desea agregar?");
	mostrarAgregarElementos();
	Scanner scanner = new Scanner(System.in);
	int opcion = scanner.nextInt();
	boolean existeProducto = false;
	for (Producto elemento : pedidoEnCurso.getElementos())
	{
	    if (!elemento.getNombre().contains("combo"))
	    {
		existeProducto = true;
	    }

	}

	switch (opcion)
	{
	case 1:
	    // Creamos lista del menu, mostramos el menu y despues,basandose en la opcion
	    // escogida, lo agregamos al pedido
	    ArrayList<ProductoMenu> menu = restaurante.getMenuBase();
	    mostrarMenu();
	    System.out.println("Numero de producto que desea:");
	    int op = scanner.nextInt();
	    pedidoEnCurso.agregarProducto(menu.get(op - 1));
	    existeProducto = true;
	    break;

	case 2:
	    // Creamos lista de combos, mostramos los combos basandose en la opcion escogida
	    // lo agregamos al pedido
	    ArrayList<Combo> combos = restaurante.getCombos();
	    mostrarCombos();
	    System.out.println("Numero de combo que desea:");
	    int op1 = scanner.nextInt();
	    pedidoEnCurso.agregarProducto(combos.get(op1 - 1));
	    break;
	case 3:
	    ArrayList<ProductoMenu> bebidas = restaurante.getBebidas();
	    mostrarBebidas();
	    System.out.println("Numero de bebida que desea:");
	    int op2 = scanner.nextInt();
	    pedidoEnCurso.agregarProducto(bebidas.get(op2 - 1));
	    break;
	case 4:
	    if (existeProducto)
	    {
		// Le pedimos que escoja un producto de los que ya esta en su pedido y que sea
		// del menubase y le preguntamos a cual le quiere hacer cambios, luego miramos
		// si quiere agregarle o quitarle algo
		System.out.println("A que producto le desea agregar algo?");
		int i = 1;
		
		for (Producto producto : pedidoEnCurso.getElementos())
		{
		    if (!producto.getNombre().contains("combo") && !producto.getNombre().contains("agua") && !producto.getNombre().contains("gaseosa"))
		    {
			System.out.println(i + ". " + producto.getNombre() + ", Precio: " + producto.getPrecio());
		    }

		    i++;
		}

		int prod = scanner.nextInt();
		ProductoAjustado modificado = new ProductoAjustado(
			(ProductoMenu) pedidoEnCurso.getElementos().get(prod - 1));
		System.out.println("Desea 1. agregarle o 2. quitarle algo?");

		// Si le quiere agregar algo le mostramos los ingredientes, iramos cuales escoge
		// y los agregamos al producto modificado
		if (scanner.nextInt() == 1)
		{
		    boolean cont = true;
		    int j = 0;
		    while (cont)
		    {
			if (j != 0)
			{
			    System.out.println("Desea seguir agregandole cosas? (1 es si, 0 es no):");
			    if (scanner.nextInt() == 0)
			    {
				cont = false;
				continue;
			    } else
			    {
				System.out.println("Opcion invalida.");
			    }
			}
			System.out.println("Que le desea agregar?");
			j = 1;
			for (Ingrediente ing : restaurante.getIngredientes())
			{
			    System.out.println(
				    j + ". " + ing.getNombre() + ", Costo Adicional: " + ing.getCostoAdicional());
			    j++;
			}
			int ingre = scanner.nextInt();
			modificado.agregarIngrediente(restaurante.getIngredientes().get(ingre - 1));

		    }

		}
		// Si le quiere quitar algo le mostramos los ingredientes, iramos cuales escoge
		// y los agregamos al producto modificado
		else if (scanner.nextInt() == 2)
		{
		    boolean cont = true;
		    int j = 0;
		    while (cont)
		    {
			if (j != 0)
			{
			    System.out.println("Desea seguir quitandole cosas? (1 es si, 0 es no):");
			    if (scanner.nextInt() == 0)
			    {
				cont = false;
			    } else
			    {
				System.out.println("Opcion invalida.");
			    }
			}
			System.out.println("Que le desea quitar?");
			j = 1;
			for (Ingrediente ing : restaurante.getIngredientes())
			{
			    System.out.println(
				    i + ". " + ing.getNombre() + ", Costo Adicional: " + ing.getCostoAdicional());
			    j++;
			}
			int ingre = scanner.nextInt();
			modificado.eliminarIngrediente(restaurante.getIngredientes().get(ingre - 1));

		    }

		} else
		{
		    System.out.println("Opcion invalida.");
		}
		// Finalmente agregamos el producto modificado al pedido
		pedidoEnCurso.agregarProducto(modificado);
		break;
	    } else
	    {
		System.out.println("No hay productos base en el pedido a los que agregarle o quitarle algo.");
	    }

	default:
	    System.out.println("Opcion invalida.");
	}

    }

    public void ejecutarCerrarPedido()
    {
	restaurante.cerrarYGuardarPedido();
    }

    public void ejecutarConsultarInformacionPedido()
    {
	Scanner scanner = new Scanner(System.in);
	System.out.println("Ingrese el id del pedido que desea consultar:");
	int id = scanner.nextInt();
	ArrayList<Pedido> pedidos = restaurante.getPedidos();
	for (Pedido pedido : pedidos)
	{
	    if (pedido.getPedido() == id)
	    {
		File factura = pedido.getFactura();
		try
		{
		    Desktop.getDesktop().open(factura);
		} catch (IOException e)
		{
		    System.out.println("No se puedo abrir el archivo.");
		    e.printStackTrace();
		}

	    }
	}
    }

    public static void main(String[] args)
    {
	AplicacionModificada app = new AplicacionModificada();
	Pedido.setNumeroPedidos(0);
	boolean cont = true;
	boolean cargados = false;
	boolean iniciado = false;
	// Correr aplicacion en loop
	while (cont)
	{
	    System.out.println("\nBienvenido al menu de opciones, seleccione una opción:\n");
	    app.mostrarOpciones();
	    Scanner scanner = new Scanner(System.in);
	    int opcion = scanner.nextInt();
	    if (opcion == 0)
	    {
		if (!cargados)
		{
		    app.ejecutarOpcion(opcion);
		    cargados = true;
		} else
		{
		    System.out.println("Ya cargo los archivos.");
		}
	    } else if (opcion == 1)
	    {
		if (cargados)
		{
		    app.ejecutarOpcion(opcion);
		} else
		{
		    System.out.println("No ha cargado los archivos.");
		}

	    } else if (opcion == 2)
	    {
		if (cargados)
		{
		    if (!iniciado)
		    {
			app.ejecutarOpcion(opcion);
			iniciado = true;
		    } else
		    {
			System.out
				.println("Ya hay un pedido iniciado, primero cierrelo para seguir con nuevos pedidos.");
		    }
		} else
		{
		    System.out.println("No ha cargado los archivos.");
		}
	    } else if (opcion == 3)
	    {
		if (cargados)
		{
		    if (iniciado)
		    {
			app.ejecutarOpcion(opcion);
			System.out.println();
		    } else
		    {
			System.out.println("No ha iniciado ningun pedido");
		    }
		} else
		{
		    System.out.println("No ha cargado los archivos.");
		}
	    } else if (opcion == 4)
	    {
		if (cargados)
		{
		    if (iniciado)
		    {
			app.ejecutarOpcion(opcion);
			iniciado = false;
		    } else
		    {
			System.out.println("No hay nigun pedido abierto.");
		    }
		} else
		{
		    System.out.println("No ha cargado los archivos.");
		}
	    } else if (opcion == 5)
	    {
		if (cargados)
		{
		    app.ejecutarOpcion(opcion);
		} else
		{
		    System.out.println("No ha cargado los archivos.");
		}
	    } else if (opcion == 6)
	    {
		cont = false;
	    } else
	    {
		System.out.println("Opcion invalida.");
	    }

	}

    }

}
