package BattleShip_Game;
import javax.swing.*;
import java.util.HashMap;

/**
 * @brief Clase que contiene la logica del juego
 * @autor Alejandro Fernández - José David Giraldo
 * @version 1.0.0
 * @date 2023-07-02
 *
 */


public class ModelBoard {
    private JLabel Model[][];
    private HashMap<JLabel, Integer> BusyBox; // Verifica si la casilla esta ocupada por una nave, 1 si esta ocupado, 2 si fue atacado
    private HashMap<JLabel, Integer> BoxShip; // Relacion entre la casilla y las casillas que usa la nave
    private HashMap<JLabel, String> BoxNameShip; // Almacena el nombre del barco ubicado en la casilla


    public ModelBoard(){
        Model = new JLabel[11][11];
        BusyBox = new HashMap<>();
        BoxShip = new HashMap<>();
        BoxNameShip = new HashMap<>();

    }

    public void DamageShip (String Ship){
        for (int row = 1; row < 11; row++) {
            for (int col = 1; col < 11; col++) {
                if(BoxNameShip.get(Model[row][col]) != null){
                    if(BoxNameShip.get(Model[row][col]).equals(Ship)){
                        BoxShip.replace(Model[row][col], BoxShip.get(Model[row][col])-1);
                    }
                }
            }
        }
    }

    public JLabel[][] getMatriz(){

        return Model;
    }

    public HashMap getBusyBox(){

        return BusyBox;
    }

    public HashMap getBoxShip(){

        return BoxShip;
    }

    public HashMap getBoxNameShip(){

        return BoxNameShip;
    }
}
