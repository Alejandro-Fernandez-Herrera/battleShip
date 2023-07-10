package BattleShip_Game;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
public class FleetPanel extends JPanel{
    public static final String PATH = "/resources/";
    private JButton aircraftCarrier, destroyer, frigate, submarine, vertical, horizontal, topBottom, bottomTop, leftRight, rightLeft, buttonExplanation;
    private JPanel fleetPanel, gameInfoPanel, buttonPanel, subButtonPanel, subButtonPanel2;
    private JLabel assignTurn;
    private JTextPane gameInformation;
    private ImageIcon destroyerImage, aircraftCarrierImage, frigateImage, submarineImage;
    private TitledBorder fleetTitle, infoTitle, orientationTitle;
    private Border blackline;
    private String buttonName; // Stores the text of the button
    private int orientation; // 0 if vertical, 1 if horizontal
    private int orientationDirection; // 1 top-bottom, 2 bottom-top, 3 left-right, 4 right-left
    private int aircraftCarrierCount;
    private int submarineCount;
    private int destroyerCount;
    private int frigateCount;

    public FleetPanel() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        this.setLayout(gridBagLayout);
        this.setBackground(Color.lightGray);
        this.setPreferredSize(new Dimension(600, 100));
        createShips();

    }
    public void createShips() {
        GridBagConstraints gbc = new GridBagConstraints();

        buttonName = "";
        orientation = 0;
        orientationDirection = 0;

        // Initial ship quantities
        aircraftCarrierCount = 1;
        submarineCount = 2;
        destroyerCount = 3;
        frigateCount = 4;

        // Images
        destroyerImage = new ImageIcon(getClass().getResource(PATH + "destructor.png"));
        aircraftCarrierImage = new ImageIcon(getClass().getResource(PATH + "aircraftCarrier.png"));
        frigateImage = new ImageIcon(getClass().getResource(PATH + "frigate.png"));
        submarineImage = new ImageIcon(getClass().getResource(PATH + "submarine.png"));

        // Aircraft Carrier button
        aircraftCarrier = new JButton();
        aircraftCarrier.setText("AIRCRAFT CARRIER");
        aircraftCarrier.setIcon(aircraftCarrierImage);
        aircraftCarrier.setBackground(Color.lightGray);
        aircraftCarrier.setHorizontalTextPosition(SwingConstants.CENTER);
        aircraftCarrier.setVerticalTextPosition(SwingConstants.BOTTOM);
        aircraftCarrier.setFocusable(false);
        aircraftCarrier.setBorder(null);

        destroyer = new JButton();
        destroyer.setText("DESTROYER");
        destroyer.setIcon(destroyerImage);
        destroyer.setBackground(Color.lightGray);
        destroyer.setHorizontalTextPosition(SwingConstants.CENTER);
        destroyer.setVerticalTextPosition(SwingConstants.BOTTOM);
        destroyer.setFocusable(false);
        destroyer.setBorder(null);

        frigate = new JButton();
        frigate.setText("FRIGATE");
        frigate.setIcon(frigateImage);
        frigate.setBackground(Color.lightGray);
        frigate.setHorizontalTextPosition(SwingConstants.CENTER);
        frigate.setVerticalTextPosition(SwingConstants.BOTTOM);
        frigate.setFocusable(false);
        frigate.setBorder(null);

        submarine = new JButton();
        submarine.setText("SUBMARINE");
        submarine.setIcon(submarineImage);
        submarine.setBackground(Color.lightGray);
        submarine.setHorizontalTextPosition(SwingConstants.CENTER);
        submarine.setVerticalTextPosition(SwingConstants.BOTTOM);
        submarine.setFocusable(false);
        submarine.setBorder(null);

// Orientation buttons
        vertical = new JButton("Vertical");
        vertical.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 15));
        vertical.setBackground(new Color(255, 0, 128));
        vertical.setForeground(Color.WHITE);
        vertical.setFocusable(false);
        vertical.setBorder(null);

        horizontal = new JButton("Horizontal");
        horizontal.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 15));
        horizontal.setBackground(new Color(128, 0, 255));
        horizontal.setForeground(Color.WHITE);
        horizontal.setFocusable(false);
        horizontal.setBorder(null);

        topBottom = new JButton("Top-Bottom");
        topBottom.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 15));
        topBottom.setBackground(new Color(0, 0, 255));
        topBottom.setForeground(Color.WHITE);
        topBottom.setFocusable(false);
        topBottom.setBorder(null);

        bottomTop = new JButton("Bottom-Top");
        bottomTop.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 15));
        bottomTop.setBackground(new Color(255, 0, 255));
        bottomTop.setForeground(Color.WHITE);
        bottomTop.setFocusable(false);
        bottomTop.setBorder(null);
        leftRight = new JButton("Left-Right");
        leftRight.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 15));
        leftRight.setBackground(new Color(0, 0, 255));
        leftRight.setForeground(Color.WHITE);
        leftRight.setFocusable(false);
        leftRight.setBorder(null);

        rightLeft = new JButton("Right-Left");
        rightLeft.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 15));
        rightLeft.setBackground(new Color(255, 0, 255));
        rightLeft.setForeground(Color.WHITE);
        rightLeft.setFocusable(false);
        rightLeft.setBorder(null);

// Orientation button explanation

        buttonExplanation = new JButton("Button Explanation");
        buttonExplanation.setFont(new Font(Font.SERIF, Font.ROMAN_BASELINE, 15));
        buttonExplanation.setBackground(new Color(255, 255, 255));
        buttonExplanation.setFocusable(false);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(buttonExplanation, gbc);

// Fleet
        blackline = BorderFactory.createLineBorder(Color.BLACK);
        fleetPanel = new JPanel();
        fleetPanel.setLayout(new GridLayout(2, 2, 10, 10));
        fleetPanel.setPreferredSize(new Dimension(350, 300));
        fleetPanel.setBackground(Color.lightGray);
        fleetTitle = BorderFactory.createTitledBorder(blackline, "Your Ships");
        fleetTitle.setTitleJustification(TitledBorder.CENTER);
        fleetPanel.setBorder(fleetTitle);
        fleetPanel.add(aircraftCarrier);
        fleetPanel.add(destroyer);
        fleetPanel.add(frigate);
        fleetPanel.add(submarine);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(fleetPanel, gbc);

// Text below fleet
        assignTurn = new JLabel();
        assignTurn.setHorizontalAlignment(SwingConstants.CENTER);

        gameInformation = new JTextPane();
        gameInformation.setEditable(false);
        gameInformation.setBackground(Color.lightGray);
        StyledDocument documentStyle = gameInformation.getStyledDocument();
        SimpleAttributeSet centerAttribute = new SimpleAttributeSet();
        StyleConstants.setAlignment(centerAttribute, StyleConstants.ALIGN_CENTER);
        documentStyle.setParagraphAttributes(0, documentStyle.getLength(), centerAttribute, false);

        gameInfoPanel = new JPanel(new GridLayout(2, 0, 0, 0));
        gameInfoPanel.setPreferredSize(new Dimension(350, 100));
        gameInfoPanel.setBackground(Color.lightGray);
        infoTitle = BorderFactory.createTitledBorder(blackline, "Game Information");
        infoTitle.setTitleJustification(TitledBorder.CENTER);
        gameInfoPanel.setBorder(infoTitle);
        gameInfoPanel.add(assignTurn);
        gameInfoPanel.add(gameInformation);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(gameInfoPanel, gbc);

        // Button panel inside fleet panel
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 2, 5, 10));
        buttonPanel.setBackground(Color.lightGray);
        buttonPanel.setPreferredSize(new Dimension(350, 120));
        orientationTitle = BorderFactory.createTitledBorder(blackline, "How do you want to arrange your fleet?");
        orientationTitle.setTitleJustification(TitledBorder.CENTER);
        buttonPanel.setBorder(orientationTitle);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        this.add(buttonPanel, gbc);
// Panel inside button panel for vertical and horizontal buttons
        subButtonPanel = new JPanel(new GridLayout());
        subButtonPanel.setPreferredSize(new Dimension(175, 120));
        subButtonPanel.setBackground(Color.lightGray);
        subButtonPanel.setLayout(new GridLayout(2, 0, 5, 5));
        subButtonPanel.add(vertical);
        subButtonPanel.add(horizontal);
        buttonPanel.add(subButtonPanel, BorderLayout.WEST);

// Panel inside button panel for top-bottom, bottom-top, right-left, left-right buttons
        subButtonPanel2 = new JPanel();
        subButtonPanel2.setPreferredSize(new Dimension(175, 120));
        subButtonPanel2.setBackground(Color.lightGray);
        subButtonPanel2.setLayout(new GridLayout(4, 0, 5, 5));
        subButtonPanel2.add(topBottom);
        subButtonPanel2.add(bottomTop);
        subButtonPanel2.add(leftRight);
        subButtonPanel2.add(rightLeft);
        buttonPanel.add(subButtonPanel2, BorderLayout.EAST);
    }

// Returns the specified ship button
    public JButton getShipButton (String ship){
        JButton button = new JButton();
        if (ship.equals("portavion")) {
            button = aircraftCarrier;
        }else{
            if (ship.equals("submarino")) {
                button = submarine;
            }else{
                if (ship.equals("destructor")) {
                    button = destroyer;
                }else{
                    if (ship.equals("fragata")) {
                        button = frigate;
                    }
                }
            }
        }
        return button;
    }

// Returns the specified orientation button
    public JButton getOrientationButton (String orientation){
        JButton button = new JButton();
        if (orientation.equals("vertical")) {
            button = vertical;
        }else{
            if (orientation.equals("horizontal")) {
                button = horizontal;
            }
        }
        return button;
    }

// Returns the specified orientation direction button
    public JButton getOrientationDirectionButton (String direction){
        JButton button = new JButton();
        if (direction.equals("sup_inf")) {
            button = topBottom;
        }else{
            if (direction.equals("inf_sup")) {
                button = bottomTop;
            } else{
                if (direction.equals("izq_der")) {
                    button = leftRight;
                } else {
                    if (direction.equals("der_izq")) {
                        button = rightLeft;
                    }
                }
            }
        }
        return button;
    }

// Sets the name of the pressed button
    public void setButtonName (String _buttonName){
        buttonName = _buttonName;
    }

// Returns the name of the pressed ship button
    public String getButtonName () {
        return buttonName;
    }

// Sets the orientation state
    public void setOrientation ( int _orientation){
        orientation = _orientation;
    }

// Sets the orientation direction state
    public void setOrientationDirection ( int _orientationDirection){
        orientationDirection = _orientationDirection;
    }

// Returns the orientation state
    public int getOrientation () {
        return orientation;
    }

// Returns the orientation direction state
    public int getOrientationDirection () {
        return orientationDirection;
    }

// Decreases the available quantity of the specified ship
    public void decreaseShipQuantity (String ship){
        if (ship.equals("portavion")) {
            aircraftCarrierCount--;
        } else {
            if (ship.equals("submarino")) {
                submarineCount--;
            } else {
                if (ship.equals("destructor")) {
                    destroyerCount--;
                } else {
                    if (ship.equals("fragata")) {
                        frigateCount--;
                    }
                }
            }
        }
    }


// Returns the available quantity of the specified ship
    public int getShipQuantity (String ship){
        int quantity = 0;
        if (ship.equals("portavion")) {
            quantity = aircraftCarrierCount;
        } else {
            if (ship.equals("submarino")) {
                quantity = submarineCount;
            } else {
                if (ship.equals("destructor")) {
                    quantity = destroyerCount;
                } else {
                    if (ship.equals("fragata")) {
                        quantity = frigateCount;
                    }
                }
            }
        }
        return quantity;
    }

// Returns the total quantity of available ships
    public int getTotalShipQuantity () {

        return aircraftCarrierCount + submarineCount + destroyerCount + frigateCount;
    }

// Returns the JTextPane to edit game information
    public JTextPane getGameInformation () {

        return gameInformation;
    }


// Returns the JLabel to edit the turn assignment
    public JLabel getAssignTurn () {

        return assignTurn;
    }

// Returns the button that explains the button dynamics
    public JButton getButtonExplanation () {

        return buttonExplanation;
    }
}


