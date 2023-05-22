package logica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import excepciones.PedidoExcedidoException;

public class Pedido
{
    private static int numeroPedidos;
    private int idPedido;
    private String nombreCliente;
    private String direccionCliente;
    private ArrayList<Producto> elementosPedido;
    private File factura;

    public Pedido(String nombreCliente, String direccionCliente)
    {
	this.idPedido = numeroPedidos;
	this.nombreCliente = nombreCliente;
	this.direccionCliente = direccionCliente;
	this.elementosPedido = new ArrayList<Producto>();
    }

    public int getPedido()
    {
	return this.idPedido;
    }

    public void agregarProducto(Producto nuevoitem)
    {
    	try {comprobarTopePedido(nuevoitem);}
    	
    	catch (PedidoExcedidoException e) {
    		
    		System.out.println("¡Algo salió mal D: !");
        	System.out.println(e.getMessage());
        	e.printStackTrace();
    	}
    }
    
    private void comprobarTopePedido(Producto nuevoElemento) throws PedidoExcedidoException {
    	
    	int PrecioParcialPedido = getPrecioNetoPedido();
    	int PrecioProducto = nuevoElemento.getPrecio();
    	
    	if ((PrecioParcialPedido + PrecioProducto) <=150000) {
    		
    		this.elementosPedido.add(nuevoElemento);
    	}
    	
    	else {
    		throw new PedidoExcedidoException ("¡Ups! Se excedió el costo del pedido, ni modo");
    	}
    	
    }

    private int getPrecioNetoPedido()
    {
	int precio = 0;
	for (Producto elemento : elementosPedido)
	{
	    precio += elemento.getPrecio();
	}
	return precio;
    }
    
    

    private int getPrecioTotalPedido()
    {
	int neto = getPrecioNetoPedido();
	double iva = 0.19d;
	return (int) (neto + (neto * iva));
    }

    private String generarTextoFactura()
    {
	String generado = "Id de la factura: " + this.idPedido + ", Nombre del cliente: " + this.nombreCliente
		+ ", Direccion del cliente: " + this.direccionCliente + ", Items del pedido: \n";
	for (Producto item : elementosPedido)
	{
	    generado += item.generarTextoFactura();
	}
	generado += "\nTotal: " + getPrecioNetoPedido() + ", IVA : 19%" + ", Total con IVA: " + getPrecioTotalPedido() + "\n";
	return generado;
    }

    public void guardarFactura(File archivo)
    {
	try
	{
	    FileWriter writer = new FileWriter(archivo.getAbsoluteFile());
	    BufferedWriter bwriter = new BufferedWriter(writer);
	    bwriter.write(generarTextoFactura());
	    bwriter.close();
	    this.factura = archivo;

	} catch (IOException e)
	{
	    System.out.println("An error occurred.");
	    e.printStackTrace();
	}
	
    }
    
    public File getFactura()
    {
	return factura;
    }

    public ArrayList<Producto> getElementos()
    {
	return this.elementosPedido;
    }

    public static int getNumeroPedidos()
    {
	return Pedido.numeroPedidos;
    }

    public static void setNumeroPedidos(int numeroPedidos)
    {
	Pedido.numeroPedidos = numeroPedidos;
    }
    
    public boolean equals(Pedido pedido)
    {
	boolean res = false;
	if (this.idPedido == pedido.idPedido)
	{
	    if (this.elementosPedido.size()==pedido.elementosPedido.size())
	    {
		int tam = 0;
		for (Producto elemento: this.elementosPedido)
		{
		    for(Producto otroEl : pedido.elementosPedido)
		    {
			if(elemento.generarTextoFactura().equals(otroEl.generarTextoFactura()))
			{
			    tam+=1;
			}
		    }
		}
		
		if(tam == this.elementosPedido.size()-1) {
		    res = true;
		}
	    }
	}
	return res;
    }
}
