package BattleShip_Game;

import javax.swing.*;
import java.awt.*;

/**
 * @brief Clase correspondiente a la vista del tablero del usuario
 * @autor Alejandro Fernández - José David Giraldo
 * @version 1.0.0
 * @date 2023-07-03
 */

public class BoardUserPanel extends JPanel {
    public static final String PATH ="/Resources/";

    private JLabel positionBoardLabel, mainBoardLabel, paintShotsLabel;
    private BackgroundPane positionPanel, mainPanel;
    private ModelBoard positionBoard, mainBoard;
    private String[] columnLetters;


    public BoardUserPanel() {
        GridBagLayout layout = new GridBagLayout();
        this.setLayout(layout);
        this.setBackground(Color.lightGray);
        positionBoard = new ModelBoard();
        mainBoard = new ModelBoard();
        columnLetters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        startGame();
        modelBoard();
    }

    public void startGame() {
        GridBagConstraints gbc = new GridBagConstraints();

        positionBoardLabel = new JLabel("T A B L E R O   P O S I C I O N");
        positionBoardLabel.setForeground(new Color(0, 0, 0, 230));
        positionBoardLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(positionBoardLabel, gbc);

        positionPanel = new BackgroundPane();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 15, 0, 15);
        this.add(positionPanel, gbc);

        // Panel tablero principal
        mainBoardLabel = new JLabel("T A B L E R O   P R I N C I P A L");
        mainBoardLabel.setForeground(new Color(0, 0, 0, 230));
        mainBoardLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(mainBoardLabel, gbc);



        mainPanel = new BackgroundPane();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 15, 0, 15);
        this.add(mainPanel, gbc);

        paintShotsLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(PATH + "tiros.png"));
        Image image = imageIcon.getImage().getScaledInstance(150, 130, Image.SCALE_SMOOTH);
        imageIcon.setImage(image);
        paintShotsLabel.setIcon(imageIcon);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(paintShotsLabel, gbc);
    }

    public class BackgroundPane extends JPanel {

        private Image image;

        public BackgroundPane() {
            image = new ImageIcon(getClass().getResource(PATH + "mar.jpg")).getImage();
            this.setLayout(new GridLayout(11, 11));
            this.setPreferredSize(new Dimension(400, 400));
            this.setBorder(BorderFactory.createLineBorder(Color.white));
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
                    positionBoard.getMatriz()[row][col] = new JLabel();
                    positionBoard.getMatriz()[row][col].setOpaque(true);
                    positionBoard.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    positionBoard.getMatriz()[row][col].setBackground(Color.WHITE);

                    mainBoard.getMatriz()[row][col] = new JLabel();
                    mainBoard.getMatriz()[row][col].setOpaque(true);
                    mainBoard.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    mainBoard.getMatriz()[row][col].setBackground(Color.WHITE);

                } else {
                    if (row == 0 && col > 0) {
                        positionBoard.getMatriz()[row][col] = new JLabel(columnLetters[col - 1], SwingConstants.CENTER);
                        positionBoard.getMatriz()[row][col].setOpaque(true);
                        positionBoard.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        positionBoard.getMatriz()[row][col].setBackground(Color.WHITE);

                        mainBoard.getMatriz()[row][col] = new JLabel(columnLetters[col - 1], SwingConstants.CENTER);
                        mainBoard.getMatriz()[row][col].setOpaque(true);
                        mainBoard.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        mainBoard.getMatriz()[row][col].setBackground(Color.WHITE);
                    } else {
                        if (row > 0 && col == 0) {
                            positionBoard.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            positionBoard.getMatriz()[row][col].setOpaque(true);
                            positionBoard.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            positionBoard.getMatriz()[row][col].setBackground(Color.WHITE);

                            mainBoard.getMatriz()[row][col] = new JLabel(String.valueOf(row), SwingConstants.CENTER);
                            mainBoard.getMatriz()[row][col].setOpaque(true);
                            mainBoard.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                            mainBoard.getMatriz()[row][col].setBackground(Color.WHITE);
                        } else {
                            positionBoard.getMatriz()[row][col] = new JLabel();
                            positionBoard.getMatriz()[row][col].setOpaque(false);
                            positionBoard.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));

                            mainBoard.getMatriz()[row][col] = new JLabel();
                            mainBoard.getMatriz()[row][col].setOpaque(false);
                            mainBoard.getMatriz()[row][col].setBorder(BorderFactory.createLineBorder(Color.BLACK));
                        }
                    }
                }
                positionPanel.add(positionBoard.getMatriz()[row][col]);
                mainPanel.add(mainBoard.getMatriz()[row][col]);
            }
        }
    }

    // Retorna el tablero ingresado
    public ModelBoard getBoard(String _board) {
        ModelBoard board = new ModelBoard();
        if (_board.equals("position")) {
            board = positionBoard;
        } else {
            if (_board.equals("principal")) {
                board = mainBoard;
            }
        }
        return board;

    }
}
