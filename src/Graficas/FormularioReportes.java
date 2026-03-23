
package Graficas;

//Clase para crear la interfaz grafica de los reportes

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FormularioReportes extends JFrame {
    
    private JPanel panel;
    private JLabel etNombre,etCategoria,etFechaD,etFechaH;
    private JButton botonN,botonC, botonR,botonF;
    private JTextField ctNombre;
    private JTextArea reporte;
    private JComboBox<String> categorias, añoInicio, añoFinal,mesInicio, mesFinal;
    private String nombreInforme = "Genera un Informe";
    private int conteo;
    
    //Constructor de la clase
    public FormularioReportes(){
        
        setBounds(50,50,750,500);
        setTitle("Reportes del plantel");
        iniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    //Coloca los componentes
    private void iniciarComponentes() {
        
        colocarPaneles();
        colocarAreaTexto();
        colocarBotones();
        colocarEtiquetaSelecInicio();
        colocarEtiquetaSelecFinal();
        colocarConjuntoNombre();
        colocarConjuntoCategoria();
    }
    
    //Crea el panel
    private void colocarPaneles() {
        panel = new JPanel();
        panel.setBackground(Color.gray);
        panel.setLayout(null);
        getContentPane().add(panel);
    }
    
    //Crea areas de texto
    private void colocarAreaTexto(){
        
        reporte = new JTextArea();
        JScrollPane barra = new JScrollPane(reporte);
        barra.setBounds(20, 20, 355,420);
        barra.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        barra.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        reporte.setBackground(Color.WHITE);
        reporte.setText("--"+nombreInforme+"--\n"+"Cantidad de registros encontrados -> "+conteo);
        //reporte.append("Alonso");//Añade mas texto
        reporte.setEditable(false);//Bloquea el contenido
        panel.add(barra); 
    }
    
    //Crea los botones de busqueda y les da funcionalidad
    private void colocarBotones() {
        //Boton de Volver, regresa al menu de seleccion
        botonR = new JButton("Regresar");
        botonR.setBounds(610,410, 100, 25);
        botonR.setFont(new Font("arial",1,14));
        botonR.addActionListener(e -> {
        new Inicio().setVisible(true);
        dispose();
        });
        panel.add(botonR);
        
        
        //Boton buscar en rango de fecha
        botonF = new JButton("Buscar por fecha");
        botonF.setBounds(585,110, 120, 25);
        botonF.setFont(new Font("arial",1,10));
        botonF.addActionListener(e -> { 
            Set<String> jugadoresUnicos = new HashSet<>();
            String fechaInicio = obtenerFechaSeleccionada(añoInicio, mesInicio);
            String fechaFinal = obtenerFechaSeleccionada(añoFinal, mesFinal);
            if (fechaInicio == null || fechaFinal == null) {
                JOptionPane.showMessageDialog(null, "Seleccione un rango de fechas válido.");
                return;
            }
            reporte.setText("-- Reporte por Fecha --");
            reporte.append("\n\n");
            try (BufferedReader br = new BufferedReader(new FileReader("Deportistas.txt"))) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length >= 7) {  
                        String nombre = datos[0];
                        String fecha = datos[5];  
                        String categoria = datos[6]; 
                        String numero = datos[3];
                        //Valida que la fecha está dentro del rango
                        if (fecha.compareTo(fechaInicio) >= 0 && fecha.compareTo(fechaFinal) <= 0) {
                            //adjunta al reporte solo los nombres que no se repiten
                            if (!jugadoresUnicos.contains(nombre)) { 
                                jugadoresUnicos.add(nombre);
                                reporte.append("Nombre: " + nombre + " | Número: " + numero + " | Fecha de ingreso: " + fecha + " | Categoría: " + categoria + "\n");
                            }
                        }
                    }
                }
                conteo = jugadoresUnicos.size();
                reporte.append("\nCantidad de registros encontrados -> " + conteo);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo.");
            }
        });
        panel.add(botonF);
        
        
        //Boton buscar por nombre
        botonN = new JButton("Buscar");
        botonN.setBounds(625,155, 80, 25);
        botonN.setFont(new Font("arial",1,12));
        //Filtra,cuenta e Imprime en el reporte, jugadores por nombre y entrenamientos asignados 
        botonN.addActionListener(e -> {
        String nombreBuscado = ctNombre.getText().trim();
            //Valida que el campo nombre no este vacio
            if (nombreBuscado.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un nombre válido.");
                return;
            }     
            //Usa bufer de almacenaje para guardar el archivo linea por linea
            try (BufferedReader br = new BufferedReader(new FileReader("Deportistas.txt"))) {
                String linea;
                StringBuilder resultados = new StringBuilder();
                conteo = 0;
                //lee cada linea del archivo mientras exista
                while ((linea = br.readLine()) != null) {
                    //Usa la funcion split para dividir la linea usando la ','
                    String[] datos = linea.split(",");
                    // Verifica que la línea tenga los datos suficientes
                    if (datos.length >= 6) {
                        String nombre = datos[0].trim();
                        //Compara los nombres
                        if (nombre.equalsIgnoreCase(nombreBuscado)) {
                            String altura = datos[1];
                            String posicion = datos[2];
                            String numero = datos[3];
                            String entrenamiento = datos[4];
                            String fecha = datos[5];
                            //Agrega los datos al Buffer
                            resultados.append("Nombre: ").append(nombre).append(" | Altura: ").append(altura).append(" | Posición: ")
                                      .append(posicion).append(" | Número: ").append(numero).append(" | \n")
                                      .append("Entrenamiento: ").append(entrenamiento).append(" Asigado el ").append(fecha).append("\n\n");
                            conteo++;
                        }
                    }
                }
                // Muestra los resultados en el área de texto
                if (conteo > 0) {
                    nombreInforme = "Reporte por nombre";
                    reporte.setText("--" + nombreInforme + "--\n" + 
                                    "Cantidad de registros encontrados -> " + conteo + "\n\n" + 
                                    resultados.toString());
                } else {
                    reporte.setText("--" + nombreInforme + "--\nNo se encontraron coincidencias.");
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo.");
            }
        });
        panel.add(botonN);
        
        
        //Boton buscar por Categoria
        botonC = new JButton("Buscar");
        botonC.setBounds(640,200, 65, 25);
        botonC.setFont(new Font("arial",1,8));
        //Filtra,cuenta e Imprime en el reporte, jugadores por categoria o subcategoria y fecha 
        botonC.addActionListener(e -> {
            String categoriaSeleccionada = (String) categorias.getSelectedItem();
            String fechaInicio = obtenerFechaSeleccionada(añoInicio, mesInicio);
            String fechaFinal = obtenerFechaSeleccionada(añoFinal, mesFinal);
            if (fechaInicio == null || fechaFinal == null) {
                JOptionPane.showMessageDialog(null, "Seleccione un rango de fechas válido.");
                return;
            }
            if (categoriaSeleccionada == null || categoriaSeleccionada.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Seleccione una categoría válida.");
                return;
            }
            reporte.setText("-- Reporte por Categoria --");
            reporte.append("\n\n");
            try (BufferedReader br = new BufferedReader(new FileReader("Deportistas.txt"))) { 
                String linea;
                Set<String> nombresUnicos = new HashSet<>();
                while ((linea = br.readLine()) != null) {
                    String[] datos = linea.split(",");
                    if (datos.length >= 8) {
                        String nombre = datos[0].trim();
                        String numero = datos[3].trim();
                        String categoria = datos[6].trim();
                        String subcategoria = datos[7].trim();
                        String fecha = datos[5].trim();
                        if (fecha.compareTo(fechaInicio) >= 0 && fecha.compareTo(fechaFinal) <= 0) {
                            // Verifica si la categoría coincide con la selección del JComboBox
                            boolean esCategoriaValida = categoriaSeleccionada.equals("Primer Equipo") ? categoria.equalsIgnoreCase("Primer Equipo") : subcategoria.equalsIgnoreCase(categoriaSeleccionada);
                            if (esCategoriaValida && !nombresUnicos.contains(nombre)) {
                                nombresUnicos.add(nombre);
                                if(categoriaSeleccionada.equals("Primer Equipo")){
                                    reporte.append("Nombre: " + nombre + " | Número: " + numero  + " | Categoría: " + categoria + "\n");
                                    conteo++;
                                }else{
                                    reporte.append("Nombre: " + nombre + " | Número: " + numero  + " | Categoría: " + subcategoria + "\n");
                                    conteo++;
                                }
                            }
                        }
                    }
                }
                conteo = nombresUnicos.size();
                reporte.append("\nCantidad de registros encontrados -> " + conteo);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error al leer el archivo.");
            }
        });
        panel.add(botonC);
        
        
    }
    
    //Crea las etiquetas y listas de fecha de inicio
    private void colocarEtiquetaSelecInicio() {
        //Etiqueta fecha Desde
        etFechaD = new JLabel();
        etFechaD.setOpaque(true);
        etFechaD.setText("Seleciona un año y un mes de inicio");
        etFechaD.setBounds(395, 20, 180, 25);
        etFechaD.setHorizontalAlignment(SwingConstants.CENTER );
        etFechaD.setFont(new Font("arial",1,10));
        panel.add(etFechaD);
        //Lista desplegable Año de inicio
        añoInicio = new JComboBox<>();
        añoInicio.addItem("2020");
        añoInicio.addItem("2021");
        añoInicio.addItem("2022");
        añoInicio.addItem("2023");
        añoInicio.addItem("2024");
        añoInicio.addItem("2025");
        añoInicio.setBounds(575,20, 65, 25);
        panel.add(añoInicio);
        //Lista desplegable mes de inicio
        mesInicio = new JComboBox<>();
        mesInicio.addItem("ENE");
        mesInicio.addItem("FEB");
        mesInicio.addItem("MAR");
        mesInicio.addItem("ABR");
        mesInicio.addItem("MAY");
        mesInicio.addItem("JUN");
        mesInicio.addItem("JUL");
        mesInicio.addItem("AGO");
        mesInicio.addItem("SET");
        mesInicio.addItem("OCT");
        mesInicio.addItem("NOV");
        mesInicio.addItem("DIC");
        mesInicio.setBounds(640,20, 65, 25);
        panel.add(mesInicio);
        
    }

    //Crea las etiquetas y listas de fecha de Final
    private void colocarEtiquetaSelecFinal() {
        //Etiqueta fecha hasta
        etFechaH = new JLabel();
        etFechaH.setOpaque(true);
        etFechaH.setText("Seleciona un año y un mes final");
        etFechaH.setBounds(395, 65, 180, 25);
        etFechaH.setHorizontalAlignment(SwingConstants.CENTER );
        etFechaH.setFont(new Font("arial",1,10));
        panel.add(etFechaH);
        //Lista desplegable Año Final
        añoFinal = new JComboBox<>();
        añoFinal.addItem("2020");
        añoFinal.addItem("2021");
        añoFinal.addItem("2022");
        añoFinal.addItem("2023");
        añoFinal.addItem("2024");
        añoFinal.addItem("2025");
        añoFinal.setSelectedIndex(5);
        añoFinal.setBounds(575,65, 65, 25);
        panel.add(añoFinal);
        //Lista desplegable mes Final
        mesFinal = new JComboBox<>();
        mesFinal.addItem("ENE");
        mesFinal.addItem("FEB");
        mesFinal.addItem("MAR");
        mesFinal.addItem("ABR");
        mesFinal.addItem("MAY");
        mesFinal.addItem("JUN");
        mesFinal.addItem("JUL");
        mesFinal.addItem("AGO");
        mesFinal.addItem("SET");
        mesFinal.addItem("OCT");
        mesFinal.addItem("NOV");
        mesFinal.addItem("DIC");
        mesFinal.setSelectedIndex(11);
        mesFinal.setBounds(640,65, 65, 25);
        panel.add(mesFinal);
        
        
    }
    
    //Crea la etiqueta y la caja de texto para Nombre
    private void colocarConjuntoNombre() {   
        //Etiqueta Nombre
        etNombre = new JLabel();
        etNombre.setOpaque(true);
        etNombre.setText("Escribe el nombre del jugador");
        etNombre.setBounds(395, 155, 160, 25);
        etNombre.setHorizontalAlignment(SwingConstants.CENTER );
        etNombre.setFont(new Font("arial",1,10));
        panel.add(etNombre);
        //Caja de texto Nombre permite insertar el nombre del jugador
        ctNombre = new JTextField();
        ctNombre.setBounds(555,155,70,25);
        ctNombre.setFont(new Font("arial",0,12));
        panel.add(ctNombre); 
    }
    
    //Crea La etiqueta y la lista de seleccion para Categorias
    private void colocarConjuntoCategoria() {
        //Etiqueta Categoria
        etCategoria = new JLabel();
        etCategoria.setOpaque(true);
        etCategoria.setText("Seleciona una categoria a bucar");
        etCategoria.setBounds(395, 200, 160, 25);
        etCategoria.setHorizontalAlignment(SwingConstants.CENTER );
        etCategoria.setFont(new Font("arial",1,10));
        panel.add(etCategoria);
        //Lista desplegable pasara seleccion de categorias
        categorias = new JComboBox<>();
        categorias.addItem("");
        categorias.addItem("Primer Equipo");
        categorias.addItem("Menor");
        categorias.addItem("-U15-");
        categorias.addItem("-U17-");
        categorias.addItem("-U21-");
        categorias.setBounds(555,200, 85, 25);
        panel.add(categorias);  
    }
    
    //Convierte el formato de texto de la lista para poder realizar la validacion
    private String obtenerFechaSeleccionada(JComboBox<String> año, JComboBox<String> mes) {
    String ano = (String) año.getSelectedItem();
    String mesSeleccionado = (String) mes.getSelectedItem();
    if (ano == null || mesSeleccionado == null || ano.isEmpty() || mesSeleccionado.isEmpty()) {
        return null;
    }
    // Mapa para convertir el mes de texto a número
    Map<String, String> meses = new HashMap<>();
    meses.put("ENE", "01");
    meses.put("FEB", "02");
    meses.put("MAR", "03");
    meses.put("ABR", "04");
    meses.put("MAY", "05");
    meses.put("JUN", "06");
    meses.put("JUL", "07");
    meses.put("AGO", "08");
    meses.put("SET", "09");
    meses.put("OCT", "10");
    meses.put("NOV", "11");
    meses.put("DIC", "12");
    return ano + "-" + meses.get(mesSeleccionado);
}
       
}
