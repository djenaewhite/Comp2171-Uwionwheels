import java.awt.*;
//import java.awt.image.BufferedImage;
//import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
// import javax.swing.JScrollPane;
// import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.util.*;

 public class MainPage extends JFrame
    {
        private JLabel header           = new JLabel("UWI ON WHEELS");
        private JPanel  mainScreenPanel = new JPanel();
        private JButton selectBike      = new JButton("SELECT BIKE");
        private JButton paymentButton   = new JButton("PAYMENT PLAN");
        private JButton rateButton      = new JButton("RATE BIKE");
        private JButton incidentButton  = new JButton("REPORT INCIDENT");
        private JButton logout          = new JButton("LOGOUT");
        private JButton ViewProfile     = new JButton("VIEW MY PROFILE");
        private static final int LIGHT_NUDE_RED = 255;
        private static final int LIGHT_NUDE_GREEN = 220;
        private static final int LIGHT_NUDE_BLUE = 187;
        
        private static final int MEDIUM_NUDE_RED = 200;
        private static final int MEDIUM_NUDE_GREEN = 150;
        private static final int MEDIUM_NUDE_BLUE = 130;
        
        private static final int DARK_NUDE_RED = 160;
        private static final int DARK_NUDE_GREEN = 100;
        private static final int DARK_NUDE_BLUE = 90;
        private ArrayList<User> users = new ArrayList<User>();

        public MainPage()
        {
            setTitle("MainScreen");
            setBounds(300, 90, 420,700);
            setResizable(true);

            header.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
            header.setBounds(120, 20, 200, 30);
            mainScreenPanel.add(header);
            
            add(mainScreenPanel);
            mainScreenPanel.setLayout(null);
            mainScreenPanel.setBackground(new Color(DARK_NUDE_RED, LIGHT_NUDE_GREEN, LIGHT_NUDE_BLUE));

            selectBike.setBounds(110, 50, 200, 35);
            paymentButton.setBounds(110, 150, 200, 35);
            rateButton.setBounds(110, 250, 200, 35);
            incidentButton.setBounds(110, 350, 200, 35 );
            logout.setBounds(110, 550, 200, 35);
            ViewProfile.setBounds(110, 450, 200, 35);

            mainScreenPanel.add(selectBike);
            mainScreenPanel.add(paymentButton);
            mainScreenPanel.add(rateButton);
            mainScreenPanel.add(incidentButton);
            mainScreenPanel.add(logout);
            mainScreenPanel.add(ViewProfile);


            setVisible(true);
            // Button Listeners
            selectBike.addActionListener(new SelectBikeListener());
            paymentButton.addActionListener(new PaymentButtonListener());
            incidentButton.addActionListener(new IncidentButtonListener());
            rateButton.addActionListener(new RateBikeListener());
            logout.addActionListener(new LogoutListener());
            ViewProfile.addActionListener(new ViewProfileListener());

        }
        private class SelectBikeListener implements ActionListener
        {
            /**
             * This method calls the BicycleRenatal class
             */
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    dispose();
                    BicycleRental select = new BicycleRental(); 
                }
                catch(NullPointerException nulP)
                {}
            }
        }
 
        private class ViewProfileListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                dispose();
                // Get the ID or other unique identifier of the logged-in user
                JFrame frame = new JFrame("User Profile");
                String loggedInUserID = JOptionPane.showInputDialog(frame, "Re-enter your ID for authentication purposes:");
    
                // Retrieve the list of users from the file
                ArrayList<User> users = adduser("userdata.txt");
    
                try {
                    // Iterate through the list of users to find the logged-in user
                    for (User user : users) {
                        if (user.getid().equals(loggedInUserID)) {
                            // If found, extract user information
                            String firstName = user.getfname();
                            String lastName = user.getlname();
                            String email = user.getemail();
                            String password = user.getpassword();
    
                            // Create a panel to display user profile information
                            JPanel profilePanel = new JPanel();
                            profilePanel.setLayout(null);
                            profilePanel.setBackground(new Color(DARK_NUDE_RED, LIGHT_NUDE_GREEN, LIGHT_NUDE_BLUE));
    
                            // Add profile information labels
                            JLabel nameLabel = new JLabel("Name: " + firstName + " " + lastName);
                            nameLabel.setBounds(20, 20, 200, 30);
                            nameLabel.setForeground(Color.black);
                            profilePanel.add(nameLabel);
    
                            JLabel emailLabel = new JLabel("Email: " + email);
                            emailLabel.setBounds(20, 60, 200, 30);
                            emailLabel.setForeground(Color.black);
                            profilePanel.add(emailLabel);
    
                            JLabel idLabel = new JLabel("ID: " + loggedInUserID);
                            idLabel.setBounds(20, 100, 200, 30);
                            idLabel.setForeground(Color.black);
                            profilePanel.add(idLabel);
    
                            JLabel passwordLabel = new JLabel("Password: " + password);
                            passwordLabel.setBounds(20, 140, 200, 30);
                            passwordLabel.setForeground(Color.black);
                            profilePanel.add(passwordLabel);
                            JButton returnButton = new JButton("Return to Menu");
                            returnButton.setBounds(60,550, 140, 30);
                            returnButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    // Close the dialog
                                    dispose();
                                    // Close the main window
                                    MainPage.this.dispose();
                                }
                            });

                            profilePanel.add(returnButton);
                            JButton deleteButton = new JButton("Delete my profile");
                            deleteButton.setBounds(230, 550, 140, 30);
                            deleteButton.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    try {
                                        // Remove the user from the list
                                        dispose();
                                        MainPage.this.dispose(); // Close the main window
                                        Login login = new Login();
                                        users.remove(user);

                                        // Update the user data file
                                        FileWriter writer = new FileWriter("userdata.txt");
                                        for (User user : users) {
                                            writer.write(user.getfname() + "_" + user.getlname() + " " + user.getid() + " " + user.getemail() + " " + user.getpassword() + "\n");
                                        }
                                        writer.close();
                                        JOptionPane.showMessageDialog(frame, "Account deleted successfully.");
                                        // Close the dialog after deletion
                                        frame.dispose();
                                    } catch (IOException ex) {
                                        JOptionPane.showMessageDialog(frame, "An error occurred while updating the file.");
                                    }
                                }
                            });

                            profilePanel.add(deleteButton);

    
                            // Create a dialog with custom panel
                            JDialog dialog = new JDialog();
                            dialog.setTitle("User Profile");
                            dialog.getContentPane().add(profilePanel);
                            dialog.setBounds(300, 90, 420,700);
                            dialog.setLocationRelativeTo(null);
                            dialog.setVisible(true);
    
                            // Break the loop as we found the user
                            break;
                        }
                    }
                } catch (Exception ex) {
                    // Handle any potential errors
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    
        
        
        public User findUser(String id) {
            for (User user : users) {
                if (user.getid().equals(id)) {
                    return user;
                }
            }
            return null;
        }

        public void addUser(User user) {
            users.add(user);
        }
        public ArrayList<User> adduser(String userdata) {
            Scanner uscan = null;
            ArrayList<User> users = new ArrayList<User>();
            try {
                uscan = new Scanner(new File(userdata));
                while (uscan.hasNext()) {
                    String[] nextLine = uscan.nextLine().split(" ");
                    String fname = nextLine[0];
                    String lname = nextLine[1];
                    String id = nextLine[2];
                    String email = nextLine[3];
                    String password = nextLine[4];
        
                    User user = new User(fname, lname, id, email, password);
                    users.add(user);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } finally {
                if (uscan != null) {
                    uscan.close();
                }
            }
            return users;
        }
        
        private class PaymentButtonListener implements ActionListener
        {
            /**
             * This method calls the PaymentPlan class
             */
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    dispose();
                    PaymentPlan newPay = new PaymentPlan();
                }
                catch(NullPointerException nulP)
                {}
            }
        }

        public ArrayList<User> getUsers() {
            return users;
        }

         private class RateBikeListener implements ActionListener
         {
            /**
             * This method calls the RateBike class
             */
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    dispose();
                    RateBike rate = new RateBike(); 
                }
                catch(NullPointerException nulP)
                {}
            }
        }

        private class IncidentButtonListener implements ActionListener
        {
            /**
             * This method calls the IncidentReport class 
             */
            public void actionPerformed(ActionEvent e)
            {
                try
                {
                    dispose();
                    IncidentReport report = new IncidentReport();
                }
                catch(NullPointerException nulP)
                {}
            }
        }
    
        private class LogoutListener implements ActionListener
        {
            public void actionPerformed (ActionEvent e)
            {
                dispose();
                Login login = new Login();
            }
        }

    } 
