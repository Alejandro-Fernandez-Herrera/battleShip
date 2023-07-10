package BattleShip_Game;

/**
 * @brief GamePlay - interacciòn entre usuario y CPU
 * @autor Alejandro Fernández - José David Giraldo
 * @version 1.0.0
 * @date 2023-07-02
 *
 */
public class Combat {
    private BoardUserPanel userBoardPanel;
    private BoardComputerPanel opponentBoardPanel;

    public Combat(BoardUserPanel _userBoardPanel, BoardComputerPanel _opponentBoardPanel){
        this.userBoardPanel = _userBoardPanel;
        this.opponentBoardPanel = _opponentBoardPanel;
    }

    // Searches for the occupied cells by ships on the opponent's position board and marks them on the user's main board
    public void userVsOpponent(){
        for(int row = 1; row < 11; row++) {
            for (int col = 1; col < 11; col++) {
                if(opponentBoardPanel.getTableroOponente("position").getBusyBox().get(opponentBoardPanel.getTableroOponente("position").getMatriz()[row][col]) == Integer.valueOf(1)){
                    userBoardPanel.getBoard("principal").getBusyBox().put(userBoardPanel.getBoard("principal").getMatriz()[row][col], 1);
                }
            }
        }
    }

    // Searches for the occupied cells by ships on the user's position board and marks them on the opponent's main board
    public void opponentVsUser(){
        for(int row = 1; row < 11; row++) {
            for (int col = 1; col < 11; col++) {
                if(userBoardPanel.getBoard("position").getBusyBox().get(userBoardPanel.getBoard("position").getMatriz()[row][col]) == Integer.valueOf(1)){
                    opponentBoardPanel.getTableroOponente("principal").getBusyBox().put(opponentBoardPanel.getTableroOponente("principal").getMatriz()[row][col], 1);
                }
            }
        }
    }
}

// TODO: ésta clase deberá interactuar con las instancias de User-VS-Computer y Computer-VS-User de las clases UserBoard and ComputerBoard