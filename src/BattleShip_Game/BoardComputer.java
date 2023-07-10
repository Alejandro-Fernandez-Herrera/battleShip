package BattleShip_Game;

import javax.swing.*;
import java.awt.*;

/**
 * @brief Clase correspondiente a la vista del tablero del oponente
 * @autor Alejandro Fernández - José David Giraldo
 * @version 1.0.0
 * @date 2023-07-09
 */

public class BoardComputer extends JPanel {

    private JLabel positionBoardLabel;
    private BackgroundPane positionPanel;
    private ModelBoard positionBoardComputer, mainBoardComputer;
    private String[] columnLetters;


    public BoardComputer() {
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        this.setBackground(Color.BLUE);
        positionBoardComputer = new ModelBoard();
        mainBoardComputer = new ModelBoard();
        columnLetters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        startGame();
        modelBoardComputer();
    }

    public void startGame() {
        GridBagConstraints gbc = new GridBagConstraints();

        positionBoardLabel = new JLabel("Posición de los Barcos");
        positionBoardLabel.setForeground(Color.WHITE);
        positionBoardLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(positionBoardLabel, gbc);

        mainBoardLabel = new JLabel("Tablero de Juego");
        mainBoardLabel.setForeground(Color.WHITE);
        mainBoardLabel.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(mainBoardLabel, gbc);

        positionPanel = new BackgroundPane();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 15, 0, 15);
        this.add(positionPanel, gbc);

        mainPanel = new BackgroundPane();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 15, 0, 15);
        this.add(mainPanel, gbc);

        paintShotsLabel = new JLabel();
        paintShotsLabel.setIcon(new ImageIcon(getClass().getResource("/BattleShip_Game/Resources/Shot.png")));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(paintShotsLabel, gbc);
    }

    public class BackgroundPane extends JPanel {

        private Image image;

        public BackgroundPane() {
            image = new ImageIcon(getClass().getResource("/BattleShip_Game/Resources/SeaBackground.png")).getImage();
            this.setLayout(new GridLayout(11, 11));
            this.setPreferredSize(new Dimension(400, 400));
            this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(image, 0, 0, this);
        }
    }

    public void modelBoard() {
        for (int row = 0; row < 11; row++) {
            for (int col = 0; col < 11; col++) {
                if (row == 0 && col == 0) {
                    positionBoardComputer.getMatriz()[row][col] = new JLabel();
                    positionBoardComputer.getMatriz()[row][col].setOpaque(true);
                    positionBoardComputer.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    positionBoardComputer.getMatriz()[row][col].setBackground(Color.WHITE);

                    mainBoardComputer.getMatriz()[row][col] = new JLabel();
                    mainBoardComputer.getMatriz()[row][col].setOpaque(true);
                    mainBoardComputer.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    mainBoardComputer.getMatriz()[row][col].setBackground(Color.WHITE);

                } else {
                    if (row == 0 && col > 0) {
                        positionBoardComputer.getMatriz()[row][col] = new JLabel(columnLetters[col - 1], SwingConstants.CENTER);
                        positionBoardComputer.getMatriz()[row][col].setOpaque(true);
                        positionBoardComputer.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        positionBoardComputer.getMatriz()[row][col].setBackground(Color.WHITE);

                        mainBoardComputer.getMatriz()[row][col] = new JLabel(columnLetters[col - 1], SwingConstants.CENTER);
                        mainBoardComputer.getMatriz()[row][col].setOpaque(true);
                        mainBoardComputer.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        mainBoardComputer.getMatriz()[row][col].setBackground(Color.WHITE);
                    } else {
                        if (row > 0 && col == 0) {
                            positionBoardComputer.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            positionBoardComputer.getMatriz()[row][col].setOpaque(true);
                            positionBoardComputer.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            positionBoardComputer.getMatriz()[row][col].setBackground(Color.WHITE);

                            mainBoardComputer.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            mainBoardComputer.getMatriz()[row][col].setOpaque(true);
                            mainBoardComputer.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            mainBoardComputer.getMatriz()[row][col].setBackground(Color.WHITE);
                        } else {
                            positionBoardComputer.getMatriz()[row][col] = new JLabel();
                            positionBoardComputer.getMatriz()[row][col].setOpaque(false);
                            positionBoardComputer.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                            mainBoardComputer.getMatriz()[row][col] = new JLabel();
                            mainBoardComputer.getMatriz()[row][col].setOpaque(false);
                            mainBoardComputer.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        }
                    }
                }
                positionPanel.add(positionBoardComputer.getMatriz()[row][col]);
                mainPanelComputer.add(mainBoardComputer.getMatriz()[row][col]);
            }
        }
    }

    public ModelBoard getBoard(String _board) {
        ModelBoard board = new ModelBoard();
        if (_board.equals("Posición")) {
            board = positionBoardComputer;
        } else {
            if (_board.equals("Principal")) {
                board = mainBoardComputer;
            }
        }
        return board;

    }
}