
package Graficas;

import Fut_Training_app.MenuControl;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;


//Clase Inicio, crea la interfaz grafica para dar una bienvebida agradable al cliente y seleccionar la accion que quiere ejecutar
public class Inicio extends JFrame {
    
    private JPanel pane1;
    private JLabel etiquetaT, etiquetaG, etiquetaI, etiquetaM;
    private JButton botonN, botonE;
    private JComboBox<String> opciones;
    
    //Constructor de la ventana
    public Inicio(){
        setSize(500,550);
        setTitle("Pantalla de Inicio");
        iniciarComponentes();//Inicia los componenetes de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    } 
    
    //Maneja los componentes de la ventana
    private void iniciarComponentes(){
        colocarPaneles();
        colocarEtiquetas();
        colocarBoton();
        colocarListaD();
    }
    
    //Crea el panel
    private void colocarPaneles(){
        pane1 = new JPanel();//Instancia el panel
        pane1.setBackground(Color.WHITE);
        pane1.setLayout(null);//Desactiva el formato predeterminado
        getContentPane().add(pane1);//Agrega el panel a la ventana 
    }
    
    //Crea las etiquetas Superior, imagen, inferior y menu
    private void colocarEtiquetas(){
        //Etiqueta Superior
        etiquetaT = new JLabel();//Crea la etiqueta
        etiquetaT.setOpaque(true);//Habilita la pintura de fondo en las etiquetas
        etiquetaT.setText("Programa de Entrenamiento");//Introduce texto
        etiquetaT.setBounds(50, 10, 380, 70);//Posicion y tamaño
        etiquetaT.setHorizontalAlignment(SwingConstants.CENTER );//Se alinea el texto, tambien se puede definir en el constructor
        etiquetaT.setForeground(Color.BLUE);//Color de la letra
        etiquetaT.setBackground(Color.WHITE);//Color del fondo
        etiquetaT.setFont(new Font("cooper black",0,25));//Dar formato al texto 
        pane1.add(etiquetaT);//Agrega la eqtiqueta al Panel
        //Logo
        ImageIcon imagen = new ImageIcon(getClass().getResource("/Graficas/balon.png"));//Crea la imagen con la direccion
        etiquetaG = new JLabel();
        etiquetaG.setSize(180,160);//le da un tamaño a ña etiqueta
        etiquetaG.setLocation(145,70); //Posiciona la etiqueta
        etiquetaG.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(etiquetaG.getWidth(), etiquetaG.getHeight(),Image.SCALE_SMOOTH)));//crea un objeto ImageIcon asigba la imagen y le da nuevas dimenciones
        pane1.add(etiquetaG);
        //Etiqueta Inferior
        etiquetaI = new JLabel();
        etiquetaI.setOpaque(true);
        etiquetaI.setText("Bienvenidos");
        etiquetaI.setBounds(85, 220, 300, 70);
        etiquetaI.setHorizontalAlignment(SwingConstants.CENTER );
        etiquetaI.setForeground(Color.BLUE);
        etiquetaI.setBackground(Color.WHITE);
        etiquetaI.setFont(new Font("cooper black",0,30)); 
        pane1.add(etiquetaI);
        //Etiqueta Menu
        etiquetaM = new JLabel();
        etiquetaM.setOpaque(true);
        etiquetaM.setText("Selecciona una Opcion");
        etiquetaM.setBounds(45, 300, 200, 30);
        etiquetaM.setHorizontalAlignment(SwingConstants.CENTER );
        etiquetaM.setFont(new Font("arial",0,15));
        pane1.add(etiquetaM);
    }
    
    //Crea los botones y definie sus eventos
    private void colocarBoton(){   
        //Boton de continuar
        botonN = new JButton("Continuar");
        botonN.setBounds(85, 450, 100, 25);
        botonN.setFont(new Font("arial",0,15));
        botonN.addActionListener(e ->  { 
            MenuControl.abrirVentana((String) opciones.getSelectedItem());
            dispose();    
            });//Redirecciona y usa la clase MenuControl para manejar el menu.
        pane1.add(botonN);     
        //Boton de salida
        botonE = new JButton("Salir");
        botonE.setBounds(290, 450, 100, 25);
        botonE.setFont(new Font("arial",0,15));
        botonE.addActionListener(e -> System.exit(0));//Cierra el programa
        pane1.add(botonE);
    }
    
    //Crea la lista desplegable
    private void colocarListaD() {
        
        opciones = new JComboBox<>();
        //opciones.addItem("...");
        opciones.addItem("Ingresar Datos");
        opciones.addItem("Entrenamientos");
        opciones.addItem("Reportes");
        opciones.setBounds(245, 300, 200, 30);
        pane1.add(opciones);
        
    }
      
}
