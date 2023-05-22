package logica;

import java.util.ArrayList;

public class ProductoAjustado implements Producto
{
    private String nombre;
    private int precio;
    private int preciobase;
    private int caloriasbase;
    private int calorias;
    private ArrayList<Ingrediente> agregados;
    private ArrayList<Ingrediente> eliminados;

    public ProductoAjustado(ProductoMenu base)
    {
	this.nombre = base.getNombre();
	this.preciobase = base.getPrecio();
	this.precio = base.getPrecio();
	this.caloriasbase = base.getCalorias();
	this.calorias = base.getCalorias();
	this.agregados = new ArrayList<Ingrediente>();
	this.eliminados = new ArrayList<Ingrediente>();
    }

    public void agregarIngrediente(Ingrediente ingrediente)
    {
	this.agregados.add(ingrediente);
	int precio_ing = ingrediente.getCostoAdicional();
	this.precio += precio_ing;
	this.calorias += ingrediente.getCalorias();
    }

    public void eliminarIngrediente(Ingrediente ingrediente)
    {
	this.eliminados.add(ingrediente);
	this.calorias -= ingrediente.getCalorias();
    }

    public String getNombre()
    {
	return this.nombre;
    }

    public int getPrecio()
    {
	return this.precio;
    }

    public int getCalorias()
    {
	return this.calorias;
    }

    public String generarTextoFactura()
    {
	String generado = "\nNombre del producto base: " + this.nombre + ", Precio base: " + this.preciobase
		+ ", Calorias base: " + this.caloriasbase + ", Adiciones: \n";
	for (Ingrediente agregado : agregados)
	{
	    String nombreAgregado = agregado.getNombre();
	    int precioAgregado = agregado.getCostoAdicional();
	    int caloriasAgregado = agregado.getCalorias();

	    generado += "Con adicion de: " + nombreAgregado + " | Costo adicional: " + precioAgregado
		    + " | Calorias adicionales: " + caloriasAgregado + "\n";
	}

	for (Ingrediente eliminado : eliminados)
	{
	    String nombreEliminado = eliminado.getNombre();
	    int caloriasEliminado = eliminado.getCalorias();

	    generado += "Sin: " + nombreEliminado + " | Calorias restadas: " + caloriasEliminado + "\n";
	}
	generado += "Precio total: " + precio + ", Calorias totales: " + calorias + "\n\n";
	return generado;
    }
}
