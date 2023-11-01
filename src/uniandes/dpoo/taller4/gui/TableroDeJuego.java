package uniandes.dpoo.taller4.gui;
import javax.swing.*;
import uniandes.dpoo.taller4.modelo.Tablero;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class TableroDeJuego extends JFrame {
    private static JPanel panel;
    private static Tablero tablero;

    public TableroDeJuego(Tablero tablero_new,int n) {
        tablero=tablero_new;
        boolean [][] matriz= tablero.darTablero();
        panel= new JPanel(new GridLayout(n, n));
    	for (int i = 0; i < n; i++) {
            int fila=i;
            for (int j = 0; j <n ; j++) {
                int columna=j;
                boolean value = matriz[i][j];
                JButton button; 
                if (value){button=new JButton(new ImageIcon("data\\encendido.png"));}
                else{button=new JButton(new ImageIcon("data\\apagado.png"));}
                button.setPreferredSize(new Dimension(55, 60)); // Establecer el tamaño preferido del botón
                button.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    // Cuando se presiona el botón, obtén la posición i, j en la matriz
                    tablero.jugar(fila, columna);
                    Vista.getVista().actualizarPanelInferior(tablero.darJugadas());
                    actualizarBotones();
                    tablero.jugar(fila, columna);
                    if(tablero.tableroIluminado()){
                        Vista.getTop10().agregarRegistro(Vista.getNomJugador(), tablero.darJugadas());
                        tablero.reiniciar();
                        Vista.getVista().inicializarTablero();
                        //mostrar mensaje
                        //reiniciar tablero
                    }
                }});
                button.setRolloverEnabled(false);
                panel.add(button);
                

            }
        }

}
    public JPanel getPanel(){
        return panel;
    }
    public static void actualizarBotones(){
        boolean[][] matriz = tablero.darTablero();
        Component[] buttons = panel.getComponents();
        int index = 0;

        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                boolean value = matriz[i][j];
                JButton button = (JButton) buttons[index];

                if (value) {
                    button.setIcon(new ImageIcon("data\\encendido.png"));
                } else {
                    button.setIcon(new ImageIcon("data\\apagado.png"));
                }

                index++;
            }
        }
    }
    }

