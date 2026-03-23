
package Graficas;

import Fut_Training_app.CategoriaMenor;
import Fut_Training_app.Deportista;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import Fut_Training_app.MenuControl;
import Fut_Training_app.PrimerEquipo;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

//Clase Formulario crea la Interfaz grafica para ingresar los datos
public class FormularioDeportista extends JFrame {
    
    private JPanel panel;
    private JLabel etnumero, etnombre, etaltura, etposicion, etfecha, etEntre,etCategoria,etPeso,etPorGrasa, etSubCat;
    private JButton botonC, botonV;
    private JTextField ctNombre, ctNumeroJ, ctEstatura, ctfecha, ctPeso, ctPorGrasa ;
    private JComboBox<String> posicion,entrenamientoMayor,entrenamientoMenor, categoria;
    private JRadioButton ctCategoriaE, ctCategoriaE2 ; 
    
    //Constructor de la clase
    public FormularioDeportista(){
        
        setBounds(50,50,1000,500);
        setTitle("Ingreso de datos");
        iniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        asignarFechaActual();
    }
    
    //Metodo para colocar los componentes en la ventana
    private void iniciarComponentes(){
        
        colocarPaneles();
        colocarBotones();
        colocarCajaTexto();
        colocarEtiqueta();
        ColocarCamposBotonE();
        colocarBotonesR();
        colocarListaD();
        
    }
    
    //Crea el panel
    private void colocarPaneles() {
        panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);
        getContentPane().add(panel);
    }
    
    //Crea los botones y sus funcionalidades
    private void colocarBotones() {
        //Boton de Volver, regresa al menu de seleccion
        botonV = new JButton("Regresar");
        botonV.setBounds(30, 410, 100, 25);
        botonV.setFont(new Font("arial",1,14));
        botonV.addActionListener(e -> {
        new Inicio().setVisible(true);
        dispose();
        });
        panel.add(botonV);
        //Boton de Crear
        botonC = new JButton("Crear");
        botonC.setBounds(860, 410, 100, 25);
        botonC.setFont(new Font("arial",1,14));
        botonC.addActionListener(e -> {
            //Variables generales
            String numeroJugador = ctNumeroJ.getText().trim();
            String nombre = ctNombre.getText().trim();
            String estaturaTexto = ctEstatura.getText().trim();
            String posicionSeleccionada = (String) posicion.getSelectedItem();
            String fecha = ctfecha.getText().trim();
            String entrenamientoSeleccionado = ctCategoriaE.isSelected() ? (String) entrenamientoMayor.getSelectedItem() : (String) entrenamientoMenor.getSelectedItem();
            // Validar datos básicos
            if (!MenuControl.validarDatos(numeroJugador, nombre, estaturaTexto, posicionSeleccionada)) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos correctamente.");
                return;
            }
            //Convierte las variables segun las necesidad de las subclases 
            int altura = Integer.parseInt(estaturaTexto);
            int numero = Integer.parseInt(numeroJugador);
            //Intancia un objeto de la clase padre
            Deportista nuevoDeportista;
            //Controla la sub clase PrimerEquipo
            if (ctCategoriaE.isSelected()) {
                //Variables de la sub clase
                String pesoTexto = ctPeso.getText().trim();
                String porGrasaTexto = ctPorGrasa.getText().trim();
                //Valida datos especificos de la subclase
                if (!MenuControl.validarDatosPrimerEquipo(pesoTexto, porGrasaTexto)) {
                    JOptionPane.showMessageDialog(null, "Ingrese datos válidos para peso y porcentaje de grasa.");
                    return;
                }
                int peso = Integer.parseInt(pesoTexto);
                int porcentajeGrasa = Integer.parseInt(porGrasaTexto);
                //Crea el objeto de la subclase
                nuevoDeportista = new PrimerEquipo(peso, porcentajeGrasa, numero , nombre, altura, posicionSeleccionada,fecha,entrenamientoSeleccionado );
            // Controla la subclase CategoriaMenor    
            } else { 
                String descripcionCategoria = (String) categoria.getSelectedItem(); // Obtener la descripción de la categoría menor
                if (descripcionCategoria == null || descripcionCategoria.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Seleccione una subcategoría válida.");
                    return;
                }
                nuevoDeportista = new CategoriaMenor(descripcionCategoria, numero, nombre, altura, posicionSeleccionada,fecha, entrenamientoSeleccionado  );
            }
                //Usa el Metodo Guardar archivo para crear el .txt 
                guardarDeportistaEnArchivo(nuevoDeportista);
        });
        panel.add(botonC);
    }
    
    //Crea las cajas de texto numero,nombre y altura
    private void colocarCajaTexto() {
        //Caja de texto Numero permite ingresar un numero al jugador
        ctNumeroJ = new JTextField();
        ctNumeroJ.setBounds(280,80,30,30);
        ctNumeroJ.setFont(new Font("arial",0,16));
        panel.add(ctNumeroJ);
        //Caja de texto Nombre permite ingresar un numero al jugador
        ctNombre = new JTextField();
        ctNombre.setBounds(480,80,100,30);
        ctNombre.setFont(new Font("arial",0,16));
        panel.add(ctNombre);
        //Caja de texto Altura permite ingresar La altura de un jugador
        ctEstatura = new JTextField();
        ctEstatura.setBounds(180,130,50,30);
        ctEstatura.setFont(new Font("arial",0,16));
        panel.add(ctEstatura);
    }

    //Crea las etiquetas numero,nombre,categoria,atura y entrenamientos
    private void colocarEtiqueta() {
        //Etiqueta Ingresar Numero
        etnumero = new JLabel();
        etnumero.setOpaque(true);
        etnumero.setText("Ingresa un numero de jugador");
        etnumero.setBounds(30, 80, 250, 30);
        etnumero.setHorizontalAlignment(SwingConstants.CENTER );
        etnumero.setFont(new Font("arial",1,16));
        panel.add(etnumero);
        //Etiqueta Ingresar Nombre
        etnombre = new JLabel();
        etnombre.setOpaque(true);
        etnombre.setText("Nombre del atleta");
        etnombre.setBounds(330, 80, 150, 30);
        etnombre.setHorizontalAlignment(SwingConstants.CENTER );
        etnombre.setFont(new Font("arial",1,16));
        panel.add(etnombre);
        //Etiqueta Ingresar Categoria
        etCategoria = new JLabel();
        etCategoria.setOpaque(true);
        etCategoria.setText("Selecciona una categoria");
        etCategoria.setBounds(30, 30, 200, 30);
        etCategoria.setHorizontalAlignment(SwingConstants.CENTER );
        etCategoria.setFont(new Font("arial",1,16));
        panel.add(etCategoria);
        //Etiqueta Ingresar Altura
        etaltura = new JLabel();
        etaltura.setOpaque(true);
        etaltura.setText("Altura en CM");
        etaltura.setBounds(30, 130, 150, 30);
        etaltura.setHorizontalAlignment(SwingConstants.CENTER );
        etaltura.setFont(new Font("arial",1,16));
        panel.add(etaltura);
        //Etiqueta Entrenamientos
        etEntre = new JLabel();
        etEntre.setOpaque(true);
        etEntre.setText("Selecciona un entrenamiento");
        etEntre.setBounds(330, 130, 250, 30);
        etEntre.setHorizontalAlignment(SwingConstants.CENTER );
        etEntre.setFont(new Font("arial",1,16));
        panel.add(etEntre);
    }
    
    //crea los botones de radio y sus funcionalidades de control de la lista desplegable
    private void colocarBotonesR() {
        // Botón de radio para "Primer Equipo"
        ctCategoriaE = new JRadioButton("Primer Equipo", false);
        ctCategoriaE.setBounds(230, 30, 140, 30);
        panel.add(ctCategoriaE);
        // Botón de radio para "Categoría Menor"
        ctCategoriaE2 = new JRadioButton("Categoría Menor", false);
        ctCategoriaE2.setBounds(370, 30, 140, 30);
        panel.add(ctCategoriaE2);
        // Agrupar los botones de radio
        ButtonGroup seleccion1 = new ButtonGroup();
        seleccion1.add(ctCategoriaE);
        seleccion1.add(ctCategoriaE2);
        // Agregar evento para cambiar la visibilidad de los campos y controla la lista desplegable Tipos de entrenamientos
        ctCategoriaE.addActionListener(e -> {
            toggleCamposAdicionales(true);
            toggleCamposAdicionalesC(false);
            MenuControl.cargarEntrenamientosMayor(entrenamientoMayor);
        });
        ctCategoriaE2.addActionListener(e -> {
            toggleCamposAdicionales(false);
            toggleCamposAdicionalesC(true);
            ctPeso.setText("");
            ctPorGrasa.setText("");
            MenuControl.cargarEntrenamientosMenor(entrenamientoMenor);
        });
        // Estado inicial si se decide poner una categoria predeterminada al inicio
        //toggleCamposAdicionales(ctCategoriaE.isSelected());
    }
    
    //Metodo para controlar los campos del primer equipo
    private void toggleCamposAdicionales(boolean mostrar) {
        etPeso.setVisible(mostrar);
        etPorGrasa.setVisible(mostrar);
        ctPeso.setVisible(mostrar);
        ctPorGrasa.setVisible(mostrar);
        entrenamientoMayor.setVisible(mostrar);
    }
    
    //Metodo para controlar los campos de la categoria menor
    private void toggleCamposAdicionalesC(boolean mostrar){
        categoria.setVisible(mostrar);
        etSubCat.setVisible(mostrar);
        entrenamientoMenor.setVisible(mostrar);
    }
  
    //Crea las listas desplegables, etiquetas y campos extra de categorias para usar con BotonesR
    private void ColocarCamposBotonE(){
        
        //Etiqueta Ingresar peso
        etPeso = new JLabel();
        etPeso.setOpaque(true);
        etPeso.setText("Peso en libras");
        etPeso.setBounds(30, 180, 150, 30);
        etPeso.setHorizontalAlignment(SwingConstants.CENTER );
        etPeso.setFont(new Font("arial",1,16));
        panel.add(etPeso);
        etPeso.setVisible(false);
        
        //Caja de texto Peso permite ingresar el peso de un jugador
        ctPeso = new JTextField();
        ctPeso.setBounds(180,180,50,30);
        ctPeso.setFont(new Font("arial",0,16));
        panel.add(ctPeso);
        ctPeso.setVisible(false);
        
        //Etiqueta Porcentaje de grasa
        etPorGrasa = new JLabel();
        etPorGrasa.setOpaque(true);
        etPorGrasa.setText("Porcentaje de grasa");
        etPorGrasa.setBounds(250, 180, 180, 30);
        etPorGrasa.setHorizontalAlignment(SwingConstants.CENTER );
        etPorGrasa.setFont(new Font("arial",1,16));
        panel.add(etPorGrasa);
        etPorGrasa.setVisible(false);
        
        //Caja de texto Porcentaje permite ingresar el porcentaje de grasa de un jugador
        ctPorGrasa = new JTextField();
        ctPorGrasa.setBounds(430,180,50,30);
        ctPorGrasa.setFont(new Font("arial",0,16));
        panel.add(ctPorGrasa);
        ctPorGrasa.setVisible(false);
        
        //Lista Desplegable Categoria Mayor
        entrenamientoMayor = new JComboBox<>();
        entrenamientoMayor.setBounds(580, 130, 230, 30);
        panel.add(entrenamientoMayor);
        entrenamientoMayor.setVisible(false);
        // Lista desplegable Categoría Menor
        entrenamientoMenor = new JComboBox<>();
        entrenamientoMenor.setBounds(580, 130, 230, 30);
        panel.add(entrenamientoMenor);
        entrenamientoMenor.setVisible(false);
        
        
    }
    
    //Crea las listas desplegables y etiquetas de posicion,subCategoria 
    private void colocarListaD() { 
        //Etiqueta Seleccionar Posicion
        etposicion = new JLabel();
        etposicion.setOpaque(true);
        etposicion.setText("Selecciona una posicion");
        etposicion.setBounds(610, 80, 200, 30);
        etposicion.setHorizontalAlignment(SwingConstants.CENTER );
        etposicion.setFont(new Font("arial",1,16));
        panel.add(etposicion);
        //Lista desplegable posiciones
        posicion = new JComboBox<>();
        posicion.addItem("");
        posicion.addItem("DLT");
        posicion.addItem("MCP");
        posicion.addItem("DEF");
        posicion.addItem("POR");
        posicion.setBounds(810, 80, 60, 30);
        panel.add(posicion);
        //Etiqueta Seleccionar Categoria
        etSubCat = new JLabel();
        etSubCat.setOpaque(true);
        etSubCat.setText("Selecciona una sudcategoria");
        etSubCat.setBounds(30, 230, 240, 30);
        etSubCat.setHorizontalAlignment(SwingConstants.CENTER );
        etSubCat.setFont(new Font("arial",1,16));
        panel.add(etSubCat);
        etSubCat.setVisible(false);
        //Lista desplegable categorias
        categoria = new JComboBox<>();
        categoria.addItem("");
        categoria.addItem("Menor");
        categoria.addItem("-U15-");
        categoria.addItem("-U17-");
        categoria.addItem("-U21-");
        categoria.setBounds(270,230, 70, 30);
        panel.add(categoria);
        categoria.setVisible(false);
    }
    
    // Método para asignar la fecha actual automáticamente
    private void asignarFechaActual() {
        //Etiqueta Fecha
        etfecha = new JLabel();
        etfecha.setOpaque(true);
        etfecha.setText("Fecha");
        etfecha.setBounds(530, 30, 75, 30);
        etfecha.setHorizontalAlignment(SwingConstants.CENTER );
        etfecha.setFont(new Font("arial",1,16));
        panel.add(etfecha);
        //Caja de texto Fecha permite ver la fecha
        ctfecha = new JTextField();
        ctfecha.setBounds(605,30,100,30);
        ctfecha.setFont(new Font("arial",0,16));
        panel.add(ctfecha);
        //Inserta la fecha del sistema y le da formato con la clase formatoFecha
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        String fechaHoy = formatoFecha.format(new Date());
        ctfecha.setText(fechaHoy);
        ctfecha.setEditable(false);
    }
    
    //Metodo para guardar un deportista en .txt usando los metodos string de cada subclase
    private void guardarDeportistaEnArchivo(Deportista deportista) {
        try (FileWriter fw = new FileWriter("Deportistas.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
                pw.println(deportista.toString());
                JOptionPane.showMessageDialog(null, "Deportista creado exitosamente.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al guardar el deportista: " + ex.getMessage());
        }
    }

}
    
    
    
    
    
    
    

