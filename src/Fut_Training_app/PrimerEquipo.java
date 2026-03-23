
package Fut_Training_app;

//Clase hija, crea el objetos PrimerEquipo
public class PrimerEquipo extends Deportista {
    
    //Atributos
    private int peso, porcentaje;
    //Constructor
    public PrimerEquipo(int peso, int porcentaje, int numero, String nombre, int altura, String posicion, String fecha, String tipoEnt) {
        super(numero, nombre, altura, posicion, fecha, "Primer Equipo", tipoEnt);
        this.peso = peso;
        this.porcentaje = porcentaje;
    }

    @Override
    public String toString() {
        return super.nombre +','+ super.altura+','+super.posicion+','+super.numero+','+super.tipoEnt+','+super.fecha+','+ super.categoria+",Null,"+peso+','+porcentaje;
    }
   
    
    
    
    
    
    
}
