
package Graficas;



import Fut_Training_app.Entrenamiento;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

//Clase para la interfaz grafica de manejo de la clase entrenamientos
public class FormularioEntrenamiento extends JFrame {
    
    private JPanel panel;
    private JLabel etCodigoE, etDescripcion, etCategoriaE,etiquetaX;
    private JButton botonC,botonE,botonB,botonX,botonV;
    private JTextField ctCodigoE, ctDescripcion,ctBuscar;
    private JRadioButton ctCategoriaE, ctCategoriaE2 ; 
    
    //Constructor de la clase
    public FormularioEntrenamiento(){
        
        setBounds(50,50,570,300);
        setTitle("Creador de Entrenamientos");
        iniciarComponentes();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
    }
    
    //Metodo para el inicio de componentes
    private void iniciarComponentes() {
        
        colocarPaneles();
        colocarBotones();
        colocarCajaTexto();
        colocarEtiqueta();
        colocarBotonesR();
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
        botonV.setBounds(30, 200, 100, 25);
        botonV.setFont(new Font("arial",1,14));
        botonV.addActionListener(e -> {
            new Inicio().setVisible(true);
            dispose();
        });
        panel.add(botonV);
        //Boton Crear, carga la informacion digitada a la Clase Entrenamientos
        botonC = new JButton("Crear");
        botonC.setBounds(160, 200, 100, 25);
        botonC.setFont(new Font("arial",1,14));
        botonC.addActionListener(e -> {
        //Variables Requeridas
        String codigoEntrenamiento = ctCodigoE.getText().trim();
        String descripcion = ctDescripcion.getText().trim();
        String categoriaEntrenamiento= ctCategoriaE.isSelected() ? "Primer Equipo" : "Categoria Menor";//la sentencia ? : iguala el valor de la variable si el botonR esta en true.
        //Condicional que valida que los campos restantes no esten vacios antes de cargar los datos    
        if (codigoEntrenamiento.isEmpty() || descripcion.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Todos los campos deben estar llenos.");
            return;
        }
            //Crea un nuevo objeto de la Clase Entrenamiento
            Entrenamiento entrenamiento = new Entrenamiento(codigoEntrenamiento, descripcion, categoriaEntrenamiento);
            //Usa el Metodo Guardar para enviar la informacion al TXT.
            entrenamiento.guardarEnArchivo();
            JOptionPane.showMessageDialog(null, "Entrenamiento guardado correctamente.");
        //Limpia la caja de texto para evitar tuplas repetidas.
        ctCodigoE.setText("");
        });
        panel.add(botonC);
        //Boton Editar, edita la informacion de la Clase
        botonE = new JButton("Editar");
        botonE.setBounds(290, 200, 100, 25);
        botonE.setFont(new Font("arial",1,14));
        botonE.addActionListener(e -> {
            String codigoBuscado = ctCodigoE.getText().trim();
            String nuevaDescripcion = ctDescripcion.getText().trim();
            String nuevaCategoria = ctCategoriaE.isSelected() ? "Primer Equipo" : "Categoria Menor";
            //Usa el Metodo editar entrenamientos para editar la clase
            boolean actualizado = Entrenamiento.editarEntrenamiento(codigoBuscado, nuevaDescripcion, nuevaCategoria);
            if (actualizado) {
                JOptionPane.showMessageDialog(null, "Entrenamiento actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Código no encontrado o error al editar.");
            }
        });
        panel.add(botonE);
        //Boton Borrar, Elimina el entrenamiento de la Clase
        botonB = new JButton("Borrar");
        botonB.setBounds(420, 200, 100, 25);
        botonB.setFont(new Font("arial",1,14));
        botonB.addActionListener(e -> {
            String codigoBuscado = ctCodigoE.getText().trim();

            if (codigoBuscado.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un código para borrar.");
                return;
            }
            int confirmacion = JOptionPane.showConfirmDialog(null, "¿Está seguro de eliminar este entrenamiento?","Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            //Usa la funcion showConfirmDialog, para confirmar el uso de el metodo borrar entrenamientos.
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean eliminado = Entrenamiento.borrarEntrenamiento(codigoBuscado);
                //Borra los campos de las cajas de texto o informa si el codigo no existe
                if (eliminado) {
                    JOptionPane.showMessageDialog(null, "Entrenamiento eliminado correctamente.");
                    ctCodigoE.setText(""); // Limpiar campos después de borrar
                    ctDescripcion.setText("");
                } else {
                    JOptionPane.showMessageDialog(null, "Código no encontrado.");
                }
            }
        });
        panel.add(botonB);
        
        //Boton Buscar, ejecuta la busqueda del entrenamiento, para poder Editarlo o Borrarlo
        botonX = new JButton("Buscar");
        botonX.setBounds(330, 30, 90, 30);
        botonX.setFont(new Font("arial",1,16));
        botonX.addActionListener(e -> {
        //Variable a utilizar
        String codigoBuscado = ctBuscar.getText().trim(); // Obtener el código ingresado
        //Vaida que el campo ctBuscar no este vacio
        if (codigoBuscado.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, ingresa un código.");
            return;
        }
        //Usa el metodo de la clase Entrenamiento para buscar el código
        boolean encontrado = Entrenamiento.buscarCodigo(codigoBuscado);
        //Informa al usuario el resultado
        if (encontrado) {
            JOptionPane.showMessageDialog(null, "El código " +codigoBuscado+ " Lo deseas editar?.");
            ctCodigoE.setText(codigoBuscado);
            ctDescripcion.setText("");
        } else {
            ctCodigoE.setText(codigoBuscado); //Asigna el código para que pueda ser usado
            JOptionPane.showMessageDialog(null, "Crea un nuevo entrenamiento.");
        }
        });
        
        panel.add(botonX);   
    }
    
    //Crea las cajas de textos codigo, descripcion y Buscar
    private void colocarCajaTexto() {
        //Caja de texto Buscar, permite ingresar el codigo a buscar
        ctBuscar = new JTextField();
        ctBuscar.setBounds(230,30,100,30);
        String textoFijo = "ENT";
        ctBuscar.setText(textoFijo);   
        // Evitar que el usuario borre el texto y lo fija
        ((AbstractDocument) ctBuscar.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if (offset >= textoFijo.length()) { //Permite escribir solo después del texto fijo
                    super.replace(fb, offset, length, text, attrs);
                }
            }
            @Override
            public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
                if (offset >= textoFijo.length()) { //Evita borrar el texto fijo
                    super.remove(fb, offset, length);
                }
            }
        });
        panel.add(ctBuscar);
        //Caja de texto Descripcion, permite al usuario dar una descripcion del entrenamiento
        ctDescripcion = new JTextField();
        ctDescripcion.setBounds(230,110,292,25);
        panel.add(ctDescripcion);
        //Caja de Texto Codigo, permite al usuario ingresar un nuevo codigo
        ctCodigoE = new JTextField();
        ctCodigoE.setBounds(230,70,100,25);
        ctCodigoE.setEditable(false);
        panel.add(ctCodigoE);
    }
    
    //Crea las etiquetas Codigo, nuevo codigo, descripcion y categorias
    private void colocarEtiqueta() {
        
        //Etiqueta Ingresar Codigo
        etiquetaX = new JLabel();
        etiquetaX.setOpaque(true);
        etiquetaX.setText("Ingresa un codigo");
        etiquetaX.setBounds(30, 30, 200, 30);
        etiquetaX.setHorizontalAlignment(SwingConstants.CENTER );
        etiquetaX.setFont(new Font("arial",1,16));
        panel.add(etiquetaX);
        //Etiqueta Nuevo Codigo
        etCodigoE = new JLabel();
        etCodigoE.setOpaque(true);
        etCodigoE.setText("Nuevo Codigo");
        etCodigoE.setBounds(30, 70, 200, 25);
        etCodigoE.setBackground(Color.cyan);
        etCodigoE.setHorizontalAlignment(SwingConstants.CENTER );
        etCodigoE.setFont(new Font("arial",1,14));
        panel.add(etCodigoE);
        //Etiqueta Descripcion
        etDescripcion = new JLabel();
        etDescripcion.setOpaque(true);
        etDescripcion.setText("Descripcion");
        etDescripcion.setBounds(30, 110, 200, 25);
        etDescripcion.setBackground(Color.cyan);
        etDescripcion.setHorizontalAlignment(SwingConstants.CENTER );
        etDescripcion.setFont(new Font("arial",1,14));
        panel.add(etDescripcion);
        //Etiqueta Categoria
        etCategoriaE = new JLabel();
        etCategoriaE.setOpaque(true);
        etCategoriaE.setText("Categoria");
        etCategoriaE.setBounds(30, 150, 200, 25);
        etCategoriaE.setBackground(Color.cyan);
        etCategoriaE.setHorizontalAlignment(SwingConstants.CENTER );
        etCategoriaE.setFont(new Font("arial",1,14));
        panel.add(etCategoriaE);
    }
    
    //Crea los botones tipo radio
    private void colocarBotonesR() {
        //Botones de rotacion aseguran que solo se cargen dos categorias posibles a la clase Entrenamientos 
        ctCategoriaE = new JRadioButton("Primer Equipo",true);
        ctCategoriaE.setBounds(240, 150, 140, 25);
        //ctCategoriaE.setEnabled(false);//Bloquea el eso del boton.
        ctCategoriaE.addActionListener(e -> JOptionPane.showMessageDialog(null, "Selecionaste la 'Primera Division'."));
        panel.add(ctCategoriaE);
        //Categoria menor
        ctCategoriaE2 = new JRadioButton("Categoria Menor",false);
        ctCategoriaE2.setBounds(380, 150, 140, 25);
        //ctCategoriaE.setEnabled(false);//Bloquea el eso del boton.
        ctCategoriaE2.addActionListener(e -> JOptionPane.showMessageDialog(null, "Selecionaste la 'Categoria Menor'."));
        panel.add(ctCategoriaE2);
        //Agrupa los botones para que funcionen de forma inversa
        ButtonGroup seleccion1 = new ButtonGroup();
        seleccion1.add(ctCategoriaE);
        seleccion1.add(ctCategoriaE2);    
    }
    
    
    
    
    
 }
