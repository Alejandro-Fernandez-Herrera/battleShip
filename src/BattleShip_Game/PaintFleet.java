package BattleShip_Game;
public class PaintFleet {
    private BoardPanel boardPanel;
    private FleetPanel fleetPanel;
    private int usedShipCount; // Accumulator to identify which ship has been deployed (in order from 1 to 10)
    private ArrayList<Integer> usedShipCells; // Cells used by each ship

    // Constructor
    public PaintFleet(BoardPanel _boardPanel, FleetPanel _fleetPanel){
        this.boardPanel = _boardPanel;
        this.fleetPanel = _fleetPanel;
        usedShipCount = 1;
        usedShipCells = new ArrayList<>();
    }

    // Returns the image path depending on the entered ship
    public String getImagePath(String ship, int orientationState, int orientationDirectionState){
        String path = "";
        if(orientationState == 0){
            switch(orientationDirectionState){
                case 1:
                    path = "/resources/" + ship + "_V_S_I/";
                    break;
                case 2:
                    path = "/resources/" + ship + "_V_I_S/";
                    break;
            }
        }else{
            switch(orientationDirectionState){
                case 3:
                    path = "/resources/" + ship + "_H_I_D/";
                    break;
                case 4:
                    path = "/resources/" + ship + "_H_D_I/";
                    break;
            }
        }

        return path;
    }

    // Relates the cell and the number of cells used by the entered ship
    public void relateJLabelShip(JLabel cell, String ship, int shipNumber){
        if(ship.equals("carrier" + String.valueOf(shipNumber))){
            usedShipCells.add(4);
            boardPanel.getBoard("position").getCellShip().put(cell, usedShipCells.get(usedShipCells.size()-1));
        }else{
            if(ship.equals("submarine" + String.valueOf(shipNumber))){
                usedShipCells.add(3);
                boardPanel.getBoard("position").getCellShip().put(cell, usedShipCells.get(usedShipCells.size()-1));
            }else{
                if(ship.equals("destroyer" + String.valueOf(shipNumber))){
                    usedShipCells.add(2);
                    boardPanel.getBoard("position").getCellShip().put(cell, usedShipCells.get(usedShipCells.size()-1));
                }else{
                    if(ship.equals("frigate" + String.valueOf(shipNumber))){
                        usedShipCells.add(1);
                        boardPanel.getBoard("position").getCellShip().put(cell, usedShipCells.get(usedShipCells.size()-1));
                    }
                }
            }
        }
    }

    // Paints the ship on the respective cells of the position board
    public boolean shipFunctions(String ship, int orientationState, int orientationDirectionState, int col, int row){
        int cellsToUse; // Number of cells occupied by the ship
        int usedCells = 0; // Determines if the next cells are occupied to be able to deploy the ship
        int referenceColumn = 0; // Reference column depending on whether it is horizontal or vertical
        int referenceRow = 0; // Reference row depending on whether it is horizontal or vertical
        int nextImage; // Accumulator to show the images in order
        boolean aux = false; // false if the ship cannot be placed, otherwise true

        if(ship.equals("carrier")){
            cellsToUse = 4;
        }else{
            if(ship.equals("submarine")){
                cellsToUse = 3;
            }else{
                if(ship.equals("destroyer")){
                    cellsToUse = 2;
                }else{
                    cellsToUse = 1;
                }
            }
        }

        // If the orientation is horizontal, it can only use two directions
        if(orientationState == 1){
            if(orientationDirectionState == 3){
                referenceColumn = 10;
            }else{
                if(orientationDirectionState == 4){
                    referenceColumn = 1;
                }
            }

            int lastCells = Math.abs(col - referenceColumn);
            if(lastCells < cellsToUse-1){
                aux = false;
            }else{
                if(orientationDirectionState == 3){
                    nextImage = 1;
                    // Determines if the next cells are occupied by another ship or not
                    for(int cell=col; cell < col+cellsToUse; cell++){
                        if(boardPanel.getBoard("position").getOccupiedCells().get(boardPanel.getBoard("position").getMatrix()[row][cell]) == Integer.valueOf(1)) {
                            usedCells++;
                        }
                    }

                    // If the next cells are not occupied, the ship is deployed
                    if(usedCells == 0){
                        for(int pic=col; pic < col+cellsToUse; pic++){
                            boardPanel.getBoard("position").getMatrix()[row][pic].setIcon(new ImageIcon(getClass().getResource(getImagePath(ship, orientationState, orientationDirectionState) + String.valueOf(nextImage) + ".png")));
                            boardPanel.getBoard("position").getOccupiedCells().put(boardPanel.getBoard("position").getMatrix()[row][pic], 1);
                            boardPanel.getBoard("position").getCellShipName().put(boardPanel.getBoard("position").getMatrix()[row][pic], ship + String.valueOf(usedShipCount));
                            relateJLabelShip(boardPanel.getBoard("position").getMatrix()[row][pic], ship + String.valueOf(usedShipCount), usedShipCount);
                            nextImage++;
                            aux = true;
                        }
                        usedShipCount++;
                    }else{
                        aux = false;
                    }
                }else{
                    nextImage = cellsToUse;
                    for(int cell=col; cell > col-cellsToUse; cell--){
                        if(boardPanel.getBoard("position").getOccupiedCells().get(boardPanel.getBoard("position").getMatrix()[row][cell]) == Integer.valueOf(1)) {
                            usedCells++;
                        }
                    }

                    if(usedCells == 0){
                        for(int pic=col; pic > col-cellsToUse; pic--){
                            boardPanel.getBoard("position").getMatrix()[row][pic].setIcon(new ImageIcon(getClass().getResource(getImagePath(ship, orientationState, orientationDirectionState) + String.valueOf(nextImage) + ".png")));
                            boardPanel.getBoard("position").getOccupiedCells().put(boardPanel.getBoard("position").getMatrix()[row][pic], 1);
                            boardPanel.getBoard("position").getCellShipName().put(boardPanel.getBoard("position").getMatrix()[row][pic], ship + String.valueOf(usedShipCount));
                            relateJLabelShip(boardPanel.getBoard("position").getMatrix()[row][pic], ship + String.valueOf(usedShipCount), usedShipCount);
                            nextImage--;
                            aux = true;
                        }
                        usedShipCount++;
                    }else{
                        aux = false;
                    }
                }
            }
        }else{
            if(orientationDirectionState == 1){
                referenceRow = 10;
            }else{
                if(orientationDirectionState == 2){
                    referenceRow = 1;
                }
            }

            int lastCells = Math.abs(row - referenceRow);
            if(lastCells < cellsToUse-1){
                aux = false;
            }else{
                if(orientationDirectionState == 1){
                    nextImage = 1;
                   ```java
                    for(int cell=row; cell < row+cellsToUse; cell++){
                        if(boardPanel.getBoard("position").getOccupiedCells().get(boardPanel.getBoard("position").getMatrix()[cell][col]) == Integer.valueOf(1)) {
                            usedCells++;
                        }
                    }

                    if(usedCells == 0){
                        for(int pic=row; pic < row+cellsToUse; pic++){
                            boardPanel.getBoard("position").getMatrix()[pic][col].setIcon(new ImageIcon(getClass().getResource(getImagePath(ship, orientationState, orientationDirectionState) + String.valueOf(nextImage) + ".png")));
                            boardPanel.getBoard("position").getOccupiedCells().put(boardPanel.getBoard("position").getMatrix()[pic][col], 1);
                            boardPanel.getBoard("position").getCellShipName().put(boardPanel.getBoard("position").getMatrix()[pic][col], ship + String.valueOf(usedShipCount));
                            relateJLabelShip(boardPanel.getBoard("position").getMatrix()[pic][col], ship + String.valueOf(usedShipCount), usedShipCount);
                            nextImage++;
                            aux = true;
                        }
                        usedShipCount++;
                    }else{
                        aux = false;
                    }
                }else{
                    nextImage = cellsToUse;
                    for(int cell=row; cell > row-cellsToUse; cell--){
                        if(boardPanel.getBoard("position").getOccupiedCells().get(boardPanel.getBoard("position").getMatrix()[cell][col]) == Integer.valueOf(1)) {
                            usedCells++;
                        }
                    }

                    if(usedCells == 0){
                        for(int pic=row; pic > row-cellsToUse; pic--){
                            boardPanel.getBoard("position").getMatrix()[pic][col].setIcon(new ImageIcon(getClass().getResource(getImagePath(ship, orientationState, orientationDirectionState) + String.valueOf(nextImage) + ".png")));
                            boardPanel.getBoard("position").getOccupiedCells().put(boardPanel.getBoard("position").getMatrix()[pic][col], 1);
                            boardPanel.getBoard("position").getCellShipName().put(boardPanel.getBoard("position").getMatrix()[pic][col], ship + String.valueOf(usedShipCount));
                            relateJLabelShip(boardPanel.getBoard("position").getMatrix()[pic][col], ship + String.valueOf(usedShipCount), usedShipCount);
                            nextImage--;
                            aux = true;
                        }
                        usedShipCount++;
                    }else{
                        aux = false;
                    }
                }
            }
        }
        return aux;
    }

    // Changes the available quantity of the entered ship
    public void setShipCount(String ship){
        if(ship.equals("carrier")){
            carrierCount--;
        }else{
            if(ship.equals("submarine")) {
                submarineCount--;
            }else{
                if(ship.equals("destroyer")) {
                    destroyerCount--;
                }else{
                    if(ship.equals("frigate")) {
                        frigateCount--;
                    }
                }
            }
        }
    }

    // Returns the available quantity of the entered ship
    public int getShipCount(String ship){
        int count = 0;
        if(ship.equals("carrier")){
            count = carrierCount;
        }else{
            if(ship.equals("submarine")) {
                count = submarineCount;
            }else{
                if(ship.equals("destroyer")) {
                    count = destroyerCount;
                }else{
                    if(ship.equals("frigate")) {
                        count = frigateCount;
                    }
                }
            }
        }
        return count;
    }

    // Returns the total number of available ships
    public int totalShipCount(){
        return carrierCount + submarineCount + destroyerCount + frigateCount;
    }
}
