package BattleShip_Game;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * @brief Clase principal que inicia la GUI
 * @autor Alejandro Fernández - José David Giraldo
 * @version 1.0.0
 * @date 2023-07-02
 *
 */

public class Main extends JFrame {


    public Main(){
        initGUI();

        //Default JFrame configuration
        this.setTitle("Batalla Naval");
        this.setIconImage(image);
        this.setUndecorated(false);
        this.setSize(1500,750);
        this.setResizable(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private Image image;
    private GamePlay gamePlay;
    private void initGUI() {

        image = new ImageIcon("src/BattleShip_Game/Resources/icon.png").getImage();

    }


}
