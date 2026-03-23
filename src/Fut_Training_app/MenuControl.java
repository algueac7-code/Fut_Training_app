
package Fut_Training_app;

//Controla la logica de validacion, menu principal,
import Graficas.FormularioDeportista;
import Graficas.FormularioEntrenamiento;
import Graficas.FormularioReportes;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class MenuControl {
     //Controla el menu principal
     public static void abrirVentana(String opcion) {
        try{ 
            switch (opcion) {
                case "Ingresar Datos" -> new FormularioDeportista().setVisible(true);
                case "Entrenamientos" -> new FormularioEntrenamiento().setVisible(true);
                case "Reportes" -> new FormularioReportes().setVisible(true);
                //default -> JOptionPane.showMessageDialog(null, "Debes seleccionar una opción.");
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error al abrir la ventana");
        }
    }
     
    //Metodo para Validar cajas de texto Generales
    public static boolean validarDatos(String numeroJugador, String nombre, String estatura, String posicionSeleccionada) {
        
        //Valida que el campo Jugador no este vacio, sea un numero entero y en un rango de 0 a 99
        if (numeroJugador.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El número de jugador no puede estar vacío.");
            return false;
        }
        try {
            int numero = Integer.parseInt(numeroJugador.trim());
            if (numero <= 0 || numero > 99) {
                JOptionPane.showMessageDialog(null, "El número de jugador debe estar en un rango de 0 a 99.");
                return false;
            }    
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número de jugador válido.");
            return false;
        } 
        // Valida que el campo Nombre solo tenga letras, y una longitud entre 4 y 11 caracteres
        if (nombre.isEmpty() || !nombre.matches("[a-zA-Z ]+") || nombre.length() < 4 || nombre.length() > 11) {
            JOptionPane.showMessageDialog(null, "Ingrese un nombre válido (solo letras, longitud entre 4 y 11 caracteres).");
            return false;
        }
        //Valida que el campo Altura no este vacio, sea un numero entero y superior a 40cm
        if (estatura.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo 'altura' no puede estar vacío.");
            return false;
        }
        try {
            int numero = Integer.parseInt(estatura.trim());
            if (numero <= 40) {
                JOptionPane.showMessageDialog(null, "El deportista debe medir mas de 40cm");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un numero entero.");
            return false;
        }
        //Valida que la posicion este seleccionada
        if (posicionSeleccionada == null || posicionSeleccionada.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Seleccione una posicion.");
            return false;
        }

        return true;
    }
    
    //Metodo para validar cajas de texto Primer equipo
    public static boolean validarDatosPrimerEquipo(String peso,String porGrasa){
        //Valida que el peso sea un numero entero y mayor a 1
        if (peso.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "El campo peso no puede estar vacío.");
            return false;
        }
        try {
            int numero = Integer.parseInt(peso.trim());
            if (numero <= 1) {
                JOptionPane.showMessageDialog(null, "El peso debe ser mayor a 1");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un numero entero.");
            return false;
        }
        //Valida que el porsentaje de grasa este entre 1 y 99  
        if (porGrasa.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Llena el campo porcentaje de grasa");
            return false;
        }
        try {
            int numero = Integer.parseInt(porGrasa.trim());

            // Validación opcional de rango
            if (numero <= 1 || numero > 99) {
                JOptionPane.showMessageDialog(null, "Los porcentajes deben estar en un rango de 1 a 99.");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Ingrese un número entrero.");
            return false;
        }
        
        return true;
    }
    
    //Carga las descripciones del primer equipo exclusivamente a la lista desplegable
    public static void cargarEntrenamientosMayor(JComboBox<String> entrenamientoMayor) {
        entrenamientoMayor.removeAllItems(); //Limpia la lista antes de cargar nuevos datos
        entrenamientoMayor.addItem(""); //Vacias las opciones de la lista
        try (BufferedReader br = new BufferedReader(new FileReader("Entrenamientos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");//Separa la linea segun cada coma.
                //Discrimina solo por Primer equipo
                if (datos.length >= 3 && datos[2].trim().equalsIgnoreCase("Primer Equipo")) {
                    entrenamientoMayor.addItem(datos[1].trim()); // Agregar solo la descripción
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo Entrenamientos.txt: " + e.getMessage());
        }
    }
    
    //Carga las descripciones de la categoria menor 
    public static void cargarEntrenamientosMenor(JComboBox<String> entrenamientoMenor) {
        entrenamientoMenor.removeAllItems(); 
        entrenamientoMenor.addItem(""); 
        try (BufferedReader br = new BufferedReader(new FileReader("Entrenamientos.txt"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(","); 
                if (datos.length >= 3 && datos[2].trim().equalsIgnoreCase("Categoria Menor")) {
                    entrenamientoMenor.addItem(datos[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo Entrenamientos.txt: " + e.getMessage());
        }
    }
    
}
    

