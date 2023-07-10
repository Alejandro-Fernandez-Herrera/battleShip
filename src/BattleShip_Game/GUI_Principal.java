package BattleShip_Game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GUI_Principal extends JFrame {

    public static final String PATH = "/Resources/";
    public static final String HELP = "Battleship is a traditional game of strategy and luck, involving two participants (in this case, a player vs. the computer).\n" +
            "\nThe objective of the game is to be the first to sink the opponent's ships.\n" + "\nEach player has 2 boards consisting of 10 rows and 10 columns:\n" +
            "\n-> Position board: Represents your territory, where you will distribute your fleet before the game starts, and it is only for observation. You can see the position of your ships\nand the shots from your opponent on your territory, but you cannot make any changes or shots on it.\n" +
            "\n-> Main board: Represents the enemy's territory, where their fleet is deployed. This is where the player's moves (shots) take place, trying to sink the enemy's ships. This board\nwill appear on the player's screen once the game starts, and it will register all their moves, reflecting both the shots in the water and the hit and sunk ships so far.\n"
            + "\n\nEach player has a fleet of 9 ships of different sizes, so each ship will occupy a certain number of cells on the board:\n" +
            "\n• 1 aircraft carrier: occupies 4 cells\n" + "\n• 2 submarines: each occupies 3 cells\n" + "\n• 3 destroyers: each occupies 2 cells\n" + "\n• 4 frigates: each occupies 1 cell"
            + "\n\nEach ship can be placed horizontally or vertically on the position board.\n\nTerminology and Moves:\n\n" + "• Water: when a shot is fired at a cell where no enemy ship is placed. An X will appear on the player's main board. It's the opponent's turn.\n" +
            "\n• Hit: when a shot is fired at a cell where an enemy ship is located, occupying 2 or more cells, and only a part of the ship is destroyed. On the player's board, that part of the ship will\nappear with a mark indicating that it has been hit. The player gets to shoot again."
            + "\n• Sunk: if a shot is fired at a cell where a frigate (1 cell) or another ship with the rest of the cells hit is located, it will be sunk, i.e., that ship will be eliminated from the game. On the player's\nmain board, the complete ship will appear with a mark indicating that it has been sunk. The player can shoot again, as long as they have not sunk the entire enemy fleet, in which case they win.";

    public static final String CREDITS = "CREDITS\n" +
            "-> JOSE DAVID GIRALDO\n" +
            "-> ALEJANDRO FERNANDEZ";

    private Header headerProject;
    private JButton helpButton, creditsButton, startGameButton,opponentMovesButton, restartButton;
    private Listener listener;
    private ImageIcon team, help, enemy, play, directionInfo, restart,infoSentidos;
    private JPanel northPanel, southPanel, eastPanel, centerPanel;
    private BoardUserPanel panelBoard;
    private PaintUserFleet paintUserFleet;
    private FleetPanel navyPanel;
    private GUI_Secundaria opponentWindow;
    private int gameState; // 1 select ship, 2 select ship orientation, 3 select ship direction, 4 place ship on the board, 5 combat, 6 opponent's turn
    private Combat combate;
    private int sunkCounter; // Counter for sunk ships
    private Timer timer; // Sets the time it takes for the opponent to choose a cell
    private Image image;

    /**
     * Constructor of the GUI class
     */
    public GUI_Principal() {
        initGUI();

        // Default JFrame configuration
        this.setTitle("Battleship");
        this.setIconImage(image);
        this.setUndecorated(false);
        this.setSize(1300, 750);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method is used to set up the default JComponent Configuration,
     * create Listener and control Objects used for the GUI class
     */

    private void initGUI() {
        // Create opponent's window
        opponentWindow = new GUI_Secundaria(this);

        // JFrame icon
        image = new ImageIcon(getClass().getResource(PATH + "icon.png")).getImage();

        // Set up JFrame Container's Layout
        northPanel = new JPanel();
        southPanel = new JPanel();
        eastPanel = new JPanel();
        centerPanel = new JPanel();

        // Create panels
        northPanel.setBackground(Color.CYAN);
        southPanel.setBackground(Color.CYAN);
        eastPanel.setBackground(Color.CYAN);
        centerPanel.setBackground(Color.CYAN);

        southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 250, 5));
        northPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 5));
        eastPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 100, 60));
        centerPanel.setLayout(new GridLayout(1, 1, 0, 100));

        southPanel.setPreferredSize(new Dimension(100, 60));
        northPanel.setPreferredSize(new Dimension(100, 60));
        eastPanel.setPreferredSize(new Dimension(1000, 60));
        centerPanel.setPreferredSize(new Dimension(600, 100));

        this.add(northPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        this.add(eastPanel, BorderLayout.EAST);
        this.add(centerPanel, BorderLayout.CENTER);

        // Game state
        gameState = 1;

        // Create event listener
        listener = new Listener();

        // Create Fleet panel
        navyPanel = new FleetPanel();

        // Set up JComponents
        // Images
        team = new ImageIcon(getClass().getResource(PATH + "team.png"));
        help = new ImageIcon(getClass().getResource(PATH + "signo.png"));
        play = new ImageIcon(getClass().getResource(PATH + "play.png"));
        enemy = new ImageIcon(getClass().getResource(PATH + "enemy.png"));
        directionInfo = new ImageIcon(getClass().getResource(PATH + "info.png"));
        restart = new ImageIcon(getClass().getResource(PATH + "reiniciarIcon.png"));

        // JComponents for the top part
        // Title
        headerProject = new Header("B A T T L E S H I P", Color.CYAN);
        northPanel.add(headerProject, FlowLayout.LEFT);

        // Create help button
        helpButton = new JButton("", help);
        helpButton.addActionListener(listener);
        helpButton.setBackground(Color.CYAN);
        helpButton.setFocusable(false);
        helpButton.setBorder(null);
        northPanel.add(helpButton, FlowLayout.CENTER);

        // Create credits button
        creditsButton = new JButton("", team);
        creditsButton.addActionListener(listener);
        creditsButton.setBackground(Color.CYAN);
        creditsButton.setFocusable(false);
        creditsButton.setBorder(null);
        northPanel.add(creditsButton, FlowLayout.LEFT);

        // Add listener to the button explaining the buttons in the FleetPanel class
        navyPanel.getButtonExplanation().addActionListener(listener);

        // JComponents for the center part
        // Board
        panelBoard = new BoardUserPanel();
        paintUserFleet = new PaintUserFleet(panelBoard, navyPanel);
        eastPanel.add(panelBoard);

        // Fleet
        centerPanel.add(navyPanel);

        // JComponents for the bottom part
        // Create "Start Game" button
        startGameButton = new JButton("Start Game", play);
        startGameButton.addActionListener(listener);
        startGameButton.setBackground(Color.CYAN);
        startGameButton.setFocusable(false);
        startGameButton.setBorder(null);
        southPanel.add(startGameButton, FlowLayout.LEFT);

        // Create "Opponent's Moves" button
        opponentMovesButton = new JButton("Opponent's Moves", enemy);
        opponentMovesButton.addActionListener(listener);
        opponentMovesButton.setBackground(Color.CYAN);
        opponentMovesButton.setFocusable(false);
        opponentMovesButton.setBorder(null);
        southPanel.add(opponentMovesButton, FlowLayout.CENTER);

        // Create "Restart" button
        restartButton = new JButton("Restart", restart);
        restartButton.addActionListener(listener);
        restartButton.setBackground(Color.CYAN);
        restartButton.setFocusable(false);
        restartButton.setBorder(null);
        southPanel.add(restartButton, FlowLayout.CENTER);

        // Add listener to all buttons in all classes
        setButtonListener("remove");
        setVerticalHorizontal("remove");
        setOrientationVertical("remove");
        setOrientationHorizontal("remove");

        // Distribute opponent's fleet
        while (opponentWindow.getPintarFlotaOponente().totalShipCount() > 0) {
            opponentWindow.distribucionFlotaOponente();
        }

        // Object to invoke combat functions
        combate = new Combat(panelBoard, opponentWindow.getPanelTableroOponente());

        sunkCounter = 0;
        // Timer for opponent's turn
        timer = new Timer(2000, listener);
    }

    // Reinicia el juego
    public void reset() {
        this.dispose();
        GUI_Principal gui = new GUI_Principal();
    }

    /**
     * Main process of the Java program
     *
     * @param args Object used in order to send input data from command line when
     *             the program is execute by console.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            GUI_Principal miProjectGUI = new GUI_Principal();
        });
    }

    // Agrega o remueve el escucha al boton de cada barco
    public void setButtonListener(String evento) {
        if (evento == "agregar") {
            navyPanel.getShipButton("portavion").addActionListener(listener);
            navyPanel.getShipButton("destructor").addActionListener(listener);
            navyPanel.getShipButton("fragata").addActionListener(listener);
            navyPanel.getShipButton("submarino").addActionListener(listener);
            navyPanel.getShipButton("portavion").setEnabled(true);
            navyPanel.getShipButton("destructor").setEnabled(true);
            navyPanel.getShipButton("fragata").setEnabled(true);
            navyPanel.getShipButton("submarino").setEnabled(true);
        } else {
            navyPanel.getShipButton("portavion").removeActionListener(listener);
            navyPanel.getShipButton("destructor").removeActionListener(listener);
            navyPanel.getShipButton("fragata").removeActionListener(listener);
            navyPanel.getShipButton("submarino").removeActionListener(listener);
            navyPanel.getShipButton("portavion").setEnabled(false);
            navyPanel.getShipButton("destructor").setEnabled(false);
            navyPanel.getShipButton("fragata").setEnabled(false);
            navyPanel.getShipButton("submarino").setEnabled(false);
        }
    }

    // Agrega o remueve el escucha a los botones Vertical y Horizontal
    public void setVerticalHorizontal(String evento) {
        if (evento == "agregar") {
            navyPanel.getOrientationButton("vertical").addActionListener(listener);
            navyPanel.getOrientationButton("horizontal").addActionListener(listener);
            navyPanel.getOrientationButton("vertical").setEnabled(true);
            navyPanel.getOrientationButton("horizontal").setEnabled(true);
        } else {
            navyPanel.getOrientationButton("vertical").removeActionListener(listener);
            navyPanel.getOrientationButton("horizontal").removeActionListener(listener);
            navyPanel.getOrientationButton("vertical").setEnabled(false);
            navyPanel.getOrientationButton("horizontal").setEnabled(false);
        }
    }

    // Agrega o remueve el escucha de los botones verticales
    public void setOrientationVertical(String evento) {
        if (evento == "agregar") {
            navyPanel.getOrientationDirectionButton("sup_inf").addActionListener(listener);
            navyPanel.getOrientationDirectionButton("inf_sup").addActionListener(listener);
            navyPanel.getOrientationDirectionButton("sup_inf").setEnabled(true);
            navyPanel.getOrientationDirectionButton("inf_sup").setEnabled(true);
        } else {
            navyPanel.getOrientationDirectionButton("sup_inf").removeActionListener(listener);
            navyPanel.getOrientationDirectionButton("inf_sup").removeActionListener(listener);
            navyPanel.getOrientationDirectionButton("sup_inf").setEnabled(false);
            navyPanel.getOrientationDirectionButton("inf_sup").setEnabled(false);
        }
    }

    // Agrega o remueve el escucha de los botones horizontales
    public void setOrientationHorizontal(String evento) {
        if (evento == "agregar") {
            navyPanel.getOrientationDirectionButton("der_izq").addActionListener(listener);
            navyPanel.getOrientationDirectionButton("izq_der").addActionListener(listener);
            navyPanel.getOrientationDirectionButton("der_izq").setEnabled(true);
            navyPanel.getOrientationDirectionButton("izq_der").setEnabled(true);
        } else {
            navyPanel.getOrientationDirectionButton("der_izq").removeActionListener(listener);
            navyPanel.getOrientationDirectionButton("izq_der").removeActionListener(listener);
            navyPanel.getOrientationDirectionButton("der_izq").setEnabled(false);
            navyPanel.getOrientationDirectionButton("izq_der").setEnabled(false);
        }
    }

    // Agrega o remueve el escucha a cada uno de los JLabel de la matriz position de PintarTablero
    public void setEscuchaCasillas(String evento) {
        if (evento == "agregar") {
            for (int row = 0; row < panelBoard.getBoard("position").getMatriz().length; row++) {
                for (int col = 0; col < panelBoard.getBoard("position").getMatriz()[row].length; col++) {
                    panelBoard.getBoard("position").getMatriz()[row][col].addMouseListener(listener);
                }
            }
        } else {
            for (int row = 0; row < panelBoard.getBoard("position").getMatriz().length; row++) {
                for (int col = 0; col < panelBoard.getBoard("position").getMatriz()[row].length; col++) {
                    panelBoard.getBoard("position").getMatriz()[row][col].removeMouseListener(listener);
                }
            }
        }
    }

    // Agrega o remueve el escucha a cada uno de los JLabel de la matriz principal de PintarTablero
    public void setEscuchaCasillasPrincipal(String evento) {
        if (evento == "agregar") {
            for (int row = 0; row < panelBoard.getBoard("principal").getMatriz().length; row++) {
                for (int col = 0; col < panelBoard.getBoard("principal").getMatriz()[row].length; col++) {
                    panelBoard.getBoard("principal").getMatriz()[row][col].addMouseListener(listener);
                }
            }
        } else {
            for (int row = 0; row < panelBoard.getBoard("principal").getMatriz().length; row++) {
                for (int col = 0; col < panelBoard.getBoard("principal").getMatriz()[row].length; col++) {
                    panelBoard.getBoard("principal").getMatriz()[row][col].removeMouseListener(listener);
                }
            }
        }
    }

    // Identifica si hay un barco en la casilla del tablero principal para hundirlo
    public void funcionesCombate(int row, int col, String barco) {
        // Establece una imagen a la casilla seleccionada del tablero principal del usuario y del tablero position del oponente si un barco fue tocado
        opponentWindow.getPanelTableroOponente().getTableroOponente("position").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/Resources/tocado.png")));
        panelBoard.getBoard("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/Resources/tocado.png")));
        panelBoard.getBoard("principal").getBusyBox().replace(panelBoard.getBoard("principal").getMatriz()[row][col], Integer.valueOf(2));

        // Reduce las casillas ocupadas del barco tocado para poder ser hundido
        opponentWindow.getPanelTableroOponente().getTableroOponente("position").DamageShip(barco);

        // Si no hay mas casillas ocupadas, el barco se hunde y se establecen las imagenes respectivas
        if (opponentWindow.getPanelTableroOponente().getTableroOponente("position").getBoxShip().get(opponentWindow.getPanelTableroOponente().getTableroOponente("position").getMatriz()[row][col]) == Integer.valueOf(0)) {
            navyPanel.getGameInformation().setText("Barco hundido, selecciona otra casilla");
            gameState = 5;
            sunkCounter++;
            for (int fil = 1; fil < 11; fil++) {
                for (int colu = 1; colu < 11; colu++) {
                    if (opponentWindow.getPanelTableroOponente().getTableroOponente("position").getBoxNameShip().get(opponentWindow.getPanelTableroOponente().getTableroOponente("position").getMatriz()[fil][colu]) != null) {
                        if (opponentWindow.getPanelTableroOponente().getTableroOponente("position").getBoxNameShip().get(opponentWindow.getPanelTableroOponente().getTableroOponente("position").getMatriz()[fil][colu]).equals(barco)) {
                            opponentWindow.getPanelTableroOponente().getTableroOponente("position").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/Resources/hundido.png")));
                            panelBoard.getBoard("principal").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/Resources/hundido.png")));
                        }
                    } else {
                        continue;
                    }
                }
            }
        } else {
            navyPanel.getGameInformation().setText("Tocaste una nave, selecciona otra casilla");
            gameState = 5;
        }

        if (sunkCounter == 10) {
            navyPanel.getGameInformation().setText("Todos los barcos enemigos han sido hundidos, ganaste el juego");
            setEscuchaCasillasPrincipal("remover");
        }
    }

    // Retorna el objeto de clase PanelTablero
    public BoardUserPanel getPanelTablero() {
        return panelBoard;
    }

    // Retorna el objeto de clase PanelFlota
    public FleetPanel getPanelFlota() {
        return navyPanel;
    }

    /**
     * inner class that extends an Adapter Class or implements Listeners used by GUI class
     */
    private class Listener implements ActionListener, MouseListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == restartButton) {
                reset();
            } else {
                if (e.getSource() == helpButton) {
                    JOptionPane.showMessageDialog(null, HELP, "¿Como se juega batalla naval?", JOptionPane.PLAIN_MESSAGE, help);
                } else {
                    if (e.getSource() == creditsButton) {
                        JOptionPane.showMessageDialog(null, CREDITS, "Créditos", JOptionPane.PLAIN_MESSAGE, team);
                    } else {
                        if (e.getSource() == startGameButton) {
                            startGameButton.removeActionListener(this);
                            setButtonListener("agregar");
                            setVerticalHorizontal("remover");
                            setOrientationVertical("remover");
                            setOrientationHorizontal("remover");
                            navyPanel.getAssignTurn().setText("¡Tu turno!");
                            navyPanel.getGameInformation().setText("Selecciona la nave que quieres desplegar");
                        } else {
                            if (e.getSource() == opponentMovesButton) {
                                opponentWindow.setVisible(true);
                            } else {
                                if (e.getSource() == navyPanel.getButtonExplanation()) {
                                    JOptionPane.showMessageDialog(null, "", "Como jugar", JOptionPane.PLAIN_MESSAGE, infoSentidos);
                                } else {
                                    if (gameState == 6) {
                                        if (e.getSource() == timer) {
                                            opponentWindow.oponenteVsUsuario();
                                            if (opponentWindow.getEstado() == 0) {
                                                timer.stop();
                                                gameState = 5;
                                                navyPanel.getAssignTurn().setText("Tu turno");
                                                navyPanel.getGameInformation().setText("Selecciona otra casilla del tablero principal");
                                            } else {
                                                if (opponentWindow.getEstado() == 2) {
                                                    timer.stop();
                                                    navyPanel.getGameInformation().setText("Tus barcos han sido hundidos, perdiste el juego");
                                                }
                                            }
                                        }
                                    } else {
                                        switch (gameState) {
                                            case 1:
                                                if (e.getSource() == navyPanel.getShipButton("portavion")) {
                                                    if (navyPanel.getShipQuantity("portavion") > 0) {
                                                        navyPanel.decreaseShipQuantity("portavion");
                                                        setButtonListener("remover");
                                                        navyPanel.getGameInformation().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                        setVerticalHorizontal("agregar");
                                                        navyPanel.setButtonName("portavion");
                                                        gameState = 2;
                                                    } else {
                                                        navyPanel.getGameInformation().setText("No hay mas portaviones disponibles");
                                                    }
                                                } else {
                                                    if (e.getSource() == navyPanel.getShipButton("destructor")) {
                                                        if (navyPanel.getShipQuantity("destructor") > 0) {
                                                            navyPanel.decreaseShipQuantity("destructor");
                                                            setButtonListener("remover");
                                                            navyPanel.getGameInformation().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                            setVerticalHorizontal("agregar");
                                                            navyPanel.setButtonName("destructor");
                                                            gameState = 2;
                                                        } else {
                                                            navyPanel.getGameInformation().setText("No hay mas destructores disponibles");
                                                        }
                                                    } else {
                                                        if (e.getSource() == navyPanel.getShipButton("fragata")) {
                                                            if (navyPanel.getShipQuantity("fragata") > 0) {
                                                                navyPanel.decreaseShipQuantity("fragata");
                                                                setButtonListener("remover");
                                                                navyPanel.getGameInformation().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                                setVerticalHorizontal("agregar");
                                                                navyPanel.setButtonName("fragata");
                                                                gameState = 2;
                                                            } else {
                                                                navyPanel.getGameInformation().setText("No hay mas fragatas disponibles");
                                                            }
                                                        } else {
                                                            if (e.getSource() == navyPanel.getShipButton("submarino")) {
                                                                if (navyPanel.getShipQuantity("submarino") > 0) {
                                                                    navyPanel.decreaseShipQuantity("submarino");
                                                                    setButtonListener("remover");
                                                                    navyPanel.getGameInformation().setText("Escoge si quieres ubicarlo vertical u horizontal");
                                                                    setVerticalHorizontal("agregar");
                                                                    navyPanel.setButtonName("submarino");
                                                                    gameState = 2;
                                                                } else {
                                                                    navyPanel.getGameInformation().setText("No hay mas submarinos disponibles");
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                            case 2:
                                                if (e.getSource() == navyPanel.getOrientationButton("vertical")) {
                                                    setVerticalHorizontal("remover");
                                                    navyPanel.getGameInformation().setText("Escoge cual sentido quieres usar");
                                                    setOrientationVertical("agregar");
                                                    navyPanel.setOrientation(0);
                                                    gameState = 3;
                                                } else {
                                                    if (e.getSource() == navyPanel.getOrientationButton("horizontal")) {
                                                        setVerticalHorizontal("remover");
                                                        navyPanel.getGameInformation().setText("Escoge cual sentido quieres usar");
                                                        setOrientationHorizontal("agregar");
                                                        navyPanel.setOrientation(1);
                                                        gameState = 3;
                                                    }
                                                }
                                                break;
                                            case 3:
                                                if (e.getSource() == navyPanel.getOrientationDirectionButton("sup_inf")) {
                                                    setOrientationVertical("remover");
                                                    navyPanel.getGameInformation().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                    setEscuchaCasillas("agregar");
                                                    navyPanel.setOrientationDirection(1);
                                                    gameState = 4;
                                                } else {
                                                    if (e.getSource() == navyPanel.getOrientationDirectionButton("inf_sup")) {
                                                        setOrientationVertical("remover");
                                                        navyPanel.getGameInformation().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                        setEscuchaCasillas("agregar");
                                                        navyPanel.setOrientationDirection(2);
                                                        gameState = 4;
                                                    } else {
                                                        if (e.getSource() == navyPanel.getOrientationDirectionButton("izq_der")) {
                                                            setOrientationHorizontal("remover");
                                                            navyPanel.getGameInformation().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                            setEscuchaCasillas("agregar");
                                                            navyPanel.setOrientationDirection(3);
                                                            gameState = 4;
                                                        } else {
                                                            if (e.getSource() == navyPanel.getOrientationDirectionButton("der_izq")) {
                                                                setOrientationHorizontal("remover");
                                                                navyPanel.getGameInformation().setText("Selecciona la casilla en la que quieres ubicar la nave");
                                                                setEscuchaCasillas("agregar");
                                                                navyPanel.setOrientationDirection(4);
                                                                gameState = 4;
                                                            }
                                                        }
                                                    }
                                                }
                                                break;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int auxiliar = 0; // Variable para indicar cuando se debe terminar el primer ciclo
            switch (gameState) {
                case 4:
                    for (int row = 1; row < 11; row++) {
                        for (int col = 1; col < 11; col++) {
                            if (e.getSource() == panelBoard.getBoard("position").getMatriz()[row][col]) {
                                // Condicional para saber si el usuario pudo colocar el barco
                                if (paintUserFleet.shipFunctions(navyPanel.getButtonName(), navyPanel.getOrientation(), navyPanel.getOrientationDirection(), col, row)) {
                                    if (navyPanel.getTotalShipQuantity() > 0) {
                                        setEscuchaCasillas("remover");
                                        navyPanel.getGameInformation().setText("Escoge otro barco");
                                        setButtonListener("agregar");
                                        gameState = 1;
                                    } else {
                                        setEscuchaCasillas("remover");
                                        navyPanel.getGameInformation().setText("El combate comienza, selecciona una casilla del tablero principal");
                                        combate.userVsOpponent();
                                        combate.opponentVsUser();
                                        setEscuchaCasillasPrincipal("agregar");
                                        gameState = 5;
                                    }
                                }
                                auxiliar = 1;
                                break;
                            }
                        }
                        if (auxiliar == 1) {
                            break;
                        }
                    }
                    break;
                case 5:
                    for (int row = 1; row < 11; row++) {
                        for (int col = 1; col < 11; col++) {
                            if (e.getSource() == panelBoard.getBoard("principal").getMatriz()[row][col]) {
                                // Verifica si la casilla seleccionada hay un barco oponente
                                if (panelBoard.getBoard("principal").getBusyBox().get(panelBoard.getBoard("principal").getMatriz()[row][col]) == Integer.valueOf(1)) {
                                    // Verifica si todas las casillas del barco fueron seleccionadas
                                    if (opponentWindow.getPanelTableroOponente().getTableroOponente("position").getBoxShip().get(opponentWindow.getPanelTableroOponente().getTableroOponente("position").getMatriz()[row][col]) != Integer.valueOf(0)) {
                                        for (int num = 1; num < 11; num++) {
                                            if (opponentWindow.getPanelTableroOponente().getTableroOponente("position").getBoxNameShip().get(opponentWindow.getPanelTableroOponente().getTableroOponente("position").getMatriz()[row][col]).equals("portavion" + String.valueOf(num))) {
                                                funcionesCombate(row, col, "portavion" + String.valueOf(num));
                                                break;
                                            } else {
                                                if (opponentWindow.getPanelTableroOponente().getTableroOponente("position").getBoxNameShip().get(opponentWindow.getPanelTableroOponente().getTableroOponente("position").getMatriz()[row][col]).equals("submarino" + String.valueOf(num))) {
                                                    funcionesCombate(row, col, "submarino" + String.valueOf(num));
                                                    break;
                                                } else {
                                                    if (opponentWindow.getPanelTableroOponente().getTableroOponente("position").getBoxNameShip().get(opponentWindow.getPanelTableroOponente().getTableroOponente("position").getMatriz()[row][col]).equals("destructor" + String.valueOf(num))) {
                                                        funcionesCombate(row, col, "destructor" + String.valueOf(num));
                                                        break;
                                                    } else {
                                                        if (opponentWindow.getPanelTableroOponente().getTableroOponente("position").getBoxNameShip().get(opponentWindow.getPanelTableroOponente().getTableroOponente("position").getMatriz()[row][col]).equals("fragata" + String.valueOf(num))) {
                                                            funcionesCombate(row, col, "fragata" + String.valueOf(num));
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    if (panelBoard.getBoard("principal").getBusyBox().get(panelBoard.getBoard("principal").getMatriz()[row][col]) == Integer.valueOf(2)) {
                                        navyPanel.getGameInformation().setText("Casilla usada, presiona otra");
                                        gameState = 5;
                                    } else {
                                        navyPanel.getGameInformation().setText("Le diste al agua, espera el turno del oponente");
                                        panelBoard.getBoard("principal").getBusyBox().put(panelBoard.getBoard("principal").getMatriz()[row][col], Integer.valueOf(2));
                                        opponentWindow.getPanelTableroOponente().getTableroOponente("position").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/Resources/agua.png")));
                                        panelBoard.getBoard("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/Resources/agua.png")));
                                        gameState = 6;
                                        navyPanel.getAssignTurn().setText("¡Turno del oponente!");
                                        timer.start();
                                    }
                                }
                                auxiliar = 1;
                                break;
                            }
                        }
                        if (auxiliar == 1) {
                            break;
                        }
                    }
                    break;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}