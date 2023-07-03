package BattleShip_Game;

import javax.swing.*;
import java.awt.*;

/**
 * @brief Header - contiene el encabezado de la ventana (GUI)
 * @autor Alejandro Fernández - José David Giraldo
 * @version 1.0.0
 * @date 2023-07-02
 *
 */
public class Header extends JLabel {

    public Header(String title, Color colorBackground){
        this.setText(title);
        this.setBackground(colorBackground);
        this.setForeground(new Color(0, 0, 0, 230));
        this.setFont(new Font(Font.MONOSPACED,Font.BOLD,30));
        this.setHorizontalAlignment(JLabel.CENTER);
        this.setVerticalAlignment(JLabel.CENTER);
        this.setOpaque(true);
    }
}
