package uniandes.dpoo.taller4.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import uniandes.dpoo.taller4.modelo.RegistroTop10;
import uniandes.dpoo.taller4.modelo.Tablero;
import uniandes.dpoo.taller4.modelo.Top10;



public class Vista extends JFrame {
	Tablero tablero;
    Tablero tablero_viejo;
    JFrame frame;
	int size_tablero;
    int dificultad;
    boolean choose1;
    boolean choose2;
	JPanel panel_tablero;
    JPanel panel_superior;
    JPanel panel_inferior;
    JPanel panel_derecho;
    static String nomJugador;
    static Top10 top10;
	private static final long serialVersionUID = 1L;
    private static Vista onlyVista;
    private static Collection<RegistroTop10> registros;
	public Vista(){
        nomJugador="";
        top10=new Top10();
        registros=top10.darRegistros();
        frame = new JFrame("LightsOut");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel_tablero= new JPanel();
        choose1=false;
        choose2=false;

        //Panel Superior
        panel_superior = new JPanel();
        panel_superior.setLayout(new BoxLayout(panel_superior, BoxLayout.X_AXIS)); 
        panel_superior.add(new JLabel("Size:"));
        //JTextField tamanio_u= new JTextField();
        //tamanio_u.setPreferredSize(new Dimension(40, 30)); 
        //tamanio_u.setToolTipText("Ingresa tu nombre");
        //panelSuperior.add(tamanio_u);
        String[] opciones = {"3x3", "4x4", "5x5", "6x6","7x7","8x8","9x9","10x10"};
        JComboBox<String> comboBox = new JComboBox<>(opciones);
        comboBox.addActionListener(e -> {
            String seleccion = (String) comboBox.getSelectedItem();
            size_tablero= Integer.parseInt(seleccion.split("x")[0]);
            choose1=true;


        });
        panel_superior.add(comboBox);
        panel_superior.add(new JLabel("Difultad:"));
        ButtonGroup buttonGroup = new ButtonGroup();
        JRadioButton facil=(new JRadioButton("Facil"));
        JRadioButton intermedio=(new JRadioButton("Intermedio"));
        JRadioButton dificil=(new JRadioButton("Dificil"));
        facil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dificultad=1;
                choose2=true;

            }
        });

        intermedio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dificultad=2;
                choose2=true;

            }
        });
        dificil.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dificultad=3;
                choose2=true;

            }
        });
        buttonGroup.add(facil);
        buttonGroup.add(intermedio);
        buttonGroup.add(dificil);
    
        panel_superior.add(facil);
        panel_superior.add(intermedio);
        panel_superior.add(dificil);

        frame.setLayout(new BorderLayout());
        frame.add(panel_superior,BorderLayout.NORTH);

        //Panel Inferior
        panel_inferior=new JPanel();
        //panelSuperior.setLayout(new BoxLayout(panel_inferior, BoxLayout.X_AXIS)); 
        actualizarPanelInferior(0);
        //Panel derecho
        panel_derecho= new JPanel();
        panel_derecho.setLayout(new BoxLayout(panel_derecho, BoxLayout.Y_AXIS)); 
        JButton botonNuevo= new JButton("Nueva Partida");
        botonNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (choose1&&choose2){
                inicializarTablero();
                tablero_viejo=tablero;
                }
                choose1=false;
                choose2=false;
            }
        });
        JButton botonReiniciar= new JButton("Reiniciar Partida(doble click)");
        botonReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                panel_tablero.remove(0);
                panel_tablero= new TableroDeJuego(tablero_viejo, size_tablero).getPanel();
                frame.add(panel_tablero,BorderLayout.WEST);
                tablero.reiniciar();
                frame.setVisible(true);
                }
        });
        JButton botonTop10= new JButton("Ver Top 10");
        botonTop10.addActionListener(new ActionListener(){
        JDialog jdialog= new JDialog(frame,"Top 10");
        public void actionPerformed(ActionEvent e){
            for (RegistroTop10 i: registros){
                jdialog.add(new Label(i.darNombre()+": "+Integer.toString(i.darPuntos())));
            }
            jdialog.setVisible(true);
        }});
        JButton botonCambiarJugador= new JButton("Cambiar de Jugador");
        botonCambiarJugador.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            nomJugador = showInputDialog(frame);
                inicializarTablero();
                actualizarPanelInferior(0);
            }
        }); 
        botonNuevo.setPreferredSize(new Dimension(155, 60)); // Establecer el tamaÃÂ±o preferido del botÃÂ³n
        botonReiniciar.setPreferredSize(new Dimension(205, 60)); // Establecer el tamaÃÂ±o preferido del botÃÂ³n
        botonTop10.setPreferredSize(new Dimension(155, 60)); // Establecer el tamaÃÂ±o preferido del botÃÂ³n
        botonCambiarJugador.setPreferredSize(new Dimension(155, 60)); // Establecer el tamaÃÂ±o preferido del botÃÂ³n
        panel_derecho.add(botonCambiarJugador);

        panel_derecho.add(botonNuevo);
        panel_derecho.add(botonReiniciar);
        panel_derecho.add(botonTop10);
        panel_derecho.add(botonCambiarJugador);


        
        frame.add(panel_derecho,BorderLayout.EAST);
        frame.setSize(500,500);
        frame.setVisible(true);
        onlyVista=this;
	}
        public static String showInputDialog(JFrame parentFrame) {
        // Crear un JDialog modal
        JDialog dialog = new JDialog(parentFrame, "Entrada de Usuario", true);
        dialog.setSize(300, 150);

        // Crear un panel para contener los componentes
        JPanel panel = new JPanel();

        // Agregar un JLabel y un JTextField al panel
        JLabel label = new JLabel("Ingrese su entrada:");
        JTextField textField = new JTextField(20);
        panel.add(label);
        panel.add(textField);

        // Agregar un botÃÂ³n "Aceptar" al panel
        JButton acceptButton = new JButton("Aceptar");
        acceptButton.addActionListener(e -> {
            // Cuando se hace clic en el botÃÂ³n "Aceptar", oculta el diÃÂ¡logo
            dialog.setVisible(false);
        });
        panel.add(acceptButton);

        // Agregar el panel al diÃÂ¡logo
        dialog.getContentPane().add(panel);

        // Mostrar el diÃÂ¡logo modal
        dialog.setVisible(true);

        // Cuando se cierra el diÃÂ¡logo, retorna el texto ingresado
        return textField.getText();
    }
    public void inicializarTablero(){
        tablero=new Tablero(size_tablero);
        tablero.desordenar(dificultad);
        panel_tablero= new TableroDeJuego(tablero,size_tablero).getPanel();
        frame.add(panel_tablero,BorderLayout.WEST);
        tablero.reiniciar();
        frame.setVisible(true);
    }

    public void actualizarPanelInferior(int n){
        panel_inferior.removeAll();
        panel_inferior.add(new JLabel("Jugadas:"));
        if (n>0){panel_inferior.add(new JLabel(Integer.toString(n)));}
        else{panel_inferior.add(new JLabel(Integer.toString(0)));}
        panel_inferior.add(new JLabel("Jugador:"));
        if (nomJugador.equals("")){panel_inferior.add(new JLabel("No disponible"));}
        else{panel_inferior.add(new JLabel(nomJugador));};

        
        frame.add(panel_inferior,BorderLayout.SOUTH);
        frame.setVisible(true);


    }
    public static Vista getVista(){
        return onlyVista;
    }
    public static String getNomJugador(){
        return nomJugador;
    }
    public static Top10 getTop10(){
        return top10;
    }
    public static void main(String[] args) {
        
    	Vista vista= new Vista();
    }
}