import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Class ApplicationGUI to display a GUI for the application.
 *
 * Tu Phuong Dang (103814482)
 * @version 11 May, 2024
 */
public class ApplicationGUI extends JFrame
{
    // instance variables
    private CarPark carPark = new CarPark();
    private JPanel mainPanel, menuPanel, contentPanel;
    private Container container;
    
    // Color Constants
    private static final String UNOCCUPIED_SPOT_COLOR = "#B5D5C5";
    private static final String OCCUPIED_SPOT_COLOR = "#F9B572";
    
    // Size Constants
    private static final int WIDTH = 600;
    private static final int HEIGHT = 1200;

    public ApplicationGUI(){
        // Set title for the container
        setTitle("CarPark Application");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //get content container
        container = getContentPane();
        
        //create JPanel with Box layout
        mainPanel = new JPanel(new BorderLayout());
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new BoxLayout(gridPanel, BoxLayout.X_AXIS));
        
        //Add the mainPanel to the frame's content pane
        container.add(mainPanel);
        
        // Menu Panel contains 9 menu items with an action listner
        menuPanel = new JPanel(new GridLayout(0, 1));
        menuPanel.setPreferredSize(new Dimension((int)(HEIGHT * 0.25), WIDTH));
        menuPanel.setBackground(Color.decode("#51829B"));
        
        // Button 1 with action listner
        JButton btn1 = new JButton("1. Add a parking spot");
        menuPanel.add(btn1);
        btn1.addActionListener(new addParkingSpot());
        
        // Button 2 with action listner
        JButton btn2 = new JButton("2. Delete a parking spot");
        menuPanel.add(btn2);
        btn2.addActionListener(new deleteParkingSpot());
        
        // Button 3 with action listner
        JButton btn3 = new JButton("3. List all parking spots");
        menuPanel.add(btn3);
        btn3.addActionListener(new listAllSpots());
        
        // Button 4 with action listner
        JButton btn4 = new JButton("4. Park a car");
        menuPanel.add(btn4);
        btn4.addActionListener(new parkCar());
        
        // Button 5 with action listner
        JButton btn5 = new JButton("5. Find car by registration number");
        menuPanel.add(btn5);
        btn5.addActionListener(new findCarByRegistration());
        
        // Button 6 with action listner
        JButton btn6 = new JButton("6. Remove car by registration number");
        menuPanel.add(btn6);
        btn6.addActionListener(new removeCarByRegistration());
        
        // Button 7 with action listner
        JButton btn7 = new JButton("7. Find cars by make");
        menuPanel.add(btn7);
        btn7.addActionListener(new findCarsByMake());
        
        // Button 8 with action listner
        JButton btn8 = new JButton("8. Reset cark park");
        menuPanel.add(btn8);
        btn8.addActionListener(new resetCarPark());
        
        // Button 9 with action listner
        JButton btn9 = new JButton("9. Exit");
        menuPanel.add(btn9);
        btn9.addActionListener(new exit());
        
        // Content Panel contain car park and car info
        contentPanel = new JPanel();
        contentPanel.setPreferredSize(new Dimension((int)(HEIGHT * 0.75), WIDTH));
        contentPanel.setBackground(Color.decode("#9E9FA5"));
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // Add Menu Panel and Content Panel to Grid Panel
        gridPanel.add(menuPanel);
        gridPanel.add(contentPanel);
        
        // Add grid panel to the main panel
        mainPanel.add(gridPanel);
        
        //The layout manager will arrange contents properly
        pack();
    }
    
    /**
     * Class addParkingSpot to add a parking spot.
     *
     */
    class addParkingSpot implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Display input dialog to enter the spot ID
            String spotID = JOptionPane.showInputDialog(contentPanel, "What's the spot ID?", "Input", JOptionPane.PLAIN_MESSAGE);
            // Check if spotID is null or empty
            if (spotID == null ) {
                return;
            } else if (spotID.isEmpty()) {
                JOptionPane.showMessageDialog(contentPanel, "Spot ID cannot be empty", "Message", JOptionPane.ERROR_MESSAGE);
                
            } else {
                String result = carPark.addParkingSpot(spotID);
                if (result != "OK") {
                    JOptionPane.showMessageDialog(contentPanel, result, "Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Create a JPanel to represent the box
                    JPanel boxPanel = new JPanel();
                    boxPanel.setPreferredSize(new Dimension(200, 150)); // Set width and height
                    boxPanel.setBackground(Color.decode(UNOCCUPIED_SPOT_COLOR)); // Set background color
                    boxPanel.setLayout(new BoxLayout(boxPanel, BoxLayout.Y_AXIS)); // Set layout
                    boxPanel.addMouseListener(new handleOnclickSpot(spotID));
        
                    // Create a JLabel to display the spotID
                    JLabel spotLabel = new JLabel(spotID);
                    spotLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                    boxPanel.add(spotLabel);

                    // Add the small panel to the content panel
                    contentPanel.add(boxPanel);
        
                    // Refresh the layout of the content panel to reflect the changes
                    contentPanel.revalidate();
                    contentPanel.repaint();
                }
            }
        }
    }
    
    /**
     * Class deleteParkingSpot to delete a parking spot.
     *
     */
    class deleteParkingSpot implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Display input dialog to enter the spot ID
            String spotID = JOptionPane.showInputDialog(contentPanel, "What's the spot ID?", "Input", JOptionPane.PLAIN_MESSAGE);
            
            // Check if spotID is null or empty
            if (spotID == null) {
                return;
            } else if (spotID.isEmpty()) {
                JOptionPane.showMessageDialog(contentPanel, "Spot ID cannot be empty", "Message", JOptionPane.ERROR_MESSAGE);
            } else {
                String result = carPark.deleteParkingSpot(spotID);
                if (result != "OK") {
                    JOptionPane.showMessageDialog(contentPanel, result, "Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Iterate through the components of the contentPanel
                    Component[] components = contentPanel.getComponents();

                    for (Component component : components) {
                        if (component instanceof JPanel) {
                            JPanel boxPanel = (JPanel) component;
                            JLabel spotLabel = (JLabel) boxPanel.getComponent(0); // SpotID is the first component
                            String labelID = spotLabel.getText();
        
                            if (spotID.equals(labelID)) {
                                // Remove the boxPanel from the contentPanel
                                contentPanel.remove(boxPanel);
                                break;
                            }
                        }
                    }
        
                    // Refresh the layout of the contentPanel to reflect the changes
                    contentPanel.revalidate();
                    contentPanel.repaint();
                    
                    // Inform user the result
                    JOptionPane.showMessageDialog(contentPanel, "Parking slot deleted successfully.", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    }
    
    /**
     * Class listAllSpots to display all parking spots.
     *
     */
    class listAllSpots implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String result = carPark.listAllSpots();
            
            // Inform user the result
            JOptionPane.showMessageDialog(contentPanel, result, "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Class parkCar to add a car to a parking spot.
     *
     */
    class parkCar implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Display input dialog to enter the spot ID
            String spotID = JOptionPane.showInputDialog(contentPanel, "What's the spot ID?", "Input", JOptionPane.PLAIN_MESSAGE);
            
            // Check if user inputs are null or empty
            if (spotID == null) {
                return;
            } else if (spotID.isEmpty()) {
                JOptionPane.showMessageDialog(contentPanel, "Spot ID cannot be empty", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                // Display input dialog to enter the registrationNumber
                String registrationNumber = JOptionPane.showInputDialog(contentPanel, "What's the Car Registration?", "Input", JOptionPane.PLAIN_MESSAGE);
                
                if (registrationNumber == null) {
                    return;
                } else if (registrationNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(contentPanel, "Registration Number cannot be empty", "Message", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    // Display input dialog to enter make
                    String make = JOptionPane.showInputDialog(contentPanel, "What's the Car Make?", "Input", JOptionPane.PLAIN_MESSAGE);
                    
                    if (make == null) {
                        return;
                    } else if (make.isEmpty()) {
                        JOptionPane.showMessageDialog(contentPanel, "Car Make cannot be empty", "Message", JOptionPane.ERROR_MESSAGE);
                        return;
                    } else {
                        // Display input dialog to enter model
                        String model = JOptionPane.showInputDialog(contentPanel, "What's the Car Model?", "Input", JOptionPane.PLAIN_MESSAGE);
                        
                        if (model == null) {
                            return;
                        } else if (model.isEmpty()) {
                            JOptionPane.showMessageDialog(contentPanel, "Car Model cannot be empty", "Message", JOptionPane.ERROR_MESSAGE);
                            return;
                        } else {
                            // Display input dialog to enter year
                            int year = 0;
                            try {
                                year = Integer.parseInt(JOptionPane.showInputDialog(contentPanel, "What's the Car Year?", "Input", JOptionPane.PLAIN_MESSAGE));
                            } catch (NumberFormatException err) {
                                JOptionPane.showMessageDialog(contentPanel, "Car Year must be a number", "Message", JOptionPane.ERROR_MESSAGE);
                            }
                            
                            // Record current date and time
                            LocalDateTime currentDateTime = LocalDateTime.now();
                        
                            String result = carPark.parkCar(spotID, registrationNumber, make, model, year, currentDateTime);
                            
                            if (result != "OK") {
                                JOptionPane.showMessageDialog(contentPanel, result, "Message", JOptionPane.ERROR_MESSAGE);
                            } else {
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                String dateTime = currentDateTime.format(formatter);
                                
                                // Iterate through the components of the contentPanel
                                Component[] components = contentPanel.getComponents();
                
                                for (Component component : components) {
                                    if (component instanceof JPanel) {
                                        JPanel boxPanel = (JPanel) component;
                                        JLabel spotLabel = (JLabel) boxPanel.getComponent(0); // SpotID is the first component
                                        String labelID = spotLabel.getText();
                    
                                        if (spotID.equals(labelID)) {
                                            // Change background and border color
                                            boxPanel.setBackground(Color.decode(OCCUPIED_SPOT_COLOR)); 
                                            
                                            // Add text area to the component
                                            String info = "Registration: " + registrationNumber + " " + make + " " + model + " " + year + "\nParked: " + dateTime;
                                            JTextArea spotText = new JTextArea(info);
                                            spotText.setEnabled(false); // disable edit
                                            spotText.setDisabledTextColor(Color.BLACK);
                                            spotText.setOpaque(false); // Make JTextArea transparent
                                            spotText.setFocusable(false);
                                            spotText.setBorder(new EmptyBorder(15, 15, 15, 15)); // Add padding
                                            spotText.setLineWrap(true); // Enable text wrapping
                                            spotText.setAlignmentX(Component.CENTER_ALIGNMENT);
                                            spotText.addMouseListener(new handleOnclickCar(registrationNumber, spotID));
                                            boxPanel.add(spotText);
                                            break;
                                        }
                                    }
                                }
                                    
                                // Refresh the layout of the contentPanel to reflect the changes
                                contentPanel.revalidate();
                                contentPanel.repaint();
                                    
                                // Inform user the result
                                String message = "Car with registration " + registrationNumber + " parked successfully at " + dateTime + ".";
                                JOptionPane.showMessageDialog(contentPanel, message, "Message", JOptionPane.INFORMATION_MESSAGE);
                            }    
                        }
                    }
                }   
            }    
        }
    }
    
    /**
     * Class findCarByRegistration to find a car by registration number.
     *
     */
    class findCarByRegistration implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Display input dialog to enter the registrationNumber
            String registrationNumber = JOptionPane.showInputDialog(contentPanel, "What's the Car Registration Number?", "Input", JOptionPane.PLAIN_MESSAGE);
            
            // Check if user inputs are null or empty
            if (registrationNumber == null ) {
                return;
            } else if (registrationNumber.isEmpty()) {
                JOptionPane.showMessageDialog(contentPanel, "Registration Number cannot be empty", "Message", JOptionPane.ERROR_MESSAGE);
            } else {
                String result = carPark.findCarByRegistration(registrationNumber);
                
                // Inform user the result
                JOptionPane.showMessageDialog(contentPanel, result, "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    /**
     * Class removeCarByRegistration to remove a car by registration number.
     *
     */
    class removeCarByRegistration implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Display input dialog to enter the registrationNumber
            String registrationNumber = JOptionPane.showInputDialog(contentPanel, "What's the Car Registration Number?", "Input", JOptionPane.PLAIN_MESSAGE);
            
            // Check if user inputs are null or empty
            if (registrationNumber == null) {
                return;
            } else if (registrationNumber.isEmpty()) {
                JOptionPane.showMessageDialog(contentPanel, "Registration Number cannot be empty", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                ParkingSpot spot = carPark.removeCarByRegistration(registrationNumber);
                String message;
                
                if (spot == null) {
                    message = "Car with registration [" + registrationNumber + "] is not parked here!";
                } else {
                    String spotID = spot.getSpotID();
                    message = "Car with registration [" + registrationNumber + "] removed from spot [" + spotID + "]";
                    
                    // Iterate through the components of the contentPanel
                    Component[] components = contentPanel.getComponents();
    
                    for (Component component : components) {
                        if (component instanceof JPanel) {
                            JPanel boxPanel = (JPanel) component;
                            JLabel spotLabel = (JLabel) boxPanel.getComponent(0); // SpotID is the first component
                            String labelID = spotLabel.getText();
        
                            if (spotID.equals(labelID)) {
                                // Change background and border color
                                boxPanel.setBackground(Color.decode(UNOCCUPIED_SPOT_COLOR)); 
                                
                                // Remove car info from parking spot
                                Component[] cs = boxPanel.getComponents();
                                boxPanel.remove(cs[1]);
                                
                                break;
                            }
                        }
                    }
                }
                
                // Refresh the layout of the contentPanel to reflect the changes
                contentPanel.revalidate();
                contentPanel.repaint();
                
                // Inform user the result
                JOptionPane.showMessageDialog(contentPanel, message, "Message", JOptionPane.INFORMATION_MESSAGE);      
            }
        }
    }
    
    /**
     * Class findCarsByMake to find cars by make.
     *
     */
    class findCarsByMake implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Display input dialog to enter the car make
            String make = JOptionPane.showInputDialog(contentPanel, "What's the Car Make?", "Input", JOptionPane.PLAIN_MESSAGE);
            
            // Check if user inputs are null or empty
            if (make == null) {
                return;
            } else if (make.isEmpty()) {
                JOptionPane.showMessageDialog(contentPanel, "Car Make cannot be empty", "Message", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                String result = carPark.findCarsByMake(make);
                
                // Inform user the result
                if (result == null) {
                    JOptionPane.showMessageDialog(contentPanel, "No cars found with make [" + make + "]", "Message", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(contentPanel, result, "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        
        }
    }
    
    /**
     * Class resetCarPark to reset all cars in car park.
     *
     */
    class resetCarPark implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int count = carPark.getParkedCarsCount();
            String message = count + " car(s) is being removed to reset the car park!";
            
            // Inform user the number of parked cars
            JOptionPane.showMessageDialog(contentPanel, message, "Message", JOptionPane.INFORMATION_MESSAGE);
            
            // Remove all cars
            carPark.resetCarPark();
            
            // Remove cars from contentPanel
            Component[] components = contentPanel.getComponents();
            
            for (Component component : components) {
                if (component instanceof JPanel) {
                    JPanel boxPanel = (JPanel) component;

                    // Change background and border color
                    boxPanel.setBackground(Color.decode(UNOCCUPIED_SPOT_COLOR)); 
                    
                    // Remove car info from parking spot
                    Component[] cs = boxPanel.getComponents();
                    
                    // Check if car info exists
                    if (cs.length > 1) {
                        boxPanel.remove(cs[1]); // Car info is the second component
                    }
                }
            }
        }
    }
    
    /**
     * Class exit to close the application.
     *
     */
    class exit implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Exit the application
            System.exit(0);
        }
    }
    
    /**
     * Class handleOnclickCar to remove clicked car from spot.
     *
     */
    class handleOnclickCar implements MouseListener {
        private String registrationNumber;
        private String spotID;
        
        public handleOnclickCar(String registrationNumber, String spotID) {
            this.registrationNumber = registrationNumber;
            this.spotID = spotID;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            // Display confirmation dialog
            int option = JOptionPane.showConfirmDialog(contentPanel, "Are you sure you want to remove this car [" + registrationNumber + "] from spot [" + spotID + "]?", "Confirm", JOptionPane.YES_NO_OPTION);
            
            // Check user's choice
            if (option == JOptionPane.YES_OPTION) {
                // User clicked "Yes", perform removal action
                ParkingSpot spot = carPark.removeCarByRegistration(registrationNumber);
                String message;
                
                if (spot == null) {
                    message = "Something went wrong!";
                } else {
                    message = "Car with registration [" + registrationNumber + "] removed from spot [" + spotID + "]";
                    
                    // Iterate through the components of the contentPanel
                    Component[] components = contentPanel.getComponents();
    
                    for (Component component : components) {
                        if (component instanceof JPanel) {
                            JPanel boxPanel = (JPanel) component;
                            JLabel spotLabel = (JLabel) boxPanel.getComponent(0); // SpotID is the first component
                            String labelID = spotLabel.getText();
        
                            if (spotID.equals(labelID)) {
                                // Change background and border color
                                boxPanel.setBackground(Color.decode(UNOCCUPIED_SPOT_COLOR)); 
                                
                                // Remove car info from parking spot
                                Component[] cs = boxPanel.getComponents();
                                boxPanel.remove(cs[1]);
                                
                                break;
                            }
                        }
                    }
                }
                
                // Refresh the layout of the contentPanel to reflect the changes
                contentPanel.revalidate();
                contentPanel.repaint();
                
                // Inform user the result
                JOptionPane.showMessageDialog(contentPanel, message, "Message", JOptionPane.INFORMATION_MESSAGE);  
            } else {
                // User clicked "No" or closed the dialog
                return;
            }
        }
        
        @Override
        public void mouseExited(MouseEvent e) {}
        
        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}
    
        @Override
        public void mouseEntered(MouseEvent e) {}
    }
    
    /**
     * Class handleOnclickSpot to remove clicked spot.
     *
     */
    class handleOnclickSpot implements MouseListener {
        private String spotID;
        
        public handleOnclickSpot(String spotID) {
            this.spotID = spotID;
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            // Display confirmation dialog
            int option = JOptionPane.showConfirmDialog(contentPanel, "Are you sure you want to remove this spot?", "Confirm", JOptionPane.YES_NO_OPTION);
            
            // Check user's choice
            if (option == JOptionPane.YES_OPTION) {
                // User clicked "Yes", perform removal action
                String result = carPark.deleteParkingSpot(spotID);
            
                if (result != "OK") {
                    JOptionPane.showMessageDialog(contentPanel, result, "Message", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Iterate through the components of the contentPanel
                    Component[] components = contentPanel.getComponents();
    
                    for (Component component : components) {
                        if (component instanceof JPanel) {
                            JPanel boxPanel = (JPanel) component;
                            JLabel spotLabel = (JLabel) boxPanel.getComponent(0); // SpotID is the first component
                            String labelID = spotLabel.getText();
        
                            if (spotID.equals(labelID)) {
                                // Remove the boxPanel from the contentPanel
                                contentPanel.remove(boxPanel);
                                break;
                            }
                        }
                    }
        
                    // Refresh the layout of the contentPanel to reflect the changes
                    contentPanel.revalidate();
                    contentPanel.repaint();
                    
                    // Inform user the result
                    JOptionPane.showMessageDialog(contentPanel, "Parking slot deleted successfully.", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                // User clicked "No" or closed the dialog
                return;
            }
        }
        
        @Override
        public void mouseExited(MouseEvent e) {}
        
        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}
    
        @Override
        public void mouseEntered(MouseEvent e) {}
    }

    public static void main(String[] args) {
        JFrame myApp = new ApplicationGUI();
        myApp.setVisible(true);
        myApp.setSize(HEIGHT, WIDTH); // set window size
    }
}
