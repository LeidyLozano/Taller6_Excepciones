package logica;

public class ProductoMenu implements Producto
{
    private String nombre;
    private int precioBase;
    private int calorias;

    public ProductoMenu(String nombre, int precioBase)
    {
	this.nombre = nombre;
	this.precioBase = precioBase;
	this.calorias = 0;
    }
    
    public ProductoMenu(String nombre, int precioBase, int calorias)
    {
	this.nombre = nombre;
	this.precioBase = precioBase;
	this.calorias = calorias;
    }

    public String getNombre()
    {
	return this.nombre;
    }

    public int getPrecio()
    {
	return precioBase;
    }
    
    public int getCalorias()
    {
	return this.calorias;
    }

    public String generarTextoFactura()
    {
	return "\nNombre del producto base del menu: " + this.nombre + ", Precio base: " + this.precioBase + ", Calorias base: " + this.calorias;
    }
}
