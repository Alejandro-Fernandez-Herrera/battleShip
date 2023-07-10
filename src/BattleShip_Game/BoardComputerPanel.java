package BattleShip_Game;

import javax.swing.*;
import java.awt.*;

/**
 * @brief Clase correspondiente a la vista del tablero del oponente
 * @autor Alejandro Fernández - José David Giraldo
 * @version 1.0.0
 * @date 2023-07-09
 */

public class BoardComputerPanel extends JPanel {
    private BackgroundPane panelTableroPosicion;
    private JLabel nombreTableroPosicion;
    private ModelBoard tableroPosicionOponente, tableroPrincipalOponente;
    private String abecedario[];

    // Constructor
    public BoardComputerPanel(){
        GridBagLayout gb = new GridBagLayout();
        this.setLayout(gb);
        this.setBackground(Color.CYAN);
        tableroPosicionOponente = new ModelBoard();
        tableroPrincipalOponente = new ModelBoard();
        abecedario = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        iniciar();
        modelTableroOponente();
    }

    // Establece la configuracion inicial del JComponent
    public void iniciar(){
        GridBagConstraints gbc = new GridBagConstraints();

        // Panel tablero posicion
        nombreTableroPosicion = new JLabel("T A B L E R O   P O S I C I O N");
        nombreTableroPosicion.setForeground(new Color(0, 0, 0, 230));
        nombreTableroPosicion.setFont(new Font(Font.MONOSPACED,Font.BOLD,15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(nombreTableroPosicion, gbc);

        panelTableroPosicion = new BackgroundPane();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0,15,0,15);
        this.add(panelTableroPosicion, gbc);
    }

    // JPanel con imagen para agregar las matrices
    public class BackgroundPane extends JPanel{
        private Image img;

        public BackgroundPane(){
            img = new ImageIcon(getClass().getResource("/Resources/mar.jpg")).getImage();
            this.setLayout(new GridLayout(11, 11));
            this.setPreferredSize(new Dimension(400, 400));
            this.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        }

        @Override
        protected void paintComponent(Graphics g){
            super.paintComponent(g);
            g.drawImage(img, 0, 0, this);
            revalidate();
            repaint();
        }
    }

    // Crea los tableros posicion y principal
    public void modelTableroOponente(){
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if(row == 0 && col == 0){
                    tableroPosicionOponente.getMatriz()[row][col] = new JLabel();
                    tableroPosicionOponente.getMatriz()[row][col].setOpaque(true);
                    tableroPosicionOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                    tableroPosicionOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                    tableroPrincipalOponente.getMatriz()[row][col] = new JLabel();
                    tableroPrincipalOponente.getMatriz()[row][col].setOpaque(true);
                    tableroPrincipalOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                    tableroPrincipalOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }else{
                    if(row == 0 && col > 0){
                        tableroPosicionOponente.getMatriz()[row][col] = new JLabel(abecedario[col-1], SwingConstants.CENTER);
                        tableroPosicionOponente.getMatriz()[row][col].setOpaque(true);
                        tableroPosicionOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                        tableroPosicionOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                        tableroPrincipalOponente.getMatriz()[row][col] = new JLabel(abecedario[col-1], SwingConstants.CENTER);
                        tableroPrincipalOponente.getMatriz()[row][col].setOpaque(true);
                        tableroPrincipalOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                        tableroPrincipalOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    }else{
                        if(row > 0 && col == 0){
                            tableroPosicionOponente.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            tableroPosicionOponente.getMatriz()[row][col].setOpaque(true);
                            tableroPosicionOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                            tableroPosicionOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                            tableroPrincipalOponente.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            tableroPrincipalOponente.getMatriz()[row][col].setOpaque(true);
                            tableroPrincipalOponente.getMatriz()[row][col].setBackground(Color.WHITE);
                            tableroPrincipalOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        }else{
                            tableroPosicionOponente.getMatriz()[row][col] = new JLabel();
                            tableroPosicionOponente.getMatriz()[row][col].setOpaque(false);
                            tableroPosicionOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                            tableroPrincipalOponente.getMatriz()[row][col] = new JLabel();
                            tableroPrincipalOponente.getMatriz()[row][col].setOpaque(false);
                            tableroPrincipalOponente.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        }
                    }
                }

                panelTableroPosicion.add(tableroPosicionOponente.getMatriz()[row][col]);
            }
        }
    }

    // Retorna el tablero ingresado
    public ModelBoard getTableroOponente(String _tablero){
        ModelBoard tablero = new ModelBoard();
        if(_tablero.equals("position")){
            tablero = tableroPosicionOponente;
        }else{
            if(_tablero.equals("principal")){
                tablero = tableroPrincipalOponente;
            }
        }
        return tablero;
    }
}
