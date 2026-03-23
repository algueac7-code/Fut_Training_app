
package Fut_Training_app;

import Graficas.Inicio;
import javax.swing.SwingUtilities;

/*
Alonso Guevara Acosta
Programa para el control fisico de jugadores de futbol
UNED Alajuela 2025
 */

public class Principal {

    public static void main(String[] args) {
        
         SwingUtilities.invokeLater(() -> {
            Inicio bienvenida = new Inicio();
            bienvenida.setVisible(true);
        });    
        
    }
    
}
