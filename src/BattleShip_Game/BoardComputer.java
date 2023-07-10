package BattleShip_Game;


import javax.swing.*;

/**
 * @brief Clase correspondiente a la vista del tablero del computador
 * @autor Alejandro Fernández - José David Giraldo
 * @version 1.0.0
 * @date 2023-07-03
 *
 */


public class BoardComputer extends JPanel {
    private BackgroundPane positionBoardPanel;
    private JLabel positionBoardName;
    private OpponentBoards positionBoard, mainBoardOpponent;
    private String alphabet[];

    // Constructor
    public OpponentBoardPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        this.setBackground(Color.CYAN);
        positionBoard = new OpponentBoards();
        mainBoardOpponent = new OpponentBoards();
        alphabet = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        initialize();
        createOpponentBoardModel();
    }
    // Sets the initial configuration of the JComponent
    public void initialize() {
        GridBagConstraints gbc = new GridBagConstraints();

        // Position board panel
        positionBoardName = new JLabel("P O S I T I O N   B O A R D");
        positionBoardName.setForeground(new Color(0, 0, 0, 230));
        positionBoardName.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(positionBoardName, gbc);

        positionBoardPanel = new BackgroundPane();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 15, 0, 15);
        this.add(positionBoardPanel, gbc);
    }

}
