package BattleShip_Game;
import javax.swing.*;
import java.awt.*;
import java.util.Random;
public class GUI_Secundaria extends JFrame {
    public static final String PATH = "/Resources/";
    private Header titulo;
    private ImageIcon enemyIcon;
    private BoardComputerPanel panelTableroOponenteR;
    private PaintComputerFleet pintarFlotaOponenteR;
    private GUI_Principal guiPrincipal;
    private int contadorHundidos;
    private int estado; // 1 if continues, 2 if the opponent wins, otherwise 0

    public GUI_Secundaria(GUI_Principal _guiPrincipal) {
        this.guiPrincipal = _guiPrincipal;
        contadorHundidos = 0;
        initGUI_Secundaria();

        // Default JFrame configuration
        this.setTitle("Batalla Naval");
        Image image = new ImageIcon(getClass().getResource(PATH + "barcoIcono.png")).getImage();
        this.setIconImage(image);
        this.setUndecorated(false);
        this.setSize(600, 600);
        this.setResizable(true);
        this.setVisible(false);
        this.setLocationRelativeTo(null);
    }
    private void initGUI_Secundaria() {
        // Set up JFrame Container's Layout
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setBackground(Color.CYAN);
        getContentPane().add(panelPrincipal, BorderLayout.CENTER);
        panelPrincipal.setLayout(new BorderLayout(0, 0));

        JPanel panelSup = new JPanel();
        panelSup.setBackground(Color.CYAN);
        panelPrincipal.add(panelSup, BorderLayout.NORTH);
        panelSup.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(Color.CYAN);
        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);
        panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 200, 0));

        JPanel panelCentral = new JPanel();
        panelCentral.setBackground(Color.CYAN);
        panelPrincipal.add(panelCentral, BorderLayout.CENTER);
        panelCentral.setLayout(new GridBagLayout());
        panelTableroOponenteR = new BoardComputerPanel();
        pintarFlotaOponenteR = new PaintComputerFleet(panelTableroOponenteR);
        panelCentral.add(panelTableroOponenteR);

        // Set up JComponents
        // Titulo
        titulo = new Header("MOVIMIENTOS ENEMIGO", Color.CYAN);
        panelSup.add(titulo, FlowLayout.LEFT);

        // Icono
        enemyIcon = new ImageIcon(getClass().getResource(PATH + "enemy.png"));
        JLabel enemy = new JLabel(enemyIcon);
        panelSup.add(enemy, FlowLayout.CENTER);
    }
    public void oponenteVsUsuario() {
        Random fila = new Random();
        Random columna = new Random();

        int row = fila.nextInt(10) + 1;
        int col = columna.nextInt(10) + 1;

        // Verifies if the selected cell contains a user's ship
        if (panelTableroOponenteR.getTableroOponente("principal").getBusyBox().get(panelTableroOponenteR.getTableroOponente("principal").getMatriz()[row][col]) == Integer.valueOf(1)) {
            // Verifies if all cells of the ship have been selected
            if (guiPrincipal.getPanelTablero().getBoard("position").getBoxShip().get(guiPrincipal.getPanelTablero().getBoard("position").getMatriz()[row][col]) != Integer.valueOf(0)) {
                for (int num = 1; num < 11; num++) {
                    if (guiPrincipal.getPanelTablero().getBoard("position").getBoxNameShip().get(guiPrincipal.getPanelTablero().getBoard("position").getMatriz()[row][col]).equals("portavion" + String.valueOf(num))) {
                        funcionesCombate(row, col, "portavion" + String.valueOf(num));
                        break;
                    } else if (guiPrincipal.getPanelTablero().getBoard("position").getBoxNameShip().get(guiPrincipal.getPanelTablero().getBoard("position").getMatriz()[row][col]).equals("submarino" + String.valueOf(num))) {
                        funcionesCombate(row, col, "submarino" + String.valueOf(num));
                        break;
                    } else if (guiPrincipal.getPanelTablero().getBoard("position").getBoxNameShip().get(guiPrincipal.getPanelTablero().getBoard("position").getMatriz()[row][col]).equals("destructor" + String.valueOf(num))) {
                        funcionesCombate(row, col, "destructor" + String.valueOf(num));
                        break;
                    } else if (guiPrincipal.getPanelTablero().getBoard("position").getBoxNameShip().get(guiPrincipal.getPanelTablero().getBoard("position").getMatriz()[row][col]).equals("fragata" + String.valueOf(num))) {
                        funcionesCombate(row, col, "fragata" + String.valueOf(num));
                        break;
                    }
                }
            }
        } else {
            if (panelTableroOponenteR.getTableroOponente("principal").getBusyBox().get(panelTableroOponenteR.getTableroOponente("principal").getMatriz()[row][col]) == Integer.valueOf(2)) {
                oponenteVsUsuario();
            } else {
                panelTableroOponenteR.getTableroOponente("principal").getBusyBox().put(panelTableroOponenteR.getTableroOponente("principal").getMatriz()[row][col], Integer.valueOf(2));
                guiPrincipal.getPanelTablero().getBoard("position").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/Resources/agua.png")));
                panelTableroOponenteR.getTableroOponente("principal").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/Resources/agua.png")));
                estado = 0;
            }
        }
    }
    public void funcionesCombate(int row, int col, String barco) {
        // Set an image to the selected cell of the user's position board if a ship was hit
        guiPrincipal.getPanelTablero().getBoard("position").getMatriz()[row][col].setIcon(new ImageIcon(getClass().getResource("/Resources/tocado.png")));
        panelTableroOponenteR.getTableroOponente("principal").getBusyBox().replace(panelTableroOponenteR.getTableroOponente("principal").getMatriz()[row][col], Integer.valueOf(2));

        // Reduce the occupied cells of the hit ship to be sunk
        guiPrincipal.getPanelTablero().getBoard("position").DamageShip(barco);

        // If there are no more occupied cells, the ship is sunk and the respective images are set
        if (guiPrincipal.getPanelTablero().getBoard("position").getBoxShip().get(guiPrincipal.getPanelTablero().getBoard("position").getMatriz()[row][col]) == Integer.valueOf(0)) {
            contadorHundidos++;
            estado = 1;
            for (int fil = 1; fil < 11; fil++) {
                for (int colu = 1; colu < 11; colu++) {
                    if (guiPrincipal.getPanelTablero().getBoard("position").getBoxNameShip().get(guiPrincipal.getPanelTablero().getBoard("position").getMatriz()[fil][colu]) != null) {
                        if (guiPrincipal.getPanelTablero().getBoard("position").getBoxNameShip().get(guiPrincipal.getPanelTablero().getBoard("position").getMatriz()[fil][colu]).equals(barco)) {
                            guiPrincipal.getPanelTablero().getBoard("position").getMatriz()[fil][colu].setIcon(new ImageIcon(getClass().getResource("/Resources/hundido.png")));
                        }
                    } else {
                        continue;
                    }
                }
            }
        } else {
            estado = 1;
        }

        if (contadorHundidos == 10) {
            guiPrincipal.getPanelFlota().getGameInformation().setText("Tus barcos han sido hundidos, perdiste el juego");
            estado = 2;
        }
    }
    public void distribucionFlotaOponente() {
        Random barcoAleatorio = new Random();
        String nombreBarco = "";
        int numBarcoAleatorio = barcoAleatorio.nextInt(4) + 1;

        switch (numBarcoAleatorio) {
            case 1:
                nombreBarco = "aircraftCarrier";
                break;
            case 2:
                nombreBarco = "submarine";
                break;
            case 3:
                nombreBarco = "destructor";
                break;
            case 4:
                nombreBarco = "frigate";
                break;
        }

        Random orientacionAleatoria = new Random();
        int numOrientacionAleatoria = orientacionAleatoria.nextInt(2);

        Random sentidoAleatorio = new Random();
        int numSentidoAleatorio = 0;
        switch (numOrientacionAleatoria) {
            case 0:
                numSentidoAleatorio = sentidoAleatorio.nextInt(2) + 1;
                break;
            case 1:
                numSentidoAleatorio = sentidoAleatorio.nextInt(4 - 3) + 3;
                break;
        }

        Random columnaAleatoria = new Random();
        int numColumnaAleatoria = columnaAleatoria.nextInt(10) + 1;

        Random filaAleatoria = new Random();
        int numFilaAleatoria = filaAleatoria.nextInt(10) + 1;

        if (numBarcoAleatorio == 1 && pintarFlotaOponenteR.getShipCount("aircraftCarrier") > 0) {
            if (!pintarFlotaOponenteR.shipFunctions(nombreBarco, numOrientacionAleatoria, numSentidoAleatorio, numColumnaAleatoria, numFilaAleatoria)) {
                distribucionFlotaOponente();
            } else {
                pintarFlotaOponenteR.setShipCount("aircraftCarrier");
            }
        } else {
            if (numBarcoAleatorio == 2 && pintarFlotaOponenteR.getShipCount("submarine") > 0) {
                if (!pintarFlotaOponenteR.shipFunctions(nombreBarco, numOrientacionAleatoria, numSentidoAleatorio, numColumnaAleatoria, numFilaAleatoria)) {
                    distribucionFlotaOponente();
                } else {
                    pintarFlotaOponenteR.setShipCount("submarine");
                }
            } else {
                if (numBarcoAleatorio == 3 && pintarFlotaOponenteR.getShipCount("destructor") > 0) {
                    if (!pintarFlotaOponenteR.shipFunctions(nombreBarco, numOrientacionAleatoria, numSentidoAleatorio, numColumnaAleatoria, numFilaAleatoria)) {
                        distribucionFlotaOponente();
                    } else {
                        pintarFlotaOponenteR.setShipCount("destructor");
                    }
                } else {
                    if (numBarcoAleatorio == 4 && pintarFlotaOponenteR.getShipCount("frigate") > 0) {
                        if (!pintarFlotaOponenteR.shipFunctions(nombreBarco, numOrientacionAleatoria, numSentidoAleatorio, numColumnaAleatoria, numFilaAleatoria)) {
                            distribucionFlotaOponente();
                        } else {
                            pintarFlotaOponenteR.setShipCount("frigate");
                        }
                    }
                }
            }
        }
    }
    // Returns the panelTableroOponente
    public BoardComputerPanel getPanelTableroOponente() {
        return panelTableroOponenteR;
    }

    // Returns the object for painting the opponent's fleet
    public PaintComputerFleet getPintarFlotaOponente() {
        return pintarFlotaOponenteR;
    }

    // Returns the value of the estado variable
    public int getEstado() {
        return estado;
    }

    // Returns the number of sunken ships by the opponent
    public int getContadorHundidos() {
        return contadorHundidos;
    }
}
