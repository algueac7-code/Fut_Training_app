
package Fut_Training_app;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

//Clase para el control de los tipos de entrenamiento
public class Entrenamiento {
    //Atributos
    private String codigoEntrenamiento;
    private String descripcion;
    private String categoriaEntrenamiento;
    
    //Constructor de la clase
    public Entrenamiento(String codigoEntrenamiento, String descripcion, String categoriaEntrenamiento) {
        
        this.codigoEntrenamiento = codigoEntrenamiento;
        this.descripcion = descripcion;
        this.categoriaEntrenamiento = categoriaEntrenamiento;
    }
    
    //Metodo para Guardar informacion en el archivo txt.
    public void guardarEnArchivo() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Entrenamientos.txt", true))) {
            writer.write(codigoEntrenamiento + "," + descripcion + "," + categoriaEntrenamiento);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            //e.printStackTrace();//Recuerda dejar comentado este metodo, sino, va hacer un basurero.
            System.out.println("Error al guardar" +e.getMessage());
        }
    }
    
    // Método para buscar un código en el archivo
    public static boolean buscarCodigo(String codigoBuscado) {
        File archivo = new File("Entrenamientos.txt");
        boolean encontrado = false;
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.startsWith(codigoBuscado + ",")) { // Verifica si el código existe
                    encontrado = true;
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Error al leer el archivo.");
        }

        return encontrado;
    }
    
    //Metodo para editar un entrenamiento
    public static boolean editarEntrenamiento(String codigo, String nuevaDescripcion, String nuevaCategoria) {
        File archivo = new File("entrenamientos.txt");
        if (!archivo.exists()) {
            return false; // Archivo no encontrado
        }
        //Crea un arreglo con todas las lineas del archivo
        try {
            List<String> lineas = Files.readAllLines(archivo.toPath());
            boolean encontrado = false;
            //Lee el arreglo hasta encontrar el codigo
            for (int i = 0; i < lineas.size(); i++) {
                String[] datos = lineas.get(i).split(",");//Separa cada linea
                //Si encuentra el codigo cambia los valores por los nuevos y indica que el archivo fue encontrado
                if (datos[0].equals(codigo)) { 
                    // Actualizar los datos
                    lineas.set(i, codigo + "," + nuevaDescripcion + "," + nuevaCategoria);
                    encontrado = true;
                    break;
                }
            }
            //Sobre escribe el archivo
            if (encontrado) {
                Files.write(archivo.toPath(), lineas);
            }
            return encontrado;
        } catch (IOException ex) {
            System.out.println("Error al editar el archivo.");
            return false;
        }
    }
    
    //Metodo para borrar un entrenamiento
    public static boolean borrarEntrenamiento(String codigo) {
        File archivo = new File("entrenamientos.txt");
        if (!archivo.exists()) {
            return false;
        }
        try {
            List<String> lineas = Files.readAllLines(archivo.toPath());
            List<String> nuevasLineas = new ArrayList<>();
            boolean encontrado = false;
            for (String linea : lineas) {
                String[] datos = linea.split(",");
                if (!datos[0].equals(codigo)) {
                    nuevasLineas.add(linea);
                } else {
                    encontrado = true;
                }
            }
            if (encontrado) {
                Files.write(archivo.toPath(), nuevasLineas);
            }
            return encontrado;
        } catch (IOException ex) {
            System.out.println("Error al borrar el archivo.");
            ex.printStackTrace();
            return false;
        }
    }

    
    
}
