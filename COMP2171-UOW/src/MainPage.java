import java.awt.*;
//import java.awt.image.BufferedImage;
//import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
// import java.io.File;
// import javax.swing.JScrollPane;
// import java.io.IOException;

 public class MainPage extends JFrame
    {
        private JLabel header           = new JLabel("UWI ON WHEELS");
        private JPanel  mainScreenPanel = new JPanel();
        //private JPanel imgPnel = new JPanel();
        //private JScrollPane scroll = new JScrollPane();
        private JButton selectBike      = new JButton("SELECT BIKE");
        private JButton paymentButton   = new JButton("PAYMENT PLAN");
        private JButton rateButton      = new JButton("RATE BIKE");
        private JButton incidentButton  = new JButton("REPORT INCIDENT");
        private JButton logout          = new JButton("LOGOUT");

        public MainPage()
        {
            setTitle("MainScreen");
            setBounds(300, 90, 420,700);
            setResizable(false);

            header.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
            header.setBounds(120, 20, 200, 30);

            // BufferedImage myPicture = null;
            // try {
            //     myPicture = ImageIO.read(new File("Logo.png"));
            // } catch (IOException ex) {
            //     ex.printStackTrace();
            // }
            // JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            // Dimension size = new Dimension(400, 200);
            // picLabel.setPreferredSize(size);
            // imgPnel.add(picLabel);

            //add(imgPnel,BorderLayout.NORTH);
            //scroll.setViewportView(mainScreenPanel);

            mainScreenPanel.add(header);
            
            add(mainScreenPanel);
            mainScreenPanel.setLayout(null);
            mainScreenPanel.setBackground(Color.GREEN);

            selectBike.setBounds(110, 150, 200, 35);
            paymentButton.setBounds(110, 250, 200, 35);
            rateButton.setBounds(110, 350, 200, 35);
            incidentButton.setBounds(110, 450, 200, 35 );
            logout.setBounds(110, 550, 200, 35);

            mainScreenPanel.add(selectBike);
            mainScreenPanel.add(paymentButton);
            mainScreenPanel.add(rateButton);
            mainScreenPanel.add(incidentButton);
            mainScreenPanel.add(logout);

            setVisible(true);
            // Button Listeners
            selectBike.addActionListener(new SelectBikeListener());
            paymentButton.addActionListener(new PaymentButtonListener());
            incidentButton.addActionListener(new IncidentButtonListener());
            rateButton.addActionListener(new RateBikeListener());
            logout.addActionListener(new LogoutListener());

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
