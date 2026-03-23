
package Fut_Training_app;

//Clase hija, crea el objetos de CategoriaMenor
public class CategoriaMenor extends Deportista{
    //Atributos
    private String descripcionCategoria;
    //Constructor
    public CategoriaMenor(String descripcionCategoria, int numero, String nombre, int altura, String posicion, String fecha, String tipoEnt) {
        super(numero, nombre, altura, posicion, fecha, "Categoria Menor", tipoEnt);
        this.descripcionCategoria = descripcionCategoria;
    }

    @Override
    public String toString() {
        return super.nombre +','+ super.altura+','+super.posicion+','+super.numero+','+super.tipoEnt+','+super.fecha+','+ super.categoria+','+ descripcionCategoria +",Null,"+"Null";
    }
    
    
    
    
    
}
