package logica;

import java.util.ArrayList;

public class Combo implements Producto
{
    private double descuento;
    private String nombreCombo;
    private int calorias;
    private ArrayList<Producto> itemsCombo;

    public Combo(String nombre, double descuento)
    {
	this.descuento = (double) descuento/100;
	this.nombreCombo = nombre;
	this.calorias = 0;
	this.itemsCombo = new ArrayList<Producto>();
    }
    
    public void agregarItemACombo(Producto itemCombo)
    {
	this.itemsCombo.add(itemCombo);
	this.calorias+=itemCombo.getCalorias();
    }

    public int getPrecio()
    {
	double precio = 0d;
	for (Producto item : itemsCombo) 
	{
	    precio += item.getPrecio();
	}
	
	precio = precio - (int)(precio*descuento);
	return (int) precio;
    }

    public int getCalorias()
    {
	return this.calorias;
    }

    public String generarTextoFactura()
    {
	String generado = "\n\nNombre del combo: " + this.nombreCombo + ", Items del combo:";
	int total = 0;
	for (Producto item : itemsCombo)
	{
	    total += item.getPrecio();
	    
	    generado += item.generarTextoFactura();
	}
	generado += "\nTotal: " + total + ", Descuento: " + (int) (descuento*100.0) + "%, Total con descuento: " + getPrecio() + ", Calorias totales: " + calorias + "\n";
	return generado;
    }

    public String getNombre()
    {
	return nombreCombo;
    }
    
    public ArrayList<Producto> getProductos()
    {
	return this.itemsCombo;
    }
}
