
package Fut_Training_app;

//Clase padre o Super Clase
public class Deportista {
    //Atrubutos
    protected int numero;
    protected String nombre;
    protected int altura;
    protected String posicion;
    protected String fecha;
    protected String categoria;
    protected String tipoEnt; 
    //Constructor
    public Deportista(int numero, String nombre, int altura, String posicion, String fecha, String categoria, String tipoEnt) {
        this.numero = numero;
        this.nombre = nombre;
        this.altura = altura;
        this.posicion = posicion;
        this.fecha = fecha;
        this.categoria = categoria;
        this.tipoEnt = tipoEnt;
    }

    @Override
    public String toString() {
        return "Deportista{" + "numero=" + numero + ", nombre=" + nombre + ", altura=" + altura + ", posicion=" + posicion + ", fecha=" + fecha + ", categoria=" + categoria + ", tipoEnt=" + tipoEnt + '}';
    }   
    
}
